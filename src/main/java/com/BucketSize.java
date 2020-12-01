package com;

import java.util.HashMap;
import java.util.Map;

public enum BucketSize {
    minutely(60L),
    hourly(3600L),
    daily(86400L),
    weekly(604800L),
    monthly(2592000L),
    all_time(2147483647L),
    unknown(-1L);

    private static final Map<Long, BucketSize> lookupBySizeInSeconds = new HashMap();
    private Long sizeInSeconds;

    private BucketSize(Long sizeInSeconds) {
        this.sizeInSeconds = sizeInSeconds;
    }

    public Long getSizeInSeconds() {
        return this.sizeInSeconds;
    }

    public static BucketSize lookupBySizeInSeconds(Long sizeInSeconds) {
        return (BucketSize)lookupBySizeInSeconds.get(sizeInSeconds);
    }

    static {
        BucketSize[] var0 = values();
        int var1 = var0.length;

        for(int var2 = 0; var2 < var1; ++var2) {
            BucketSize type = var0[var2];
            lookupBySizeInSeconds.put(type.getSizeInSeconds(), type);
        }

    }
}
