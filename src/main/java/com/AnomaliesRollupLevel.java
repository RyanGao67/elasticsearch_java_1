package com;

public enum AnomaliesRollupLevel {
    anomalies("anomalyid", (AnomaliesRollupLevel)null),
    alerts("alertid", anomalies),
    aggregates("aggid", alerts);

    private AnomaliesRollupLevel children;
    private String queryFieldName;

    private AnomaliesRollupLevel(String queryFieldName, AnomaliesRollupLevel children) {
        this.queryFieldName = queryFieldName;
        this.children = children;
    }

    public AnomaliesRollupLevel getChildrenRollupLevel() {
        return this.children;
    }

    public String getQueryFieldName() {
        return this.queryFieldName;
    }
}
