package com;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import sun.net.www.content.text.Generic;

import java.util.ArrayList;
import java.util.List;

public class ParsedAlert implements Comparable {
    private String id;
    private DataSource ds;
    private long timestamp;
    private double risk;
    private double contribution;
    private double significance;
    private String category;
    private Templates templates;
    private Generic generic;
    private Kibana kibana;
    private SQL sql;
    private ContextType contextType;
    private PotentialThreat threat;
    private Family family;
    private Unit unit;
    private BucketSize bucketSize;
    private List<Integer> anomalyTypes;
    private String parentId;
    private long numChildren;
    private AnomaliesRollupLevel rollupLevel;
    private String topAnomalyId;
    private List<AlertTag> tags;
    private Recon recon;

    private ParsedAlert() {
        this.id = "";
        this.templates = new Templates();
        this.generic = new Generic();
        this.kibana = new Kibana();
        this.sql = new SQL();
        this.contextType = ContextType.none;
        this.threat = new PotentialThreat();
        this.family = new Family();
        this.bucketSize = BucketSize.hourly;
        this.anomalyTypes = new ArrayList();
        this.recon = new Recon();
    }

    public String getId() {
        return this.id;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public double getRisk() {
        return this.risk;
    }

    public double getContribution() {
        return this.contribution;
    }

    public double getSignificance() {
        return this.significance;
    }

    public DataSource getDs() {
        return this.ds;
    }

    public String getCategory() {
        return this.category;
    }

    public Templates getTemplates() {
        return this.templates;
    }

    public Generic getGeneric() {
        return this.generic;
    }

    public Kibana getKibana() {
        return this.kibana;
    }

    public SQL getSql() {
        return this.sql;
    }

    public ContextType getContextType() {
        return this.contextType;
    }

    public PotentialThreat getThreat() {
        return this.threat;
    }

    public Family getFamily() {
        return this.family;
    }

    public Unit getUnit() {
        return this.unit;
    }

    public List<Integer> getAnomalyTypes() {
        return this.anomalyTypes;
    }

    public BucketSize getBucketSize() {
        return this.bucketSize;
    }

    public long getNumChildren() {
        return this.numChildren;
    }

    public AnomaliesRollupLevel getRollupLevel() {
        return this.rollupLevel;
    }

    public String getParentId() {
        return this.parentId;
    }

    public String getTopAnomalyId() {
        return this.topAnomalyId;
    }

    public List<AlertTag> getTags() {
        return this.tags;
    }

    public Recon getRecon() {
        return this.recon;
    }

    public int compareTo(Object obj) {
        if (obj instanceof ParsedAlert) {
            ParsedAlert alert2 = (ParsedAlert)obj;
            long timestampDiff = alert2.getTimestamp() - this.getTimestamp();
            if (timestampDiff != 0L) {
                return (int)timestampDiff;
            } else {
                int riskDiff = Double.valueOf(alert2.getRisk() - this.risk).intValue();
                if (riskDiff == 0) {
                    return alert2.getContribution() == this.getContribution() ? alert2.getTemplates().teaser.compareTo(this.getTemplates().teaser) : Double.valueOf(alert2.getContribution() - this.getContribution()).intValue();
                } else {
                    return riskDiff;
                }
            }
        } else {
            return -1;
        }
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            ParsedAlert that = (ParsedAlert)o;
            return (new EqualsBuilder()).append(this.timestamp, that.timestamp).append(this.risk, that.risk).append(this.contribution, that.contribution).append(this.significance, that.significance).append(this.numChildren, that.numChildren).append(this.id, that.id).append(this.ds, that.ds).append(this.category, that.category).append(this.templates, that.templates).append(this.generic, that.generic).append(this.kibana, that.kibana).append(this.sql, that.sql).append(this.contextType, that.contextType).append(this.threat, that.threat).append(this.family, that.family).append(this.unit, that.unit).append(this.bucketSize, that.bucketSize).append(this.anomalyTypes, that.anomalyTypes).append(this.parentId, that.parentId).append(this.rollupLevel, that.rollupLevel).append(this.topAnomalyId, that.topAnomalyId).append(this.tags, that.tags).append(this.recon, that.recon).isEquals();
        } else {
            return false;
        }
    }

    public int hashCode() {
        return (new HashCodeBuilder(17, 37)).append(this.id).append(this.ds).append(this.timestamp).append(this.risk).append(this.contribution).append(this.significance).append(this.category).append(this.templates).append(this.generic).append(this.kibana).append(this.sql).append(this.contextType).append(this.threat).append(this.family).append(this.unit).append(this.bucketSize).append(this.anomalyTypes).append(this.parentId).append(this.numChildren).append(this.rollupLevel).append(this.topAnomalyId).append(this.tags).append(this.recon).toHashCode();
    }

    public static class Builder {
        ParsedAlert parsedAlert = new ParsedAlert();

        public Builder(List<Integer> anomalyTypes, AggregateAlertTemplate template, String topAnomalyId) {
            this.parsedAlert.templates = template.templates;
            this.parsedAlert.generic = template.generic;
            this.parsedAlert.kibana = template.kibana;
            this.parsedAlert.sql = template.sql;
            this.parsedAlert.category = template.datasource.getCategory();
            this.parsedAlert.threat = template.threat;
            this.parsedAlert.family = template.family;
            this.parsedAlert.unit = template.unit;
            this.parsedAlert.contextType = template.contextType;
            this.parsedAlert.bucketSize = template.bucketSize;
            this.parsedAlert.anomalyTypes = anomalyTypes;
            this.parsedAlert.ds = template.datasource;
            this.parsedAlert.topAnomalyId = topAnomalyId;
            this.parsedAlert.recon = template.recon;
        }

        public Builder(AbstractAnomaly a, DataSource datasource) {
            this.parsedAlert.id = a.getId();
            this.parsedAlert.timestamp = a.getTimestamp();
            this.parsedAlert.risk = a.getRisk();
            this.parsedAlert.contribution = a.getContribution();
            this.parsedAlert.significance = a.getSignificance();
            this.parsedAlert.category = datasource.getCategory();
            this.parsedAlert.anomalyTypes = a.getAnomalyTypes();
            this.parsedAlert.ds = datasource;
            this.parsedAlert.rollupLevel = a.getRollupLevel();
            this.parsedAlert.parentId = a.getParentId();
            this.parsedAlert.numChildren = a.getNumChildren();
            this.parsedAlert.topAnomalyId = a.getTopAnomalyId();
        }

        public ParsedAlert.Builder setAnomalyTypes(List<Integer> anomalyTypes) {
            this.parsedAlert.anomalyTypes = anomalyTypes;
            return this;
        }

        public ParsedAlert.Builder setTemplates(Templates templates) {
            this.parsedAlert.templates = templates;
            return this;
        }

        public ParsedAlert.Builder setGeneric(Generic generic) {
            this.parsedAlert.generic = generic;
            return this;
        }

        public ParsedAlert.Builder setKibana(Kibana kibana) {
            this.parsedAlert.kibana = kibana;
            return this;
        }

        public ParsedAlert.Builder setSql(SQL sql) {
            this.parsedAlert.sql = sql;
            return this;
        }

        public ParsedAlert.Builder setContextType(ContextType contextType) {
            this.parsedAlert.contextType = contextType;
            return this;
        }

        public ParsedAlert.Builder setThreat(PotentialThreat threat) {
            this.parsedAlert.threat = threat;
            return this;
        }

        public ParsedAlert.Builder setFamily(Family family) {
            this.parsedAlert.family = family;
            return this;
        }

        public ParsedAlert.Builder setUnit(Unit unit) {
            this.parsedAlert.unit = unit;
            return this;
        }

        public ParsedAlert.Builder setNumChildren(long numChildren) {
            this.parsedAlert.numChildren = numChildren;
            return this;
        }

        public ParsedAlert.Builder setBucketSize(BucketSize size) {
            this.parsedAlert.bucketSize = size;
            return this;
        }

        public ParsedAlert.Builder setTags(List<AlertTag> tags) {
            this.parsedAlert.tags = tags;
            return this;
        }

        public ParsedAlert build() {
            return this.parsedAlert;
        }

        public ParsedAlert.Builder setRecon(Recon recon) {
            this.parsedAlert.recon = recon;
            return this;
        }
    }
}
