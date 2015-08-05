package com.calix.components;

import java.util.List;

import javax.jcr.Node;
import javax.jcr.Session;

import com.calix.model.*;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUse;
import com.calix.utils.CommonUtils;
import com.calix.utils.Constants;
import com.calix.utils.JcrUtils;
import com.calix.utils.VideoUtils;

/**
 * Detailed Systems Class.
 *
 * @author epsilon-mmalik
 */

public class DetailedSystems extends WCMUse {

    /**
     * The Constant logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(DetailedSystems.class);

    private String dsTitle;
    private String dsSubhead;
    private String dsDescription;
    private String dsBtnType;

    public String dsTitle() {
        return dsTitle;
    }

    public String getDsSubhead() {
        return dsSubhead;
    }

    public String getDsDescription() {
        return dsDescription;
    }

    public String getDsBtnType() {
        return dsBtnType;
    }

    /* (non-Javadoc)
     * @see com.adobe.cq.sightly.WCMUse#activate()
     */
    @Override
    public void activate() throws Exception {
        SlingHttpServletRequest slingRequest = this.getRequest();
        final Session session = CommonUtils.getSession(slingRequest);
        Node currentNode = slingRequest.getResource().adaptTo(Node.class);

        dsTitle = JcrUtils.getStringValue(currentNode, "title");
        dsSubhead = JcrUtils.getStringValue(currentNode, "subhead");
        dsDescription = JcrUtils.getStringValue(currentNode, "description");
        dsBtnType = JcrUtils.getStringValue(currentNode, "btnType");
    }
}
