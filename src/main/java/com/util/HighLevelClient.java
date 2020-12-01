package com.util;

import com.ElasConfig;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.security.*;
import java.util.Arrays;

public class HighLevelClient {
     public RestHighLevelClient getInstance(ElasConfig config){

         //RestClient.builder(new HttpHost("localhost", 9200, "http"))
         RestClientBuilder builder = RestClient.builder(
                 (HttpHost[])
                         config.extractESHosts().stream().map(
                                 (http) -> {
                                     return new HttpHost(http.hostname, http.port, config.esEnableSSL ? "https" : "http");
                                 }
                         ).toArray(
                                 (x$0) -> {
                                     return new HttpHost[x$0];
                                 }
                         )
         ).setRequestConfigCallback(
                 (requestConfigBuilder) -> {
                     return requestConfigBuilder.setConnectTimeout(config.getESConnectTimeout()).setSocketTimeout(config.getESSocketTimeout());
                 }
         );
         builder.setHttpClientConfigCallback((httpClientBuilder) -> {
             try {
                 if (config.needsKeystore()) {
                     this.addKeystore(config, httpClientBuilder, config.getESKeystorePath(), config.getESKeystorePassword());
                 }

                 if (config.needsTruststore()) {
                     this.addKeystore(config, httpClientBuilder, config.getESTruststorePath(), config.getESTruststorePassword());
                 }

                 if (config.needsAuth()) {
                     this.addCredentials(config, httpClientBuilder);
                 }

                 if (config.esAllowSelfSigned) {
                     this.enableSelfSignedCertificates(httpClientBuilder);
                 }

                 return httpClientBuilder;
             } catch (IOException var4) {
                 throw new UncheckedIOException(var4);
             }
         });
        return new RestHighLevelClient(
                builder
        );
     }


    private void addKeystore(ElasConfig config, HttpAsyncClientBuilder httpClientBuilder, String path, String password) throws IOException {
        try {
            SSLContext sslContext = createSSLContext(path, password);
            httpClientBuilder.setSSLContext(sslContext);
        } catch (GeneralSecurityException | IOException var6) {
            throw new IOException("Failed to load keystore '" + config.esKeystorePath + "': " + var6.toString());
        }
    }

    private void enableSelfSignedCertificates(HttpAsyncClientBuilder httpClientBuilder) throws IOException {
        try {
            httpClientBuilder.setSSLHostnameVerifier(new NoopHostnameVerifier());
            httpClientBuilder.setSSLContext(SSLContexts.custom().loadTrustMaterial(new TrustAllStrategy()).build());
        } catch (KeyManagementException | KeyStoreException | NoSuchAlgorithmException var3) {
            throw new IOException("Failed to enable allowing self-signed certificates: " + var3.toString());
        }
    }

    private static SSLContext createSSLContext(String keystore, String password) throws GeneralSecurityException, IOException {
        SSLContext sslContext = SSLContext.getInstance("TLS");
        FileInputStream inputStream = new FileInputStream(keystore);
        Throwable var5 = null;

        KeyStore truststore;
        try {
            truststore = KeyStore.getInstance(KeyStore.getDefaultType());
            truststore.load(inputStream, password.toCharArray());
        } catch (Throwable var15) {
            var5 = var15;
            throw var15;
        } finally {
            if (inputStream != null) {
                if (var5 != null) {
                    try {
                        inputStream.close();
                    } catch (Throwable var14) {
                        var5.addSuppressed(var14);
                    }
                } else {
                    inputStream.close();
                }
            }

        }

        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(truststore);
        X509TrustManager customTrustManager = null;
        TrustManager[] var6 = tmf.getTrustManagers();
        int var7 = var6.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            TrustManager tm = var6[var8];
            if (tm instanceof X509TrustManager) {
                customTrustManager = (X509TrustManager)tm;
                break;
            }
        }

        sslContext.init((KeyManager[])null, new TrustManager[]{customTrustManager}, (SecureRandom)null);
        return sslContext;
    }
    private void addCredentials(ElasConfig config, HttpAsyncClientBuilder httpAsyncClientBuilder) {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(config.esUser, config.esPassword));
        httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
    }
}
