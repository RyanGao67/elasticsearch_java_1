package com;

import java.util.Collections;
import java.util.List;

public class Anomaly extends AbstractAnomaly {
    private String ds;
    private int did;
    private int anomalyType = -1;
    private double probability;
    private double baseline = 0.0D;
    private String entityName;
    private String entityHash;
    private String entityType;
    private String idName;
    private String idHash;
    private String idType;
    private String relation;
    private String cluster;
    private List<Entity> associatedEntities;
    private double currentDecayedRisk;
    private String alertId;
    private String aggId;

    public Anomaly() {
        super.setNumChildren(0L);
        super.setNumAnomalies(1L);
    }

    public Anomaly(String id, long timestamp, int anomalyType, double risk, double probability) {
        super.setId(id);
        super.setRisk(risk);
        super.setTimestamp(timestamp);
        super.setNumChildren(0L);
        this.anomalyType = anomalyType;
        this.probability = probability;
    }

    public int getDid() {
        return this.did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public String getDs() {
        return this.ds;
    }

    public void setDs(String ds) {
        this.ds = ds;
    }

    public int getAnomalyType() {
        return this.anomalyType;
    }

    public void setAnomalyType(int anomalyType) {
        this.anomalyType = anomalyType;
    }

    public double getProbability() {
        return this.probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public double getBaseline() {
        return this.baseline;
    }

    public void setBaseline(double baseline) {
        this.baseline = baseline;
    }

    public String getEntityName() {
        return this.entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getEntityHash() {
        return this.entityHash;
    }

    public void setEntityHash(String entityHash) {
        this.entityHash = entityHash;
    }

    public String getEntityType() {
        return this.entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getRelation() {
        return this.relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getIdName() {
        return this.idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public String getIdHash() {
        return this.idHash;
    }

    public void setIdHash(String idHash) {
        this.idHash = idHash;
    }

    public String getIdType() {
        return this.idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getCluster() {
        return this.cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public List<Entity> getAssociatedEntities() {
        return this.associatedEntities;
    }

    public List<Integer> getAnomalyTypes() {
        return Collections.singletonList(this.anomalyType);
    }

    public List<Anomaly> getTopAnomalies() {
        return Collections.singletonList(this);
    }

    public void setAssociatedEntities(List<Entity> associatedEntities) {
        this.associatedEntities = associatedEntities;
    }

    public double getCurrentDecayedRisk() {
        return this.currentDecayedRisk;
    }

    public void setCurrentDecayedRisk(double currentDecayedRisk) {
        this.currentDecayedRisk = currentDecayedRisk;
    }

    public String getAlertId() {
        return this.alertId;
    }

    public void setAlertId(String alertId) {
        this.alertId = alertId;
    }

    public String getAggId() {
        return this.aggId;
    }

    public void setAggId(String aggId) {
        this.aggId = aggId;
    }

    public AnomaliesRollupLevel getRollupLevel() {
        return AnomaliesRollupLevel.anomalies;
    }

    public String getParentId() {
        return this.alertId;
    }
}
