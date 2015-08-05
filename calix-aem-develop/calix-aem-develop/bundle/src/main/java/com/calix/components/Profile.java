package com.calix.components;

import com.adobe.cq.sightly.WCMUse;
import com.calix.model.ProfileItem;
import com.calix.utils.CommonUtils;
import com.calix.utils.JcrUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.Session;

/**
 * Profile Class.
 *
 * @author epsilon-Thao
 */

public class Profile extends WCMUse {

    /**
     * The Constant logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(Profile.class);

    /**
     * The profile.
     */
    private ProfileItem item;

    public ProfileItem getItem() {
        return item;
    }

    /* (non-Javadoc)
     * @see com.adobe.cq.sightly.WCMUse#activate()
     */
    @Override
    public void activate() throws Exception {
        SlingHttpServletRequest slingRequest = this.getRequest();
        final Session session = CommonUtils.getSession(slingRequest);
        Node currentNode = slingRequest.getResource().adaptTo(Node.class);

        String fName = JcrUtils.getStringValue(currentNode, "firstName");
        String lName = JcrUtils.getStringValue(currentNode, "lastName");
        String job = JcrUtils.getStringValue(currentNode, "job");
        String department = JcrUtils.getStringValue(currentNode, "department");
        String type = "";
        String[] typeArray = JcrUtils.getStringArrayValue(currentNode, "type");
        if (null != typeArray && typeArray.length > 0) {
            for (int i = 0; i < typeArray.length; i++) {
                type += typeArray[i] + (i < typeArray.length - 1 ? " , " : "");
            }
        }
        String desc = JcrUtils.getStringValue(currentNode, "description");
        String image = JcrUtils.getStringValue(currentNode, "image");
        item = new ProfileItem(fName, lName, job, department, type, desc, image);
    }
}
