package com.javaadvance.jvm01;

/**
 * Created by aoyonggang on 2022/3/13.
 */

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class HttpClientDemo {

    public static CloseableHttpClient httpclient = HttpClients.createDefault();

    private static String getResponse(HttpRequestBase httpAction) throws IOException {
        CloseableHttpResponse responseContent = null;
        try{
            responseContent = httpclient.execute(httpAction);
            HttpEntity entity = responseContent.getEntity();
            return EntityUtils.toString(entity, "UTF-8");

        }finally {
            if (null != responseContent) {
                responseContent.close();
            }
        }
    }


    public static String getAsString(String url) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        return getResponse(httpGet);
    }


    public static String postAsJSON(String url, String json, Map<String, String> headers) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        Set<String> keySet = headers.keySet();
        for (String name : keySet) {
            httpPost.setHeader(name, headers.get(name));
        }
        final String JSON_TYPE = "application/json;charset=UTF-8";
        httpPost.setHeader(HTTP.CONTENT_TYPE, JSON_TYPE);
        StringEntity entity = new StringEntity(json, "UTF-8");

        httpPost.setEntity(entity);
        return getResponse(httpPost);
    }

    public static void main(String[] args) throws Exception {

        String url = "http://www.baidu.com/";
        String text = HttpClientDemo.getAsString(url);
        System.out.println("url: " + url + " ; response: \n" + text);
        System.out.println("-----------------------");
        String postText = HttpClientDemo.postAsJSON(url, null, null);
        System.out.println(postText);

    }
}