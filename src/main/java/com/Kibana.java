package com;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Kibana {
    @JsonProperty
    public String indexName = "";
    @JsonProperty
    public List<String> iterationKeys = Collections.emptyList();
    @JsonProperty
    public String searchQuery = "";
    @JsonProperty
    public long startTime = 0L;
    @JsonProperty
    public long endTime = 0L;
    public List<KibanaQuery> searchQueries = new ArrayList();

    public Kibana() {
    }
}