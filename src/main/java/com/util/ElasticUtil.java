package com.util;

import com.ElasConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ElasticUtil {
    // create a singleton high level rest client
    RestHighLevelClient client;
    public static ElasConfig getCredential(String path) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(path);
        ElasConfig esConfig = objectMapper.readValue(file, ElasConfig.class);
        return esConfig;
    }
    public ElasticUtil() throws IOException {
        String conf = "/Users/tiangao/interset/testTopRisky/es.json";
        ElasConfig esConf = getCredential(conf);
        System.out.println(esConf);
        this.client  = new HighLevelClient().getInstance(esConf);
    }

    // create a index
    public boolean createIndexWithMapping(String file) throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("risk_scores");
        String content = new String(Files.readAllBytes(Paths.get(file)));
        request.source(content, XContentType.JSON);
        boolean result = false;
        try {
            CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
            result = createIndexResponse.isAcknowledged();
        } catch (ElasticsearchStatusException e) {
            // if the index already exists, skip this step of creating index
            if (e.getMessage().contains("resource_already_exists_exception") ) {
                return result;
            }
            throw e;
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        ElasticUtil elasticUtil = new ElasticUtil();
        elasticUtil.createIndexWithMapping("./src/main/resources/risk_score.json");
    }
}
