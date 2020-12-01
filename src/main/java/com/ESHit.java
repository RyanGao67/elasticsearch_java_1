package com;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class ESHit {
    @JsonIgnore
    private float queryScore;
    @JsonIgnore
    private String documentId;
    @JsonIgnore
    private long documentVersion;
    @JsonIgnore
    private String indexName;
    @JsonIgnore
    private String clusterAlias;
    @JsonIgnore
    private String routing;

    public ESHit() {
    }

    public Float getQueryScore() {
        return this.queryScore;
    }

    public void setQueryScore(float queryScore) {
        this.queryScore = queryScore;
    }

    public String getDocumentId() {
        return this.documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public long getDocumentVersion() {
        return this.documentVersion;
    }

    public void setDocumentVersion(long documentVersion) {
        this.documentVersion = documentVersion;
    }

    public String getClusterAlias() {
        return this.clusterAlias;
    }

    public void setClusterAlias(String clusterAlias) {
        this.clusterAlias = clusterAlias;
    }

    public String getIndexName() {
        return this.indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getRouting() {
        return this.routing;
    }

    public void setRouting(String routing) {
        this.routing = routing;
    }
}
