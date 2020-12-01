package com;

public enum ContextType {
    volumetric,
    volumetricCluster,
    mooching,
    workingHours,
    workingDays,
    rare,
    rareUser,
    accessRatio,
    accessRatioCluster,
    rareNetflow,
    none,
    missing,
    notImplemented,
    geoVelocity;

    private ContextType() {
    }
}
