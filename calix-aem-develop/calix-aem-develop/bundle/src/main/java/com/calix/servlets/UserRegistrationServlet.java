/**
  * UserRegistrationServlet.java
  * @Author: Epsilon-TranHNM
  * 
  * Objective: This implements logic to call WS for Registration Form
  * Completion Date: July 31, 2015
  * 
  * @version: 1.0 
*/
package com.calix.servlets;

import java.io.IOException;
import java.rmi.ServerException;

import javax.servlet.ServletException;

import org.apache.commons.lang.StringUtils;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.jcr.api.SlingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.calix.service.RestInvoker;
import com.calix.utils.WSConstants;

/**
 * The Class UserRegistrationServlet.
 */
@SlingServlet(paths = WSConstants.USER_REGISTRATION_SERVLET, methods = {"GET", "POST"}, metatype = true)
public class UserRegistrationServlet extends org.apache.sling.api.servlets.SlingAllMethodsServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1604698150608975073L;

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(UserRegistrationServlet.class);

    /** The repository. */
    @Reference
    private SlingRepository repository;

    /** The invoker. */
    @Reference
    RestInvoker invoker;

    /**
     * Bind repository.
     *
     * @param repository the repository
     */
    public void bindRepository(SlingRepository repository) {
        this.repository = repository;
    }

    /* (non-Javadoc)
     * @see org.apache.sling.api.servlets.SlingSafeMethodsServlet#doGet(org.apache.sling.api.SlingHttpServletRequest, org.apache.sling.api.SlingHttpServletResponse)
     */
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /* (non-Javadoc)
     * @see org.apache.sling.api.servlets.SlingAllMethodsServlet#doPost(org.apache.sling.api.SlingHttpServletRequest, org.apache.sling.api.SlingHttpServletResponse)
     */
    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServerException, IOException {
    	try {
    		String data = request.getParameter("formData");
    	    if (StringUtils.isNotEmpty(data)){
    	    	String responseData = invoker.invoke(WSConstants.ENPOINT_WS + WSConstants.USER_REGISTRATION_WS, data);
    	    	response.getWriter().write(responseData);
    	    }
            
        } catch (Exception e) {
            log.error("Exception when registration user: {}", e);
        }
    }
}

