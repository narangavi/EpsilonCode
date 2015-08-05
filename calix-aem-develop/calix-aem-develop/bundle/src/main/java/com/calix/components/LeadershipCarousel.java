package com.calix.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUse;
import com.calix.model.CTA;
import com.calix.model.ProfileItem;
import com.calix.utils.CommonUtils;
import com.calix.utils.Constants;
import com.calix.utils.JcrUtils;
import com.calix.utils.QueryBuilderUtils;
import com.day.cq.search.result.SearchResult;

public class LeadershipCarousel extends WCMUse {

    /** The Constant logger. */
    private static final Logger  logger = LoggerFactory.getLogger(LeadershipCarousel.class);

    private List<ProfileItem> profileItems;

    private CTA cta;

    public List<ProfileItem> getProfileItems() {
        return profileItems;
    }

    public CTA getCta() {
		return cta;
	}

	/* (non-Javadoc)
     * @see com.adobe.cq.sightly.WCMUse#activate()
     */
    @Override
    public void activate() throws Exception {
    	try {
	        SlingHttpServletRequest slingRequest = this.getRequest();
	        Node currentNode = slingRequest.getResource().adaptTo(Node.class);
	        //get leaderships
	        String [] leaderPaths = JcrUtils.getStringArrayValue(currentNode, "leaders");
	        profileItems = getLeaders(leaderPaths);
	        cta = CommonUtils.convertNodeToCTA(currentNode, "");
    	} catch (Exception e){
    		logger.error("[Activate Method] Error ", e);
    	}
    }
    
    private List<ProfileItem> getLeaders(String[] leaderPaths) {
    	final List<ProfileItem> profileItems = new ArrayList<ProfileItem>();
    	if (null == leaderPaths){
            return profileItems;
        }

        for (final String leaderPath : leaderPaths) {
            try {
                final ProfileItem profile = getLeader(leaderPath);
                profileItems.add(profile);
            } catch (final Exception e) {
                logger.error("GET Leaders Error " + e.getMessage());
            }
        }
        return profileItems;
	}

	private ProfileItem getLeader(String leaderPath) {
		SlingHttpServletRequest slingRequest = this.getRequest();
		ProfileItem profile = null;
		Map<String, String> params = null;
        SearchResult result = null;
    	params = new HashMap<String, String>();
    	result = null;
    	try {
	        logger.info("Leader Path: " + leaderPath);
	        QueryBuilderUtils.buildRootParam(params, 0, 0, leaderPath);
	        params.put("property", Constants.SLING_RESOURCE_TYPE);
	        params.put("property.value", Constants.PROFILE_RESOURCE_TYPE);
	        result = QueryBuilderUtils.executequery(slingRequest, params);
	        profile = convertSearchResultToProfiles(result, slingRequest);
    	} catch (Exception e){
    		logger.error("GET Leader Error " + e.getMessage());
    		profile = null;
    	}
		return profile;
	}

	protected ProfileItem convertSearchResultToProfiles(SearchResult result, SlingHttpServletRequest slingRequest) {
    	ProfileItem profile = null;
        try {
            if (result != null) {
                String firstName = StringUtils.EMPTY;
                String lastName = StringUtils.EMPTY;
                String job = StringUtils.EMPTY;
                String department = StringUtils.EMPTY;
                String description = StringUtils.EMPTY;
                String image = StringUtils.EMPTY;
                Iterator<Node> nodeResults = result.getNodes();
                if(nodeResults.hasNext()){
                    profile = new ProfileItem();
                    Node node = nodeResults.next();
                    
                    firstName = JcrUtils.getStringValue(node, "firstName");
                    lastName = JcrUtils.getStringValue(node, "lastName");
                    job = JcrUtils.getStringValue(node, "job");
                    department = JcrUtils.getStringValue(node, "department");
                    description = JcrUtils.getStringValue(node, "description");
                    image = JcrUtils.getStringValue(node, "image");
                    
                    profile.setFirstName(firstName);
                    profile.setLastName(lastName);
                    profile.setJob(job);
                    profile.setDepartment(department);
                    profile.setDescription(description);
                    profile.setImage(image);
                }

            }
        } catch (Exception e) {
            logger.error("Error when convert profile serch result ", e);
        }
        return profile;
    }
    
    
}
