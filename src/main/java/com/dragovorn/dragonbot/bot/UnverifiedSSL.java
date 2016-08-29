package com.dragovorn.dragonbot.bot;

import javax.net.ssl.*;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 4:44 AM.
 * as of 8/6/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
class UnverifiedSSL {

    static SSLContext getUnverifedSSLContext() throws Exception {
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

        HostnameVerifier allHostsValid = new HostnameVerifier() { // Make lambda later

            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        };

        SSLContext context = SSLContext.getInstance("SSL");
        context.init(null, trustAllCerts, new SecureRandom());

        return context;
    }
}