package com.calix.components;

import com.adobe.cq.sightly.WCMUse;
import com.calix.model.CTA;
import com.calix.model.GenericItem;
import com.calix.utils.CommonUtils;
import com.calix.utils.Constants;
import com.calix.utils.JcrUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.Session;

/**
 * Spotlight Class.
 *
 * @author epsilon-Thao
 */

public class GenericContentItem extends WCMUse {

    /** The Constant logger. */
    private static final Logger  logger = LoggerFactory.getLogger(GenericContentItem.class);

    /** The generic content. */
    private String text;

    public String getText() {
        return text;
    }

    /* (non-Javadoc)
     * @see com.adobe.cq.sightly.WCMUse#activate()
     */
    @Override
    public void activate() throws Exception {
        SlingHttpServletRequest slingRequest = this.getRequest();
        final Session session = CommonUtils.getSession(slingRequest);
        Node currentNode = slingRequest.getResource().adaptTo(Node.class);
        //get text
        text = JcrUtils.getStringValue(currentNode, "text");
    }
}
