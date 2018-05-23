package io.jbehavetutorials.testutility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.security.cert.CertificateException;

/**
 * Created by prasantabiswas on 23/05/18.
 */
public class HelperClass {
    Logger logger = LogManager.getLogger(HelperClass.class);
    private static final HelperClass instance = new HelperClass();

    private HelperClass()
    {

    }

    public static HelperClass getInstance()
    {
        return instance;
    }

    public String sendHttpGet(String Url) throws IOException
    {
        URL url = new URL(Url);
        HttpURLConnection con;
        BufferedReader bufferedReader;
        if(url.getProtocol().contains("https"))
        {
            disableSSLCertificateChecking();
            con = (HttpsURLConnection) url.openConnection();
        }
        else
        {
            con = (HttpURLConnection) url.openConnection();
        }

        // optional default is GET
        con.setRequestMethod("GET");
        con.connect();

        // add request header

        int responseCode = con.getResponseCode();
        logger.info("Sending 'GET' request to URL : " + url);
        logger.info("Response Code : " + responseCode);

        if(responseCode == 200) {
            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        }
        else
        {
            bufferedReader = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        }
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = bufferedReader.readLine()) != null)
        {
            response.append(inputLine);
        }
        bufferedReader.close();
        con.disconnect();
        logger.debug("HTTP Response: "+response);
        return response.toString();
    }

    public void disableSSLCertificateChecking()
    {
        try
        {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509ExtendedTrustManager()
                    {
                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers()
                        {
                            return null;
                        }

                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType)
                        {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType)
                        {
                        }

                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] xcs, String string, Socket socket) throws CertificateException
                        {

                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] xcs, String string, Socket socket) throws CertificateException
                        {

                        }

                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] xcs, String string, SSLEngine ssle) throws CertificateException
                        {

                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] xcs, String string, SSLEngine ssle) throws CertificateException
                        {

                        }

                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new  HostnameVerifier()
            {
                @Override
                public boolean verify(String hostname, SSLSession session)
                {
                    return true;
                }
            };
            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        }
        catch (Exception e)
        {
            logger.error("Error occurred",e);
        }
    }
}
