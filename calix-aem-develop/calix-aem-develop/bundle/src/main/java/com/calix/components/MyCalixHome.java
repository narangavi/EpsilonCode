package com.calix.components;

import javax.jcr.Node;

import org.apache.sling.api.SlingHttpServletRequest;

import com.adobe.cq.sightly.WCMUse;
import com.calix.utils.JcrUtils;

/**
 * The Class MyCalixHome.
 */
public class MyCalixHome extends WCMUse {
	/** The welcome text. */
	private String welcomeText;
	
	/** The description. */
	private String description;

	/**
	 * Gets the welcome text.
	 *
	 * @return the welcome text
	 */
	public String getWelcomeText() {
		return welcomeText;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.adobe.cq.sightly.WCMUse#activate()
	 */
	@Override
	public void activate() throws Exception {
		SlingHttpServletRequest slingRequest = this.getRequest();
		Node currentNode = slingRequest.getResource().adaptTo(Node.class);
		welcomeText = JcrUtils.getStringValue(currentNode, "welcomeText");
		description = JcrUtils.getStringValue(currentNode, "desc");
	}
}
