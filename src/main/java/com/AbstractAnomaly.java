package com;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractAnomaly extends ESHit {
    private String id = "unknown";
    private double contribution;
    private double significance;
    private double risk;
    private long timestamp;
    private Long endTimestamp;
    private String topAnomalyId;
    private List<AlertTag> tags = new ArrayList();
    private double observed = 1.0D;
    long numAnomalies = 1L;
    long numChildren = 0L;
    String parentId = "";
    Map<String, Long> entityCounts = (Map)EntityType.getScoredEntities().stream().collect(Collectors.toMap(EntityType::getCode, (p) -> {
        return 0L;
    }));

    public AbstractAnomaly() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setContribution(double contribution) {
        this.contribution = contribution;
    }

    public void setSignificance(double significance) {
        this.significance = significance;
    }

    public double getContribution() {
        return this.contribution;
    }

    public double getSignificance() {
        return this.significance;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getEndTimestamp() {
        return this.endTimestamp;
    }

    public void setEndTimestamp(Long endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    public double getRisk() {
        return this.risk;
    }

    public double getRoundedRisk() {
        return (double)Math.round(this.risk * 1000.0D) / 100.0D;
    }

    public void setRisk(double risk) {
        this.risk = risk;
    }

    public void setNumAnomalies(long numAnomalies) {
        this.numAnomalies = numAnomalies;
    }

    public long getNumAnomalies() {
        return this.numAnomalies;
    }

    public double getObserved() {
        return this.observed;
    }

    public void setNumChildren(long numChildren) {
        this.numChildren = numChildren;
    }

    public Long getNumChildren() {
        return this.numChildren;
    }

    public void setEntityCounts(Map<String, Long> entityCounts) {
        this.entityCounts = entityCounts;
    }

    public Map<String, Long> getEntityCounts() {
        return this.entityCounts;
    }

    public String getTopAnomalyId() {
        return (String)this.getTopAnomalies().stream().map(AbstractAnomaly::getId).findFirst().orElse("");
    }

    public List<AlertTag> getTags() {
        return this.tags;
    }

    public void setTags(List<AlertTag> tags) {
        this.tags = tags;
    }

    public void setObserved(double observed) {
        this.observed = observed;
    }

    public abstract List<Entity> getAssociatedEntities();

    public abstract List<Integer> getAnomalyTypes();

    public abstract List<Anomaly> getTopAnomalies();

    public abstract AnomaliesRollupLevel getRollupLevel();

    public abstract String getParentId();
}
