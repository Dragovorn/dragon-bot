package com.dragovorn.dragonbot.bot.connection;

import com.dragovorn.dragonbot.api.bot.connection.IConnection;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public final class Connection implements IConnection {

    public static class Builder {

        private String username;
        private String password;
        private String channel;

        private boolean SSL;
        private boolean verifySSL;

        private Builder() { }

        public Builder withUsername(String username) {
            this.username = username;

            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;

            return this;
        }

        public Builder withChannel(String channel) {
            this.channel = channel;

            return this;
        }

        public Builder withSSL() {
            this.SSL = true;

            return this;
        }

        public Builder withSSLVerification() {
            this.verifySSL = true;

            return this;
        }

        public Connection build() {
            return new Connection(this);
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    private final String username;
    private final String password;
    private final String channel;

    private final boolean SSL;
    private final boolean verifySSL;

    private Connection(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.channel = builder.channel;
        this.SSL = builder.SSL;
        this.verifySSL = builder.verifySSL;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getChannel() {
        return this.channel;
    }

    @Override
    public boolean isSSL() {
        return this.SSL;
    }

    @Override
    public boolean shouldVerifySSL() {
        return this.verifySSL;
    }

    public static SSLContext getUnverifedSSLContext() throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException { /* NOTHING */ }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException { /* NOTHING */ }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        }};

        SSLContext context = SSLContext.getInstance("SSL");
        context.init(null, trustAllCerts, new SecureRandom());

        return context;
    }
}
