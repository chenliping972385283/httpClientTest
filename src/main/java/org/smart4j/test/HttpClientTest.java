package org.smart4j.test;


import com.sun.security.ntlm.Client;
import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.*;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.smart4j.tools.HttpParser;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by chenliping on 16/7/8.
 */
public class HttpClientTest {
    private static HttpParser httpParser = new HttpParser();


public static void main(String[] args) throws IOException{
    String loginURL = "http://16.6.0.13:8000/sap/bc/gui/sap/its/webgui";
    CredentialsProvider provider = new BasicCredentialsProvider();
    UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("songzf", "szf7035083");
    provider.setCredentials(AuthScope.ANY, credentials);
    HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();

    HttpResponse response = client.execute(new HttpGet("http://16.6.0.13:8000/sap/bc/gui/sap/its/webgui"));
    int statusCode = response.getStatusLine().getStatusCode();
    String loginHTML = EntityUtils.toString(response.getEntity());
    //System.out.println(statusCode)
    // System.out.println(loginHTML);

    String FOCUS_ID = httpParser.getValueFromInputByName(loginHTML, "FOCUS_ID");
  String sap_system_login_basic_auth = httpParser.getValueFromInputByName(loginHTML, "sap-system-login-basic_auth");



   List<NameValuePair> parameterList = new ArrayList<NameValuePair>();
    parameterList.add(new BasicNameValuePair("FOCUS_ID",FOCUS_ID));
   parameterList.add(new BasicNameValuePair("dummy_sap_password","szf7035083"));
   parameterList.add(new BasicNameValuePair("dummy_sap_user","songzf"));
    parameterList.add(new BasicNameValuePair("sap-client","500"));
    parameterList.add(new BasicNameValuePair("sap-language","ZH"));
    parameterList.add(new BasicNameValuePair("sap-language-dropdown","中文"));
    parameterList.add(new BasicNameValuePair("sap-system-login","basic_auth"));
    parameterList.add(new BasicNameValuePair("sap-system-login-oninputprocessing","onLogin"));
    parameterList.add(new BasicNameValuePair("sap-system-login-basic_auth",sap_system_login_basic_auth));
    parameterList.add(new BasicNameValuePair("sysid","GDQ"));

    HttpPost loginPost = new HttpPost(loginURL);
    loginPost.setEntity(new UrlEncodedFormEntity(parameterList,"utf-8"));
    HttpResponse httpResponse = client.execute(loginPost);
    String loginPostResult = EntityUtils.toString(httpResponse.getEntity());
    System.out.println(httpResponse.getStatusLine().getStatusCode());
    System.out.println(loginPostResult);
    String locationUrl=httpResponse.getLastHeader("Location").getValue();
    System.out.println(locationUrl);
    HttpPost response2 =  new HttpPost("http://16.6.0.13:8000"+ locationUrl);
    response2.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:47.0) Gecko/20100101 Firefox/47.0");
    HttpResponse httpResponse3 = client.execute(response2);
  // System.out.println(EntityUtils.toString(httpResponse3.getEntity()));
    System.out.println(httpResponse3.getStatusLine().getStatusCode());

    String loginHtml3 = EntityUtils.toString(httpResponse3.getEntity());

    String SEC_SESSTOKEN = httpParser.getValueFromInputByName(loginHtml3, "SEC_SESSTOKEN");
    String SEC_SESSTOKEN2 = httpParser.getValueFromInputByName(loginHtml3, "~SEC_SESSTOKEN");
    String path = httpParser.getValueFromInputByName(loginHtml3, "~path");
    String singletransaction = httpParser.getValueFromInputByName(loginHtml3, "~singletransaction");
    String webguiDynproMetric = httpParser.getValueFromInputByName(loginHtml3, "~webguiDynproMetric");
    //String webguiUserAreaHeight = httpParser.getValueFromInputByName(loginHtml3, "277");
    //String webguiUserAreaWidth = httpParser.getValueFromInputByName(loginHtml3, "1906");


    List<NameValuePair> parameterListt = new ArrayList<NameValuePair>();
    parameterListt.add(new BasicNameValuePair("SEC_SESSTOKEN",SEC_SESSTOKEN));
    parameterListt.add(new BasicNameValuePair("~SEC_SESSTOKEN",SEC_SESSTOKEN2));
    parameterListt.add(new BasicNameValuePair("~path",path));
    parameterListt.add(new BasicNameValuePair("~singletransaction",singletransaction));
    parameterListt.add(new BasicNameValuePair("~webguiDynproMetric",webguiDynproMetric));
    parameterListt.add(new BasicNameValuePair("~webguiUserAreaHeight","277"));
    parameterListt.add(new BasicNameValuePair("~webguiUserAreaWidth","1906"));



    HttpPost loginPostt = new HttpPost("http://16.6.0.13:8000"+ path );
    loginPostt.setEntity(new UrlEncodedFormEntity(parameterListt));
    HttpResponse httpResponse4 = client.execute(loginPostt);
    String loginPostResult4 = EntityUtils.toString(httpResponse4.getEntity());
    System.out.println(httpResponse4.getStatusLine().getStatusCode());
    System.out.println(loginPostResult4);
}
}