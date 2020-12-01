package com;

import com.fasterxml.jackson.annotation.JsonProperty;
import sun.net.www.content.text.Generic;

import java.util.ArrayList;
import java.util.List;

public class AggregateAlertTemplate {
    @JsonProperty
    public List<Integer> id = new ArrayList();
    @JsonProperty
    public Templates templates = new Templates();
    @JsonProperty
    public Generic generic = new Generic();
    @JsonProperty
    public Kibana kibana = new Kibana();
    @JsonProperty
    public Recon recon = new Recon();
    @JsonProperty
    public SQL sql = new SQL();
    @JsonProperty
    public boolean hasAggregatedIdentifiers = false;
    @JsonProperty
    public DataSource datasource;
    @JsonProperty
    public ContextType contextType;
    @JsonProperty
    public BucketSize bucketSize;
    @JsonProperty
    public PotentialThreat threat;
    @JsonProperty
    public Family family;
    @JsonProperty
    public Unit unit;
    @JsonProperty
    public List<Anomaly> exampleAnomalies;
    @JsonProperty
    public String splitOn;

    public AggregateAlertTemplate() {
        this.datasource = DataSource.unknown;
        this.contextType = ContextType.none;
        this.bucketSize = BucketSize.hourly;
        this.threat = new PotentialThreat("Unknown", "Unknown threat");
        this.family = new Family();
        this.unit = new Unit("Unknown", "Unknown unit", "Unknown unitType");
        this.exampleAnomalies = new ArrayList();
        this.splitOn = "${entityHashes[0]}_${idHashes[0]}";
    }

    public List<Integer> getId() {
        return this.id;
    }
}
