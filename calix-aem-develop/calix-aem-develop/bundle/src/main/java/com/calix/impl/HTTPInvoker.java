package com.calix.impl;

/**
 * Created by vkesavan on 3/31/15.
 */

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//SLing HTTP Client

@Component(metatype = true)
@Service(HTTPInvoker.class)
public class HTTPInvoker {
    /**
     * Default log.
     */
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    public String invoke(String url) {
        //Create an HTTPClient object
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(url);

        // Provide custom retry handler is necessary
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));

        try {
            int statusCode = client.executeMethod(method);

            if (statusCode != HttpStatus.SC_OK) {
                log.info("HTTP Method failed: " + method.getStatusLine());
            }

            // Read the response body.
            byte[] responseBody = method.getResponseBody();
            String returnVal = new String(responseBody);
            log.info("INVOKER RESPONSE: " + returnVal);

            return returnVal;

        } catch (Exception e) {
            log.error("ERROR in HTTP invoker: " + e);
        }
        return "There was an error invoking the URL";
    }

    public String getClientIpAddr(SlingHttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        log.debug(new StringBuilder().append("Ip address X-Forwarded-For").append(ip).toString());
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("Proxy-Client-IP");
            log.debug(new StringBuilder().append("Ip address Proxy-Client-IP").append(ip).toString());
        }
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            log.debug(new StringBuilder().append("Ip address WL-Proxy-Client-IP").append(ip).toString());
        }
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            log.debug(new StringBuilder().append("Ip address HTTP_CLIENT_IP").append(ip).toString());
        }
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            log.debug(new StringBuilder().append("Ip address HTTP_X_FORWARDED_FOR").append(ip).toString());
        }
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getRemoteAddr();
            log.debug(new StringBuilder().append("Ip address ").append(ip).toString());
        }
        return ip;
    }

    public String geoLocateCurrentUser(SlingHttpServletRequest request) {
        String userIP = request.getRemoteAddr();
        log.info("Querying geolocation for IP: " + userIP);
        StringBuilder sb = new StringBuilder("http://api.ipinfodb.com/v3/ip-city/?key=515280822bbca0ca16049bb38e9e2e1dc12607bc61aaa949d6fb497e486e3023&format=json&ip=").append(userIP);
        log.info("INFO DB EXECUTED WITH QUERY: " + sb.toString());
        return invoke(sb.toString());
    }

}