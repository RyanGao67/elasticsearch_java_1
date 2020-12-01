package com;

import java.util.*;

public enum DataSource {
    repo("rp", "Repository"),
    vpn("vpn", "VPN"),
    share("sh", "Share Drive/File"),
    auth("ad", "Authentication"),
    endpoint("wdc", "Endpoint"),
    sensor("sen", "Endpoint"),
    violation("vl", "Violation"),
    printer("pt", "Printer"),
    netflow("nf", "NetFlow"),
    web("pxy", "Web Proxy"),
    common("all", "Common"),
    resource("rs", "Resource"),
    expense("fin", "Expense"),
    email("eml", "Email"),
    byom("bym", "BYOM"),
    cef("cef", "Common Event Format"),
    unknown("??", "Unknown");

    private String code;
    private String category;
    private static final Map<String, DataSource> lookupByCode = new HashMap();
    private static final Map<String, List<DataSource>> lookupByCategory;

    private DataSource(String code, String category) {
        this.code = code;
        this.category = category;
    }

    public String getCode() {
        return this.code;
    }

    public String getCategory() {
        return this.category;
    }

    public static DataSource lookupByCode(String code) {
        return (DataSource)lookupByCode.getOrDefault(code.trim(), unknown);
    }

    public static List<DataSource> lookupByCategory(String category) {
        return (List)lookupByCategory.getOrDefault(category, Collections.emptyList());
    }

    public static DataSource lookup(String str) {
        try {
            return valueOf(str);
        } catch (IllegalArgumentException var3) {
            DataSource t = (DataSource)lookupByCode.getOrDefault(str, lookupByCode.getOrDefault(str, unknown));
            if (t.equals(unknown)) {
                t = (DataSource)lookupByCategory(str).stream().findFirst().orElse(unknown);
            }

            return t;
        }
    }

    static {
        DataSource[] var0 = values();
        int var1 = var0.length;

        int var2;
        DataSource type;
        for(var2 = 0; var2 < var1; ++var2) {
            type = var0[var2];
            lookupByCode.put(type.getCode(), type);
        }

        lookupByCategory = new HashMap();
        var0 = values();
        var1 = var0.length;

        for(var2 = 0; var2 < var1; ++var2) {
            type = var0[var2];
            List<DataSource> datasources = (List)lookupByCategory.getOrDefault(type.getCategory(), new ArrayList());
            datasources.add(type);
            lookupByCategory.put(type.getCategory(), datasources);
        }

        lookupByCategory.put("Active Directory", Collections.singletonList(auth));
    }
}

