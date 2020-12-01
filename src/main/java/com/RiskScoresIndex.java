package com;

public interface RiskScoresIndex {
    String RISK_SCORES_INDEX_NAME = "risk_scores";

    class Fields {
        private Fields() {}
        public static final String TIMESTAMP = "timestamp";
        public static final String SCORE = "score";
        public static final String ENTITYTYPE = "entityType";
        public static final String ENTITYTYPE_RAW = "entityType.raw";
        public static final String ENTITYNAME = "entityName";
        public static final String ENTITYNAME_RAW = "entityName.raw";
        public static final String ENTITYHASH = "entityHash";

        public static final String HAS_ANOMALIES = "hasAnomalies";
    }
}

