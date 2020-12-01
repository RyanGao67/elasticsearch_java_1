package com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Results<T> {
    protected static final Logger LOG = LoggerFactory.getLogger(Results.class);
    private static final String DATA_KEY = "data";
    private Map<String, Object> results = new HashMap();

    public void putMetadata(String key, Object value) {
        if ("data".equals(key)) {
            try {
                this.setData((T) value);
            } catch (ClassCastException var4) {
                LOG.warn("Unable to cast results data to correct type: {}", var4.getMessage());
                throw var4;
            }
        }

        this.results.put(key, value);
    }

    public void putAllMetadata(Map<String, Object> metadata) {
        metadata.forEach(this::putMetadata);
    }

    public Map<String, Object> getMetadata() {
        return (Map)this.results.entrySet().stream().filter((d) -> {
            return !"data".equals(d.getKey());
        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public <V> V getMetadata(String key, Class<V> clazz) {
        Object value = this.results.get(key);
        return clazz.isInstance(value) ? clazz.cast(this.results.get(key)) : null;
    }

    public T getData() {
        return (T) this.results.get("data");
    }

    public void setData(T data) {
        this.results.put("data", data);
    }

    public Results() {
    }

    public Results(Map<String, Object> metadata, T data) {
        this.results.putAll(metadata);
        this.setData(data);
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap(this.getMetadata());
        map.put("data", this.getData());
        return map;
    }

    public static class Metadata {
        public static final String SCROLL_ID = "scrollId";
        public static final String TOTAL_HITS = "totalHits";
        public static final String QUERY_TIME = "queryTime";
        public static final String REQUEST_TIME = "requestTime";
        public static final String RISK_THRESHOLD = "riskThreshold";
        public static final String CACHED = "cached";
        public static final String ALL_FIELDS = "allFields";
        public static final String SORTABLE_FIELDS = "sortableFields";

        public Metadata() {
        }
    }
}

