package com.calix.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.xml.bind.DatatypeConverter;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.calix.utils.WSConstants;

/**
 * Created by vkesavan on 7/8/15.
 */
@Component
@Service(RestInvoker.class)
public class RestInvoker {
    
    /** The log. */
    Logger log = LoggerFactory.getLogger(RestInvoker.class);

    /**
     * Invoke.
     *
     * @param restUrl the rest url
     * @param payload the payload
     * @return the string
     */
    public String invoke(String restUrl, String payload) {

        StringBuilder responseJSONBuffer = new StringBuilder();

        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            //httpClient.getCredentialsProvider().setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("vijay.kesavan", "vijaydevauthor"));
            HttpResponse response = null;
            if (payload != null && !payload.isEmpty()) {
                // POST request
                response = doPost(restUrl, payload, httpClient);
            } else {
                // GET request
                response = doGet(restUrl, httpClient);
            }

            if (null == response) {
                throw new RuntimeException("Failed: No response");
            } else if (response != null && response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

            String temp;
            while ((temp = br.readLine()) != null) {
                responseJSONBuffer.append(temp);
            }

            httpClient.getConnectionManager().shutdown();
        } catch (Exception e) {
            log.error("Exception in REST invocation: " + restUrl, e);
        }

        log.info("\n\n Response received: ### " + responseJSONBuffer.toString());
        return responseJSONBuffer.toString();
    }

    /**
     * Do get.
     *
     * @param url the url
     * @param client the client
     * @return the http response
     */
    private HttpResponse doGet(String url, DefaultHttpClient client) {
        log.info("Doing GET request in RESTInvoker");
        HttpResponse response = null;
        try {
            HttpGet getRequest = new HttpGet("url");
            String encoding = DatatypeConverter.printBase64Binary("user:passwd".getBytes("UTF-8"));
            getRequest.addHeader("authorization", "Basic " + encoding);
            getRequest.addHeader("accept", "application/json");
            response = client.execute(getRequest);
        } catch (IOException e) {
            log.error("Exception in GET request of REST Invoker: ", e);
        }
        return response;
    }

    /**
     * Do post.
     *
     * @param url the url
     * @param payload the payload
     * @param httpClient the http client
     * @return the http response
     */
    private HttpResponse doPost(String url, String payload, DefaultHttpClient httpClient) {
        log.info("\n Doing POST request in RESTInvoker for URL>>>>> " + url);
        log.info("\n Sending payload####### " + payload);
        HttpResponse response = null;
        try {
            HttpPost post = new HttpPost(url);
            String encoding = DatatypeConverter.printBase64Binary(WSConstants.AUTHENTICATION_WS.getBytes("UTF-8"));
            StringEntity input = new StringEntity(payload);
            input.setContentType("application/json");
            post.setEntity(input);
            post.addHeader("content-type", "application/json");
            post.addHeader("authorization", "Basic " + encoding);
            post.addHeader("accept", "application/json");
            response = httpClient.execute(post);
        } catch (IOException e) {
            log.error("Exception in POST request: ", e);
        }
        return response;
    }
}