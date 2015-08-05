/**
  * QuickLinks.java
  * @Author: Epsilon-DuongDD1
  * 
  * Objective: This implements logic to display quick links component.
  * Completion Date: July 31, 2015
  * 
  * @version: 1.0 
*/
package com.calix.components;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.Node;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUse;
import com.calix.model.Link;
import com.calix.utils.CommonUtils;
import com.calix.utils.JcrUtils;

public class QuickLinks extends WCMUse {

	private static final Logger logger = LoggerFactory.getLogger(QuickLinks.class);

	private String compTitle;
	private List<Link> links;

	public String getCompTitle() {
		return compTitle;
	}

	public List<Link> getLinks() {
		return links;
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
		try {
			compTitle = JcrUtils.getStringValue(currentNode, "compTitle");
			String[] linksArr = JcrUtils.getStringArrayValue(currentNode, "links");
			links = getLinks(slingRequest, linksArr);
		} catch (Exception ex) {
			logger.error("Exception in QuickLinks component: {}", ex);
		}
	}

	/**
	 * Gets the list of link item.
	 *
	 * @param slingRequest the sling request
	 * @param linksArr the links array
	 * @return the links
	 */
	protected List<Link> getLinks(SlingHttpServletRequest slingRequest, String... linksArr) {
		List<Link> links = new ArrayList<Link>();
		if (null == linksArr) {
			return links;
		}
		try {
			for (String linkArr : linksArr) {
				Link link = CommonUtils.mapJsonToObject(linkArr, Link.class);
				String url = link.getLink();
				if (StringUtils.isNotBlank(url)) {
					link.setTarget(CommonUtils.getTargetByLink(url));
					link.setLinkStyle(CommonUtils.getStyleByLink(url));
					link.setLink(CommonUtils.getFormattedLink(url));
				}
				links.add(link);
			}
		} catch (Exception e) {
			logger.error("Error when get quick links: {}", e);
		}
		return links;
	}
}
