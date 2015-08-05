package com.calix.servlets;

import java.io.IOException;
import java.rmi.ServerException;
import javax.servlet.ServletException;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.calix.utils.WSConstants;

/**
 * The Class NotificationServlet.
 */
@SlingServlet(paths = WSConstants.NOTIFICATION_SERVLET, methods = {"GET", "POST"}, metatype = true)
public class NotificationServlet extends SlingAllMethodsServlet{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7044197827189699080L;
	
	/** The log. */
	private final Logger LOGGER = LoggerFactory.getLogger(NotificationServlet.class);


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
    }

}
