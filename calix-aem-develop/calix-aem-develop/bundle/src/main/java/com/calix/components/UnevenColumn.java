package com.calix.components;

import com.adobe.cq.sightly.WCMUse;
import com.calix.model.*;
import com.calix.model.VideoLibrary;
import com.calix.utils.CommonUtils;
import com.calix.utils.JcrUtils;
import com.calix.utils.VideoUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc

/**
 * Spotlight Class.
 *
 * @author epsilon-Thao
 */

public class UnevenColumn extends WCMUse {

    /** The Constant logger. */
    private static final Logger  logger = LoggerFactory.getLogger(UnevenColumn.class);

    private String caption;
    private String headline;
    private String headlineLink;
    private CTA cta1;
    private CTA cta2;
    private com.calix.model.VideoLibrary video;
    private String modalId;
    private String curveStyle;
    private Video videoItem;

    public Video getVideoItem() {
        return videoItem;
    }

    public String getCaption() {
        return caption;
    }

    public String getHeadline() {
        return headline;
    }

    public String getHeadlineLink() {
        return headlineLink;
    }

    public CTA getCta1() {
        return cta1;
    }

    public CTA getCta2() {
        return cta2;
    }

    public VideoLibrary getVideo() {
        return video;
    }

    public String getModalId() {
        return modalId;
    }

    public String getCurveStyle() {
        return curveStyle;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Boolean getDisplayImage() {
        return displayImage;
    }

    public Boolean getDisplayVideo() {
        return displayVideo;
    }

    public Boolean getDisplayVideos() {
        return displayVideos;
    }

    public Boolean getDisplayMobile() {
        return displayMobile;
    }

    public String getImagePosition() {
        return imagePosition;
    }

    public String getBodyText() {
        return bodyText;
    }

    public String getMobileStyle() {
        return mobileStyle;
    }

    private String imagePath;
    private Boolean displayImage;
    private Boolean displayVideo;
    private Boolean displayVideos;
    private Boolean displayMobile;
    private String imagePosition;
    private String bodyText;
    private String mobileStyle;

    /* (non-Javadoc)
     * @see com.adobe.cq.sightly.WCMUse#activate()
     */
    @Override
    public void activate() throws Exception {
        try {
            SlingHttpServletRequest slingRequest = this.getRequest();
            final Session session = CommonUtils.getSession(slingRequest);
            Node currentNode = slingRequest.getResource().adaptTo(Node.class);
            //get information of dialog
            caption = JcrUtils.getStringValue(currentNode, "caption");
            headline = JcrUtils.getStringValue(currentNode, "headline");
            headlineLink = JcrUtils.getStringValue(currentNode, "headlineLink");
            headlineLink = CommonUtils.getFormattedLink(headlineLink);
            bodyText = JcrUtils.getStringValue(currentNode, "text");
            curveStyle = "";
            displayImage = false;
            displayVideo = false;
            displayVideos = false;
            displayMobile = JcrUtils.getBooleanValue(currentNode, "notDisplayMobile");
            boolean displayTopCurves = JcrUtils.getBooleanValue(currentNode, "displayTopCurves");
            boolean displayBottomCurves = JcrUtils.getBooleanValue(currentNode, "displayBottomCurves");
            String position = JcrUtils.getStringValue(currentNode, "displayLeftOrRight");
            imagePosition = "pull-left";

            //style of image position
            if(StringUtils.isNotBlank(position)){
                if(position.equalsIgnoreCase("right")){
                    imagePosition = "pull-right";
                }
            }
            mobileStyle = "";
            if(displayMobile){
                mobileStyle = "display: none;";
            }
            //style of curve
            if(displayTopCurves){
                curveStyle += " topBorder";
            }
            if (displayBottomCurves) {
                curveStyle += " bottomBorder";
            }
            //get CTA 1 and 2
            cta1 = CommonUtils.convertNodeToCTA(currentNode, "1");
            cta2 = CommonUtils.convertNodeToCTA(currentNode, "2");
            String type = JcrUtils.getStringValue(currentNode, "type");
            //get type of uneven column
            if(StringUtils.isNotBlank(type)){
                if(type.equalsIgnoreCase("video")){
                    displayVideo = true;
                    String videoPath = JcrUtils.getStringValue(currentNode, "videoPath");
                    videoItem = VideoUtils.getVideo(slingRequest, videoPath);
                    video = VideoUtils.convertVideoToVideoLibrary(videoItem);
                } else if(type.equalsIgnoreCase("image")){
                    displayImage = true;
                    imagePath = JcrUtils.getStringValue(currentNode, "imagePath");
                } else if(type.equalsIgnoreCase("videolibrary")){
                    displayVideos = true;
                }
            }
            //get modal id
            modalId = currentNode.getPath().substring(1).replace("/", "-").replace(":", "-");
        }catch (Exception ex){
            logger.error("Exeption Main Hero: ", ex);
        }
    }
}
