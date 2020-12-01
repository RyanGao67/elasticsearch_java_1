package com;

import java.util.Comparator;

public class AlertTag implements Comparable<AlertTag> {
    private String id;
    private String name;
    private TagSource source;
    private long createdAt;
    private String createdBy;

    public AlertTag() {
    }

    public AlertTag(String id, String name, TagSource source, String createdBy) {
        this(id, name, source, System.currentTimeMillis(), createdBy);
    }

    public AlertTag(Tag tag, String createdBy) {
        this(tag.getId(), tag.getName(), tag.getSource(), System.currentTimeMillis(), createdBy);
    }

    public AlertTag(String id, String name, TagSource source, long createdAt, String createdBy) {
        this.id = id;
        this.name = name;
        this.source = source;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
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

    public long getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public int compareTo(AlertTag t) {
        int matchesId = t == null ? -1 : Comparator.comparing(AlertTag::getId).compare(this, t);
        if (matchesId == 0) {
            return this.getCreatedBy() != null && !this.getCreatedBy().isEmpty() && t.getCreatedBy() != null && !t.getCreatedBy().isEmpty() ? Comparator.comparing(AlertTag::getCreatedBy).compare(this, t) : matchesId;
        } else {
            return matchesId;
        }
    }

    public boolean equals(Object obj) {
        if (obj instanceof AlertTag) {
            return this.compareTo((AlertTag)obj) == 0;
        } else {
            return false;
        }
    }
}
