package com;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ElasConfig {
    private static Logger LOG = LoggerFactory.getLogger(ElasConfig.class);
    private static final String ES_HOSTS = "esHosts";
    private static final String ES_PORT = "esPort";
    private static final String ES_USER = "esUser";
    private static final String ES_PASSWORD = "esPassword";
    private static final String ES_ENABLE_SSL = "esEnableSSL";
    private static final String ES_KEYSTORE_PATH = "esKeystorePath";
    private static final String ES_KEYSTORE_PASSWORD = "esKeystorePassword";
    private static final String ES_TRUSTSTORE_PATH = "esTruststorePath";
    private static final String ES_TRUSTSTORE_PASSWORD = "esTruststorePassword";
    private static final String ES_CONNECT_TIMEOUT = "esConnectTimeout";
    private static final String ES_SOCKET_TIMEOUT = "esTimeout";
    private static final String ES_RETRY_TIMEOUT = "esRetryTimeout";
    private static final String ES_ALLOW_SELF_SIGNED = "esAllowSelfSigned";
    private static final int DEFAULT_ES_PORT = 9200;
    private static final String DEFAULT_ES_HOST = "localhost";
    private static final boolean DEFAULT_ES_ENABLE_SSL = false;
    private static final int DEFAULT_CONNECT_TIMEOUT = 5000;
    private static final int DEFAULT_SOCKET_TIMEOUT = 60000;
    private static final int DEFAULT_RETRY_TIMEOUT = 60000;
    private static final String DEFAULT_STRING = "";
    private static final boolean DEFAULT_ES_ALLOW_SELF_SIGNED = false;
    @JsonProperty("esPort")
    public int esPort;
    @JsonProperty("esHosts")
    public String esHosts;
    @JsonProperty("esUser")
    public String esUser;
    @JsonProperty("esPassword")
    public String esPassword;
    @JsonProperty("esEnableSSL")
    public boolean esEnableSSL;
    @JsonProperty("esKeystorePath")
    public String esKeystorePath;
    @JsonProperty("esKeystorePassword")
    public String esKeystorePassword;
    @JsonProperty("esTruststorePath")
    public String esTruststorePath;
    @JsonProperty("esTruststorePassword")
    public String esTruststorePassword;
    @JsonProperty("esConnectTimeout")
    public int esConnectTimeout;
    @JsonProperty("esSocketTimeout")
    public int esSocketTimeout;
    @JsonProperty("esRetryTimeout")
    public int esRetryTimeout;
    @JsonProperty("esAllowSelfSigned")
    public boolean esAllowSelfSigned;

    public ElasConfig() {
        this.esPort = 9200;
        this.esHosts = "localhost";
        this.esUser = "";
        this.esPassword = "";
        this.esEnableSSL = false;
        this.esKeystorePath = "";
        this.esKeystorePassword = "";
        this.esTruststorePath = "";
        this.esTruststorePassword = "";
        this.esConnectTimeout = 5000;
        this.esSocketTimeout = 60000;
        this.esRetryTimeout = 60000;
    }


    public ElasConfig(Map<String, Object> apiConfig) {
        this.esPort = this.parseAPIIntegerOption(apiConfig, "esPort", 9200);
        this.esHosts = (String)apiConfig.getOrDefault("esHosts", "localhost");
        this.esUser = (String)apiConfig.getOrDefault("esUser", "");
        this.esPassword = (String)apiConfig.getOrDefault("esPassword", "");
        this.esEnableSSL = (Boolean)apiConfig.getOrDefault("esEnableSSL", false);
        this.esKeystorePath = (String)apiConfig.getOrDefault("esKeystorePath", "");
        this.esKeystorePassword = (String)apiConfig.getOrDefault("esKeystorePassword", "");
        this.esTruststorePath = (String)apiConfig.getOrDefault("esTruststorePath", "");
        this.esTruststorePassword = (String)apiConfig.getOrDefault("esTruststorePassword", "");
        this.esConnectTimeout = this.parseAPIIntegerOption(apiConfig, "esConnectTimeout", 5000);
        this.esSocketTimeout = this.parseAPIIntegerOption(apiConfig, "esTimeout", 60000);
        this.esRetryTimeout = this.parseAPIIntegerOption(apiConfig, "esRetryTimeout", 60000);
        this.esAllowSelfSigned = (Boolean)apiConfig.getOrDefault("esAllowSelfSigned", false);
    }

    private int parseAPIIntegerOption(Map<String, Object> apiConfig, String key, int defaultValue) {
        try {
            return (Integer)apiConfig.getOrDefault(key, defaultValue);
        } catch (Exception var5) {
            LOG.info("Unable to parse {} to integer with value {}. Using default value {}", new Object[]{key, apiConfig.get(key), defaultValue});
            return defaultValue;
        }
    }


    public List<Host> extractESHosts() {
        String hosts = this.esHosts;
        int defaultPort = this.esPort;
        return (List) Arrays.stream(hosts.split(",")).map((host) -> {
            return host.split(":");
        }).map((hostParts) -> {
            int port = defaultPort;
            if (hostParts.length > 1) {
                try {
                    port = Integer.parseInt(hostParts[1].trim());
                } catch (NumberFormatException var4) {
                    LOG.warn("Unable to parse {} to valid port. Defaulting to {}.", new Object[]{hostParts[1], defaultPort, var4});
                }
            }

            return new ElasConfig.Host(hostParts[0].trim(), port);
        }).collect(Collectors.toList());
    }

    public void setUserAndPassword(String userAndPassword) {
        if (userAndPassword == null || userAndPassword.indexOf(":") < 0) {
            LOG.warn("username and password not set");
        }

        this.esUser = userAndPassword.substring(0, userAndPassword.indexOf(":"));
        this.esPassword = userAndPassword.substring(userAndPassword.indexOf(":") + 1);
    }

    public boolean needsAuth() {
        return this.esUser != null && !this.esUser.isEmpty() && this.esPassword != null && !this.esPassword.isEmpty();
    }

    public boolean needsKeystore() {
        return this.esKeystorePath != null && !this.esKeystorePath.isEmpty() && this.esKeystorePassword != null && !this.esKeystorePassword.isEmpty();
    }

    public boolean needsTruststore() {
        return this.esTruststorePath != null && !this.esTruststorePath.isEmpty() && this.esTruststorePassword != null && !this.esTruststorePassword.isEmpty();
    }

    public int getESPort() {
        return this.esPort;
    }

    public void setESPort(int esPort) {
        this.esPort = esPort;
    }

    public String getESHosts() {
        return this.esHosts;
    }

    public void setESHosts(String esHosts) {
        this.esHosts = esHosts;
    }

    public String getESUser() {
        return this.esUser;
    }

    public void setESUser(String esUser) {
        this.esUser = esUser;
    }

    public String getESPassword() {
        return this.esPassword;
    }

    public void setESPassword(String esPassword) {
        this.esPassword = esPassword;
    }

    public boolean isSSLEnabled() {
        return this.esEnableSSL;
    }

    public void setESEnableSSL(boolean esEnableSSL) {
        this.esEnableSSL = esEnableSSL;
    }

    public String getESKeystorePath() {
        return this.esKeystorePath;
    }

    public void setESKeystorePath(String esKeystorePath) {
        this.esKeystorePath = esKeystorePath;
    }

    public String getESKeystorePassword() {
        return this.esKeystorePassword;
    }

    public void setESKeystorePassword(String esKeystorePassword) {
        this.esKeystorePassword = esKeystorePassword;
    }

    public String getESTruststorePath() {
        return this.esTruststorePath;
    }

    public void setESTruststorePath(String esTruststorePath) {
        this.esTruststorePath = esTruststorePath;
    }

    public String getESTruststorePassword() {
        return this.esTruststorePassword;
    }

    public void setESTruststorePassword(String esTruststorePassword) {
        this.esTruststorePassword = esTruststorePassword;
    }

    public int getESConnectTimeout() {
        return this.esConnectTimeout;
    }

    public void setESConnectTimeout(int esConnectTimeout) {
        this.esConnectTimeout = esConnectTimeout;
    }

    public int getESSocketTimeout() {
        return this.esSocketTimeout;
    }

    public void setESSocketTimeout(int esSocketTimeout) {
        this.esSocketTimeout = esSocketTimeout;
    }

    public int getESRetryTimeout() {
        return this.esRetryTimeout;
    }

    public void setESRetryTimeout(int esRetryTimeout) {
        this.esRetryTimeout = esRetryTimeout;
    }

    public boolean isAllowSelfSigned() {
        return this.esAllowSelfSigned;
    }

    public void setAllowSelfSigned(boolean esAllowSelfSigned) {
        this.esAllowSelfSigned = esAllowSelfSigned;
    }

    public static class Host {
        public String hostname;
        public int port;

        Host(String hostname, int port) {
            this.hostname = hostname;
            this.port = port;
        }
    }
}
