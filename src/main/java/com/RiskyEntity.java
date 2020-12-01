package com;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class RiskyEntity extends ESHit implements Comparable<RiskyEntity> {
    private String entityHash;
    private String entityType;
    private String entityName;
    private double botScore;
    private String[] clusters;
    private Set<Tag> tags;
    private boolean hasAnomalies;
    private boolean isBot;
    private long activeTimeStart;
    private long activeTimeEnd;
    private double currentScore;

    public RiskyEntity() {
        this.tags = Collections.emptySet();
    }

    public RiskyEntity(String entityHash, String entityName, String entityType) {
        this.tags = Collections.emptySet();
        this.entityHash = entityHash;
        this.entityName = entityName;
        this.entityType = entityType;
        this.botScore = 0.0D;
        this.tags = new HashSet();
        this.clusters = new String[0];
    }

    public RiskyEntity(String entityHash, String entityName, String entityType, double botScore) {
        this(entityHash, entityName, entityType);
        this.botScore = botScore;
    }

    public RiskyEntity(String entityHash, String entityName, String entityType, double botScore, Set<Tag> tags, String[] clusters) {
        this(entityHash, entityName, entityType, botScore);
        this.tags = tags;
        this.clusters = clusters;
    }

    public String getEntityHash() {
        return this.entityHash;
    }

    public void setEntityHash(String entityHash) {
        this.entityHash = entityHash;
    }

    public String getEntityType() {
        EntityType type = EntityType.lookupByCode(this.entityType);
        return type == null ? this.entityType : type.toString();
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getEntityName() {
        return this.entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public double getBotScore() {
        return this.botScore;
    }

    public void setBotScore(double botScore) {
        this.botScore = botScore;
    }

    public Set<Tag> getTags() {
        return this.tags;
    }

    public void setTags(Set<Tag> tags) {
        if (tags != null) {
            this.tags = tags;
        }
    }

    public String[] getClusters() {
        return this.clusters;
    }

    public void setClusters(String[] clusters) {
        this.clusters = clusters;
    }

    public boolean hasAnomalies() {
        return this.hasAnomalies;
    }

    public void setHasAnomalies(boolean hasAnomalies) {
        this.hasAnomalies = hasAnomalies;
    }

    public boolean isBot() {
        return this.isBot;
    }

    public void setBot(boolean bot) {
        this.isBot = bot;
    }

    public long getActiveTimeStart() {
        return this.activeTimeStart;
    }

    public void setActiveTimeStart(long activeTimeStart) {
        this.activeTimeStart = activeTimeStart;
    }

    public long getActiveTimeEnd() {
        return this.activeTimeEnd;
    }

    public void setActiveTimeEnd(long activeTimeEnd) {
        this.activeTimeEnd = activeTimeEnd;
    }

    public double getCurrentScore() {
        return this.currentScore;
    }

    public void getCurrentScore(double currentScore) {
        this.currentScore = Math.max(currentScore, 0.0D);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof RiskyEntity)) {
            return false;
        } else {
            RiskyEntity entity = (RiskyEntity)obj;
            boolean entityHashMatches = this.getEntityHash() == null && entity.getEntityHash() == null || this.getEntityHash() != null && this.getEntityHash().equals(entity.getEntityHash());
            boolean entityNameMatches = this.getEntityName() == null && entity.getEntityName() == null || this.getEntityName() != null && this.getEntityName().equals(entity.getEntityName());
            boolean entityTypeMatches = this.getEntityType() == null && entity.getEntityType() == null || this.getEntityType() != null && this.getEntityType().equals(entity.getEntityType());
            return entityHashMatches && entityNameMatches && entityTypeMatches;
        }
    }

    public int compareTo(RiskyEntity entity) {
        return entity == null ? -1 : this.getEntityName().compareTo(entity.getEntityName());
    }
}
