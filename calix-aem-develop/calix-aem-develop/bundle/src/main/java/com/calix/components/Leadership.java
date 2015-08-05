package com.calix.components;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.Session;

import org.apache.sling.api.SlingHttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUse;
import com.calix.model.LeadershipTab;
import com.calix.model.ProfileItem;
import com.calix.utils.CommonUtils;
import com.calix.utils.Constants;
import com.calix.utils.JcrUtils;

/**
 * Leadership Class.
 *
 * @author epsilon-duong
 */
public class Leadership extends WCMUse {

    /** The Constant logger. */
    private static final Logger  logger = LoggerFactory.getLogger(Leadership.class);

    private List<LeadershipTab> leadershipTabs;

    public List<LeadershipTab> getLeadershipTabs() {
        return leadershipTabs;
    }

    /* (non-Javadoc)
     * @see com.adobe.cq.sightly.WCMUse#activate()
     */
    @Override
    public void activate() throws Exception {
        SlingHttpServletRequest slingRequest = this.getRequest();
        final Session session = CommonUtils.getSession(slingRequest);
        Node currentNode = slingRequest.getResource().adaptTo(Node.class);
        
        String labelExe = JcrUtils.getStringValue(currentNode, "labelExe");
        String descriptionExe = JcrUtils.getStringValue(currentNode, "descriptionExe");
        String[] profilesExe = JcrUtils.getStringArrayValue(currentNode, "profilesExe");
        String labelDir = JcrUtils.getStringValue(currentNode, "labelDir");
        String descriptionDir = JcrUtils.getStringValue(currentNode, "descriptionDir");
        String[] profilesDir = JcrUtils.getStringArrayValue(currentNode, "profilesDir");
        
        leadershipTabs = new ArrayList<LeadershipTab>();
        
        LeadershipTab tabExe = new LeadershipTab();
        tabExe.setName(Constants.EXECUTIVE);
        tabExe.setLabel(labelExe);
        tabExe.setDescription(descriptionExe);
        tabExe.setProfiles(getProfiles(slingRequest, profilesExe));
        leadershipTabs.add(tabExe);
        
        LeadershipTab tabDir = new LeadershipTab();
        tabDir.setName(Constants.DIRECTORS);
        tabDir.setLabel(labelDir);
        tabDir.setDescription(descriptionDir);
        tabDir.setProfiles(getProfiles(slingRequest, profilesDir));
        leadershipTabs.add(tabDir);
    }
    
    protected List<ProfileItem> getProfiles(SlingHttpServletRequest slingRequest, String... paths) {
    	List<ProfileItem> profiles = new ArrayList<ProfileItem>();
    	try {
			for (String path : paths) {
				Node profileNode = JcrUtils.getNodeByPagePath(slingRequest, path, Constants.SLING_RESOURCE_TYPE, Constants.PROFILE_RESOURCE_TYPE);
				if(profileNode != null) {
					ProfileItem profile = convertNodeToProfileItem(profileNode);
					profiles.add(profile);
				}
			}
    	} catch(Exception e) {
            logger.error("Error when search profile: ", e);
        }
    	return profiles;
    }
    	
    protected ProfileItem convertNodeToProfileItem(Node node) {
    	ProfileItem profile = null;
        if(node != null) {
        	profile = new ProfileItem();
            profile.setFirstName(JcrUtils.getStringValue(node, "firstName"));
            profile.setLastName(JcrUtils.getStringValue(node, "lastName"));
            profile.setJob(JcrUtils.getStringValue(node, "job"));
            profile.setDepartment(JcrUtils.getStringValue(node, "department"));
            profile.setDescription(JcrUtils.getStringValue(node, "description"));
            profile.setImage(JcrUtils.getStringValue(node, "image"));

            String type = "";
            String[] typeArray = JcrUtils.getStringArrayValue(node, "type");
            if ( null != typeArray && typeArray.length > 0) {
                for (int i = 0 ; i < typeArray.length ; i++) {
                    type += typeArray[i] + (i < typeArray.length - 1 ? " , " : "");
                }
            }
            profile.setType(type);
        }
        return profile;
    }
}
