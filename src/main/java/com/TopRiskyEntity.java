package com;

import java.util.HashSet;
import java.util.Set;

public class TopRiskyEntity implements Comparable<TopRiskyEntity> {
    private String entityHash;
    private String entityType;
    private String entityName;
    private double score;
    private int scoreChange;
    private long timestamp;
    private long currentTimestamp;
    private ParsedAlert mostSignificantAlert;
    private boolean isAnomalous;
    private Set<Tag> tags;

    public TopRiskyEntity() {
        this.tags = new HashSet();
    }

    public TopRiskyEntity(String entityHash, String entityName, String entityType, double score, long timestamp) {
        this(entityHash, entityName, entityType, score, timestamp, new HashSet());
    }

    public TopRiskyEntity(String entityHash, String entityName, String entityType, double score, long timestamp, Set<Tag> tags) {
        this.tags = new HashSet();
        this.entityHash = entityHash;
        this.entityName = entityName;
        this.entityType = entityType;
        this.score = score;
        this.scoreChange = 0;
        this.timestamp = timestamp;
        this.tags = tags;
    }

    public TopRiskyEntity(String entityHash, String entityName, String entityType, double score, int scoreChange, ParsedAlert mostSignificantAlert, long timestamp) {
        this.tags = new HashSet();
        this.entityHash = entityHash;
        this.entityName = entityName;
        this.entityType = entityType;
        this.score = score;
        this.scoreChange = scoreChange;
        this.mostSignificantAlert = mostSignificantAlert;
        this.timestamp = timestamp;
    }

    public String getEntityHash() {
        return this.entityHash;
    }

    public void setEntityHash(String entityHash) {
        this.entityHash = entityHash;
    }

    public String getEntityType() {
        EntityType type = EntityType.lookupByCode(this.entityType);
        return type != null ? type.toString() : this.entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getEntityName() {
        return this.entityName;
    }

    public void setEntityName(String label) {
        this.entityName = label;
    }

    public double getScore() {
        return this.score;
    }

    public void setScore(double risk) {
        this.score = risk;
    }

    public int getScoreAsInt() {
        return this.toIntScore(this.score);
    }

    public int getScoreChange() {
        return this.scoreChange;
    }

    public void setScoreChange(int scoreChange) {
        this.scoreChange = scoreChange;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getCurrentTimestamp() {
        return this.currentTimestamp;
    }

    public void setCurrentTimestamp(long currentTimestamp) {
        this.currentTimestamp = currentTimestamp;
    }

    public ParsedAlert getMostSignificantAlert() {
        return this.mostSignificantAlert;
    }

    public void setMostSignificantAlert(ParsedAlert mostSignificantAlert) {
        this.mostSignificantAlert = mostSignificantAlert;
    }

    public Set<Tag> getTags() {
        return this.tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public boolean isAnomalous() {
        return this.isAnomalous;
    }

    public void setAnomalous(boolean isAnomalous) {
        this.isAnomalous = isAnomalous;
    }

    private int toIntScore(double risk) {
        return (int)Math.min(Math.round(risk * 100.0D), 100L);
    }

    public int compareTo(TopRiskyEntity topRisky2) {
        int scoreDiff = Double.compare(topRisky2.getScore(), this.score);
        return scoreDiff == 0 ? this.getEntityHash().compareTo(topRisky2.getEntityHash()) : scoreDiff;
    }
}
