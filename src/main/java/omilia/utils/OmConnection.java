package omilia.utils;

import Telegram.Bot;
import org.springframework.beans.factory.annotation.Autowired;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

public class OmConnection {


    @Autowired Bot bot;

    public OmConnection(Bot bot,String dialogId) {
        this.dialogId = dialogId;
        this.bot=bot;
    }

    private String dialogId;

    public HttpsURLConnection get() {
        HttpsURLConnection connection = null;
        try {
            disableCertificateChecking();
            connection = (HttpsURLConnection) getURL().openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", bot.appProperty.getTOKEN());
            connection.setDoOutput(true);
        } catch (NoSuchAlgorithmException | KeyManagementException | IOException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private URL getURL() throws MalformedURLException {
        StringBuilder url = new StringBuilder(bot.appProperty.getURL());
        if (dialogId != null) {
            url.append(dialogId);
        }
        return new URL(url.toString());
    }

    private void disableCertificateChecking() throws NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {

                    }
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {

                    }
                }
        };
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        HostnameVerifier allHostsValid = (hostname, session) -> true;
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    }
}
