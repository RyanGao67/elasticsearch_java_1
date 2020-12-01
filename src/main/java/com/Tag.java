package com;

import java.util.*;

public class Tag extends ESHit implements Comparable<Tag> {
    private String id;
    private String name;
    private TagSource source;
    private String description;
    private long created;
    private String createdBy;
    private long modified;
    private String modifiedBy;
    private Set<String> usedBy;
    private List<String> entities;

    public Tag() {
        this.usedBy = new HashSet();
        this.entities = new ArrayList();
    }

    public Tag(String id, String name, TagSource source, String createdBy) {
        this(id, name, source, "", System.currentTimeMillis(), createdBy);
    }

    public Tag(String id, String name, TagSource source, String description, long created, String createdBy, long modified, String modifiedBy) {
        this(id, name, source, description, created, createdBy);
        this.modified = modified;
        this.modifiedBy = modifiedBy;
    }

    public Tag(String id, String name, TagSource source, String description, long created, String createdBy) {
        this.usedBy = new HashSet();
        this.entities = new ArrayList();
        this.id = id;
        this.name = name;
        this.source = source;
        this.description = description;
        this.created = created;
        this.createdBy = createdBy;
        this.modified = created;
        this.modifiedBy = createdBy;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TagSource getSource() {
        return this.source;
    }

    public void setSource(TagSource source) {
        this.source = source;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCreated() {
        return this.created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public long getModified() {
        return this.modified;
    }

    public void setModified(long modified) {
        this.modified = modified;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public List<String> getEntities() {
        return this.entities == null ? Collections.emptyList() : this.entities;
    }

    public void setEntities(List<String> entities) {
        this.entities = entities == null ? Collections.emptyList() : entities;
    }

    public Set<String> getUsedBy() {
        return this.usedBy;
    }

    public void setUsedBy(Set<String> usedBy) {
        this.usedBy = usedBy;
    }

    public void addUsedBy(String usedBy) {
        this.usedBy.add(usedBy);
    }

    public int compareTo(Tag t) {
        return this.getId().compareTo(t.getId());
    }
}
