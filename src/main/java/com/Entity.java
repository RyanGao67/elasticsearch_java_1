package com;


import java.util.HashSet;
import java.util.Set;

public class Entity implements Comparable<Entity> {
    private String entityHash;
    private String entityType;
    private String entityName;
    private Set<Tag> tags;

    public Entity() {
        this("", EntityType.unknown.getCode(), "");
    }

    public Entity(String entityHash, String entityType, String entityName) {
        this.entityHash = entityHash;
        this.entityType = entityType;
        this.entityName = entityName;
        this.tags = new HashSet();
    }

    public Entity(RiskyEntity entity) {
        this.entityHash = entity.getEntityHash();
        this.entityName = entity.getEntityName();
        this.entityType = entity.getEntityType();
        this.tags = new HashSet();
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

    public String getEntityName() {
        return this.entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public Set<Tag> getTags() {
        return this.tags;
    }

    public void setTags(Set<Tag> tags) {
        if (tags != null) {
            this.tags = tags;
        }
    }

    public int compareTo(Entity entity) {
        return this.getEntityName().compareTo(entity.getEntityName());
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Entity)) {
            return false;
        } else {
            Entity entity = (Entity)obj;
            boolean entityHashMatches = this.getEntityHash() == null && entity.getEntityHash() == null || this.getEntityHash() != null && this.getEntityHash().equals(entity.getEntityHash());
            boolean entityNameMatches = this.getEntityName() == null && entity.getEntityName() == null || this.getEntityName() != null && this.getEntityName().equals(entity.getEntityName());
            boolean entityTypeMatches = this.getEntityType() == null && entity.getEntityType() == null || this.getEntityType() != null && this.getEntityType().equals(entity.getEntityType());
            return entityHashMatches && entityNameMatches && entityTypeMatches;
        }
    }
}

