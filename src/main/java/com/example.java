package com;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.util.HighLevelClient;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.NumericMetricsAggregation;
import org.elasticsearch.search.aggregations.metrics.TopHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.*;

public class example {
    protected static final Logger LOG = LoggerFactory.getLogger(example.class);
    private static final String ENTITIES = "entities";
    private static final String SCORE_AGG = "scoreAgg";
    private static final String SINGLE_HIT = "currentAgg";
    SearchRequest searchRequest = new SearchRequest("risk_scores*");
    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    RestHighLevelClient client;
    public example(){}
    public example(ElasConfig elasConfig){
        client = new HighLevelClient().getInstance(elasConfig);
    }

    public Set<String> getTopRiskyByEntityType(
            //String q,
            long ts,
            long _te,
            //List<String> entityHashesFilter,
            //RiskSort sort,
            int count,
            List<EntityType> types,
            //String scrollId,
            //ZoneId timezone,
            //boolean includeNonAnomalous
            double rs,
            double re
    ) throws IOException {
        //////////////////////////////////////////////////////////////////////////////preparing
        searchRequest.indicesOptions(IndicesOptions.fromOptions(true, true, true, true));
        searchRequest.requestCache(true);
        searchSourceBuilder.trackTotalHits(true);

        TermsAggregationBuilder agg = AggregationBuilders.terms(ENTITIES).field(RiskScoresIndex.Fields.ENTITYHASH).size(count);
        agg.subAggregation(AggregationBuilders.max(SCORE_AGG).field(RiskScoresIndex.Fields.SCORE))
                .subAggregation(AggregationBuilders.topHits(SINGLE_HIT)
                        .fetchSource(new String[]{RiskScoresIndex.Fields.ENTITYNAME, RiskScoresIndex.Fields.ENTITYTYPE, RiskScoresIndex.Fields.TIMESTAMP, RiskScoresIndex.Fields.HAS_ANOMALIES}, null)
                        .size(1))
                .order(BucketOrder.compound(
                        BucketOrder.aggregation(SCORE_AGG, "value", false),
                        BucketOrder.key(false)
                ));
        searchSourceBuilder.aggregation(agg);
        ///////////////////////////////////////////////////////////////////////////////

        //double rs = 0.5; double re = 1.0;
        long te = DateUtils.atStartOfHour(_te);
        Set<String> topRiskyEntityHashes = new LinkedHashSet<>();
        while (topRiskyEntityHashes.size() < count && re > 0) {

            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            withEntityTypes(types, boolQueryBuilder);
            excludeEntities(new ArrayList<>(topRiskyEntityHashes), boolQueryBuilder);
            withRiskRange(rs, re, boolQueryBuilder);
            includeNonAnomalous(false, boolQueryBuilder);
            between(ts, te, boolQueryBuilder);

            searchSourceBuilder.query(QueryBuilders.boolQuery().filter(boolQueryBuilder));
            searchSourceBuilder.size(0).from(0).version(false);

            SearchRequest request= searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = client.search(request, RequestOptions.DEFAULT);

            // print hit index for test
            // SearchHit[] result = searchResponse.getHits().getHits();for(SearchHit hit:result){String s = hit.getSourceAsString();System.out.println(s);}

            // get aggregations
            List<TopRiskyEntity> topRiskyEntityList = parse(searchResponse.getAggregations());
            topRiskyEntityHashes.addAll(
                    topRiskyEntityList
                            .stream()
                            .map(TopRiskyEntity::getEntityHash)
                            .collect(Collectors.toCollection(LinkedHashSet::new))
            );

            re = rs; rs = re / 2; if (rs < 0.05){rs = -0.01;}
        }

        List<String> estimatedTopRisky = new ArrayList<>(topRiskyEntityHashes);
        if (estimatedTopRisky.size() > count) {
            estimatedTopRisky = estimatedTopRisky.subList(0, count);
        }


        client.close();
        return new HashSet<>(estimatedTopRisky);
    }


    public void withEntityHashes(List<String> entityHashes, BoolQueryBuilder query) {
        // If null, there are no entityHashes to filter; don't apply the clause
        if (entityHashes != null) {
            if (entityHashes.isEmpty()) {
                // if empty, we want to match no entityHashes <- we can short-circuit the query earlier, but this is in case it still makes it through
                query.must(termQuery(RiskScoresIndex.Fields.ENTITYHASH, ""));
            } else {
                query.must(getTermsQuery(RiskScoresIndex.Fields.ENTITYHASH, entityHashes));
            }
        }
    }

    protected <V> QueryBuilder getTermsQuery(String field, Collection<V> collection) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (collection != null && !collection.isEmpty()) {
            List<V> collectionList = new ArrayList<>(collection);
            int maxTermsPerQuery = 1024;
            for (int i = 0; i < collectionList.size(); i += maxTermsPerQuery) {
                int end = Math.min(collectionList.size(), i + maxTermsPerQuery);
                List<V> partition = collectionList.subList(i, end);
                boolQueryBuilder.should(QueryBuilders.termsQuery(field, partition));
            }
        }
        return boolQueryBuilder;
    }

    public void  withEntityTypes(List<EntityType> types, BoolQueryBuilder query) {
        if (types != null) {
            BoolQueryBuilder tempBoolQuery = boolQuery();
            types.forEach(type -> tempBoolQuery.should(matchQuery(RiskScoresIndex.Fields.ENTITYTYPE, type.getCode())));
            query.must(tempBoolQuery);
        }
    }

    public void  excludeEntities(List<String> entityHashes,BoolQueryBuilder query) {
        if (entityHashes != null && !entityHashes.isEmpty()) {
            query.mustNot(getTermsQuery(RiskScoresIndex.Fields.ENTITYHASH, entityHashes));
        }
    }

    public void withRiskRange(double rs, double re, BoolQueryBuilder query) {
        query.must(
                rangeQuery(RiskScoresIndex.Fields.SCORE)
                        .from(rs)
                        .to(re)
                        .includeLower(false)
                        .includeUpper(true)
        );
    }

    public void includeNonAnomalous(boolean includeAnomalous, BoolQueryBuilder query) {
        if (!includeAnomalous) {
            query.should(
                    boolQuery()
                            .should(boolQuery().must(existsQuery(RiskScoresIndex.Fields.HAS_ANOMALIES)))
                            .should(matchQuery(RiskScoresIndex.Fields.HAS_ANOMALIES, true))
            ).should(
                    boolQuery()
                            .mustNot(matchQuery(RiskScoresIndex.Fields.SCORE, 0))
            ).minimumShouldMatch(1);
        }
    }

    public void between(long ts, long te, BoolQueryBuilder query) {
        query.must(buildRangeQuery(ts, te));
    }

    private RangeQueryBuilder buildRangeQuery(long ts, long te) {
        return rangeQuery(RiskScoresIndex.Fields.TIMESTAMP).from(ts).to(te).includeLower(true).includeUpper(true);
    }

    public List<TopRiskyEntity> parse(Aggregations aggregations) {
        List<TopRiskyEntity> topRiskyEntities = new ArrayList<>();
        Terms riskAgg = aggregations.get(ENTITIES);
        if (riskAgg == null) {return topRiskyEntities;}

        for (Terms.Bucket entity : riskAgg.getBuckets()) {
            Aggregations entityAggs = entity.getAggregations();
            TopRiskyEntity topRisky = new TopRiskyEntity();
            topRisky.setEntityHash(entity.getKeyAsString()); // get the bucket key ---- entityhash
            TopHits mostCurrent = entityAggs.get(SINGLE_HIT);
            SearchHit[] hits = mostCurrent.getHits().getHits();
            if (hits.length > 0) {
                Map<String, Object> mostCurrentHit = mostCurrent.getHits().getHits()[0].getSourceAsMap();
                topRisky.setEntityName(mostCurrentHit.get(RiskScoresIndex.Fields.ENTITYNAME).toString());
                topRisky.setEntityType(mostCurrentHit.get(RiskScoresIndex.Fields.ENTITYTYPE).toString());
                topRisky.setTimestamp(((Number) mostCurrentHit.get(RiskScoresIndex.Fields.TIMESTAMP)).longValue());
                topRisky.setAnomalous(Boolean.parseBoolean(mostCurrentHit.get(RiskScoresIndex.Fields.HAS_ANOMALIES).toString()));
                NumericMetricsAggregation.SingleValue score = entityAggs.get(SCORE_AGG);
                topRisky.setScore(Double.parseDouble(score.getValueAsString()));
            }
            topRiskyEntities.add(topRisky);
        }

        return topRiskyEntities;
    }

    public static ElasConfig getCredential(String path) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(path);
        ElasConfig esConfig = objectMapper.readValue(file, ElasConfig.class);
        return esConfig;
    }
    public static void main(String[] args) throws IOException {
        long start;long finish;long timeElapsed;
        start =System.currentTimeMillis();
//  "/Users/tiangao/interset/testTopRisky/es.json"
//        String conf = args[0];
        String conf = "/Users/tiangao/interset/testTopRisky/es.json";
        ElasConfig esConf = getCredential(conf);
        System.out.println(esConf);
        System.out.println(
                new example(esConf)
                        .getTopRiskyByEntityType(0L,158257440000000L, 5, null, 0.5, 1)
        );

        finish =System.currentTimeMillis();
        timeElapsed = finish - start;
        System.out.println("time:"+timeElapsed);
    }
}
