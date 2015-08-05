package com.calix.servlets;

import com.calix.service.RestInvoker;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.jcr.api.SlingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.io.IOException;
import java.rmi.ServerException;

@SlingServlet(paths = "/bin/calixservice/sample", methods = {"GET", "POST"}, metatype = true)
public class SampleServlet extends org.apache.sling.api.servlets.SlingAllMethodsServlet {

    private final Logger log = LoggerFactory.getLogger(SampleServlet.class);

    @Reference
    private SlingRepository repository;

    @Reference
    RestInvoker invoker;

    public void bindRepository(SlingRepository repository) {
        this.repository = repository;
    }

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServerException, IOException {
        try {
            JSONObject recordObject = new JSONObject();
            JSONObject json = new JSONObject();
            json.put("userid", "00518000000H0XC");
            json.put("acctName","Calix Internal Account");
            recordObject.put("records",json);
            response.getWriter().write(invoker.invoke("http://author-calix-dev.adobecqms.net/apps/calix/Account", recordObject.toString()));
        } catch (Exception e) {
            log.error("Exception in sample service: ", e);
        }
    }
}

