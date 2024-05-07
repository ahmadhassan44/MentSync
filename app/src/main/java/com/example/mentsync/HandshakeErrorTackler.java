package com.example.mentsync;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HandshakeErrorTackler {
    public static void fixerror()
    {
        try {
            // Set the hostname verifier to accept any hostname
            HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);

            // Create a new SSL context that accepts all certificates
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{
                    new X509TrustManager() {
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                        public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
                    }
            }, new SecureRandom());

            // Set the default SSL socket factory to the custom SSL context
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
        }
    }
}
