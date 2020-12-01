package com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public enum EntityType {
    file("fil", "fileid", "ALL_FILES", true),
    machine("mac", "machineid", "ALL_MACHINES", true),
    project("pro", "projectid", "ALL_PROJECTS", true),
    controller("srv", "controllerid", "ALL_SERVERS", true),
    server("sr2", "serverid", "ALL_SERVER2S", true),
    user("usr", "userid", "ALL_USERS", true),
    volume("vol", "volumeid", "ALL_VOLUMES", false),
    application("app", "applicationid", "ALL_APPLICATIONS", false),
    organisation("org", "organisationid", "ALL_ORGANISATIONS", false),
    bot("bot", "botid", "ALL_BOTS", false),
    printer("prt", "printerid", "ALL_PRINTERS", true),
    share("shr", "shareid", "ALL_SHARES", true),
    resource("res", "resourceid", "ALL_RESOURCES", true),
    website("web", "websiteid", "ALL_WEBSITES", true),
    host("nf", "hostid", "ALL_HOSTS", false),
    cluster("cls", "clusterid", "ALL_CLUSTERS", false),
    country("cty", "countryid", "ALL_COUNTRIES", false),
    category("cat", "categoryid", "ALL_CATEGORIES", false),
    email("ems", "emailid", "ALL_EMAILS", false),
    ip("ip", "ipid", "ALL_IPS", true, "IP Address"),
    identifier("ids", "idName", "ALL_IDENTIFIERS", false),
    relation("rel", "relation", "ALL_RELATIONS", false),
    port("ppt", "portid", "ALL_PORTS", false),
    remote("rem", "remoteid", "ALL_REMOTES", false),
    useragent("uas", "agentid", "ALL_USERAGENTS", false),
    browser("bwr", "browserid", "ALL_BROWSERS", false),
    operatingsystem("os", "operatingsystemid", "ALL_OPERATINGSYSTEMS", false),
    device("dvc", "deviceid", "ALL_DEVICES", false),
    unknown("???", "unknown", "ALL_UNKNOWN", false);

    private static final Logger LOG = LoggerFactory.getLogger(EntityType.class);
    private static final Map<String, EntityType> lookupByCode = new HashMap();
    private static final Map<String, EntityType> lookupByIdString;
    private String code;
    private String idStr;
    private String genericName;
    private boolean scored;
    private String defaultDisplayName;

    private EntityType(String code, String idStr, String genericName, boolean scored, String defaultDisplayName) {
        this.code = code;
        this.idStr = idStr;
        this.genericName = genericName;
        this.scored = scored;
        this.defaultDisplayName = defaultDisplayName;
    }

    private EntityType(String code, String idStr, String genericName, boolean scored) {
        this.code = code;
        this.idStr = idStr;
        this.genericName = genericName;
        this.scored = scored;
    }

    public String getCode() {
        return this.code;
    }

    public String getIdString() {
        return this.idStr;
    }

    public String getAggregateEntityId() {
        return this.genericName;
    }

    public String getDefaultDisplayName() {
        return this.defaultDisplayName == null ? capitalize(this.name()) : this.defaultDisplayName;
    }

    private static String capitalize(String value) {
        return value.substring(0, 1).toUpperCase() + value.substring(1);
    }

    public static EntityType lookupByCode(String code) {
        return (EntityType)lookupByCode.get(code);
    }

    public static EntityType lookupByIdString(String idStr) {
        return (EntityType)lookupByIdString.get(idStr);
    }

    public static EntityType lookup(String str) {
        try {
            return valueOf(str);
        } catch (IllegalArgumentException var5) {
            EntityType t = (EntityType)lookupByIdString.getOrDefault(str, lookupByCode.getOrDefault(str, unknown));
            if (t.equals(unknown)) {
                try {
                    t = EntityType.Plural.valueOf(str).getType();
                } catch (IllegalArgumentException var4) {
                    t = unknown;
                }
            }

            if (t.equals(unknown)) {
                LOG.info("Unable to parse {} to a known entity type", str);
            }

            return t;
        }
    }

    public static List<EntityType> getScoredEntities() {
        List<EntityType> types = new ArrayList();
        EntityType[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            EntityType type = var1[var3];
            if (type.scored) {
                types.add(type);
            }
        }

        return types;
    }

    public static String[] getCodes() {
        return (String[])((List)Arrays.stream(values()).map(EntityType::getCode).collect(Collectors.toList())).toArray(new String[values().length]);
    }

    static {
        EntityType[] var0 = values();
        int var1 = var0.length;

        int var2;
        EntityType type;
        for(var2 = 0; var2 < var1; ++var2) {
            type = var0[var2];
            lookupByCode.put(type.getCode(), type);
        }

        lookupByIdString = new HashMap();
        var0 = values();
        var1 = var0.length;

        for(var2 = 0; var2 < var1; ++var2) {
            type = var0[var2];
            lookupByIdString.put(type.getIdString(), type);
        }

    }

    public static enum Plural {
        files(EntityType.file),
        machines(EntityType.machine),
        projects(EntityType.project),
        controllers(EntityType.controller),
        servers(EntityType.server),
        users(EntityType.user),
        volumes(EntityType.volume),
        applications(EntityType.application),
        organisations(EntityType.organisation),
        bots(EntityType.bot),
        printers(EntityType.printer),
        shares(EntityType.share),
        resources(EntityType.resource),
        websites(EntityType.website),
        hosts(EntityType.host),
        clusters(EntityType.cluster),
        countries(EntityType.country),
        categories(EntityType.category),
        emails(EntityType.email),
        ips(EntityType.ip, "IP Addresses"),
        ports(EntityType.port),
        relations(EntityType.relation),
        remotes(EntityType.remote),
        useragents(EntityType.useragent),
        browsers(EntityType.browser),
        operatingsystems(EntityType.operatingsystem),
        devices(EntityType.device),
        unknowns(EntityType.unknown);

        private EntityType entityType;
        private String defaultDisplayName;

        private Plural(EntityType entityType, String defaultDisplayName) {
            this.entityType = entityType;
            this.defaultDisplayName = defaultDisplayName;
        }

        private Plural(EntityType entityType) {
            this.entityType = entityType;
        }

        public EntityType getType() {
            return this.entityType;
        }

        public static EntityType.Plural fromEntityType(EntityType type) {
            switch(type) {
                case file:
                    return files;
                case machine:
                    return machines;
                case project:
                    return projects;
                case controller:
                    return controllers;
                case server:
                    return servers;
                case user:
                    return users;
                case volume:
                    return volumes;
                case application:
                    return applications;
                case organisation:
                    return organisations;
                case bot:
                    return bots;
                case printer:
                    return printers;
                case share:
                    return shares;
                case resource:
                    return resources;
                case website:
                    return websites;
                case host:
                    return hosts;
                case cluster:
                    return clusters;
                case country:
                    return countries;
                case category:
                    return categories;
                case email:
                    return emails;
                case port:
                    return ports;
                case ip:
                    return ips;
                case relation:
                    return relations;
                case remote:
                    return remotes;
                case useragent:
                    return useragents;
                case browser:
                    return browsers;
                case operatingsystem:
                    return operatingsystems;
                case device:
                    return devices;
                default:
                    return unknowns;
            }
        }

        public String getDefaultDisplayName() {
            return this.defaultDisplayName == null ? EntityType.capitalize(this.name()) : this.defaultDisplayName;
        }

        public static List<String> names() {
            EntityType.Plural[] types = values();
            Set<String> names = new HashSet();
            Set<EntityType.Plural> exclude = new HashSet();
            exclude.add(organisations);
            exclude.add(clusters);
            exclude.add(unknowns);
            EntityType.Plural[] var3 = types;
            int var4 = types.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                EntityType.Plural type = var3[var5];
                if (!exclude.contains(type)) {
                    names.add(type.name());
                }
            }

            return (List)names.stream().sorted().collect(Collectors.toList());
        }
    }
}
