package com.calix.components;

import com.adobe.cq.sightly.WCMUse;
import com.calix.model.Video;
import com.calix.model.CTA;
import com.calix.model.VideoGroup;
import com.calix.utils.CommonUtils;
import com.calix.utils.JcrUtils;
import com.calix.utils.VideoUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Video Library Class.
 *
 * @author epsilon-khoa
 */

public class VideoLibrary extends WCMUse {

    /** The Constant logger. */
    private static final Logger  logger = LoggerFactory.getLogger(VideoLibrary.class);
    public static final String GROUPS = "groups";
    public static final String CAPTION = "caption";
    public static final String MAIN_VIDEO_PATH = "mainVideoPath";
    public static final String JCR_CONTENT_NARROW_CONTAINER_VIDEO = "/jcr:content/narrowContainer/video";

    private Video mainVideo = null;
    private List<VideoGroup> videoGroups = null;
    private int numberOfRelatedVideos = 0;
    private String modalId = StringUtils.EMPTY;

    public Video getMainVideo() {
        return mainVideo;
    }

    public List<VideoGroup> getVideoGroups() {
        return videoGroups;
    }

    public int getNumberOfRelatedVideos() {
        return numberOfRelatedVideos;
    }

    public String getModalId() {
        return modalId;
    }
    /* (non-Javadoc)
     * @see com.adobe.cq.sightly.WCMUse#activate()
     */
    @Override
    public void activate() throws Exception {
    	try {
	        SlingHttpServletRequest slingRequest = this.getRequest();
	
	        Node currentNode = slingRequest.getResource().adaptTo(Node.class);
	        if(currentNode != null){
	            modalId = currentNode.getPath().substring(1).replace("/", "-").replace(":", "-");
	        }
	        com.calix.model.VideoLibrary videoLibrary = VideoUtils.getVideoLibraryFromProperty(slingRequest, currentNode, get("groupPropertyName", String.class));
	        if(null != videoLibrary){
		        videoGroups = videoLibrary.getVideoGroups();
		        mainVideo = videoLibrary.getMainVideo();
		        numberOfRelatedVideos = videoLibrary.getNumOfRelated();
	        }
    	} catch (Exception e) {
    		logger.error("Error when process video library", e);
    	}
    }
}