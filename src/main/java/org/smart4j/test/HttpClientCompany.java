package org.smart4j.test;

import org.apache.http.auth.AuthSchemeProvider;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.impl.auth.DigestSchemeFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by jinli on 2016/7/11.
 */
public class HttpClientCompany {

    public static void main(String[] args){
        HttpGet getArticles = new HttpGet("http://16.6.0.13:8000/sap/bc/gui/sap/its/webgui");

        Registry<AuthSchemeProvider> authSchemeRegistry =
                RegistryBuilder.<AuthSchemeProvider>create().register(AuthSchemes.DIGEST,new DigestSchemeFactory()).build();
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                new AuthScope("http://16.6.0.13:8000/sap/bc/gui/sap/its/webgui",80),
                new UsernamePasswordCredentials("songzf","szf7035083"));

        CloseableHttpClient client = HttpClients.custom()
                .setDefaultAuthSchemeRegistry(authSchemeRegistry)
                .setDefaultCredentialsProvider(credentialsProvider).build();

        try {
            CloseableHttpResponse response = client.execute(getArticles);
            System.out.println(response.getStatusLine().getStatusCode());
            String loginPostResult = EntityUtils.toString(response.getEntity());
            System.out.println(loginPostResult);
        } catch (IOException e) {
            System.out.println(e);
        }finally {
            try {
                client.close();
            } catch (IOException e) {
                //logger.error(e.getMessage(),e);
            }
        }
    }
}
