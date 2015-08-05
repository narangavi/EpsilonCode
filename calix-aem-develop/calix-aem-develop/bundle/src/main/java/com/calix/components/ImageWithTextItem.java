package com.calix.components;

import com.adobe.cq.sightly.WCMUse;
import com.calix.model.*;
import com.calix.utils.CommonUtils;
import com.calix.utils.Constants;
import com.calix.utils.JcrUtils;
import com.calix.utils.VideoUtils;
import com.day.cq.wcm.foundation.Image;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.commons.json.JSONException;
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

public class ImageWithTextItem extends WCMUse {

    /**
     * The Constant logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(ImageWithTextItem.class);

    private String itemType;
    private Image image;
    private String title;
    private String headlineLink;
    private boolean isTitleLink;
    private String description;
    private CTA cta1;
    private CTA cta2;
    private com.calix.model.VideoLibrary video;
    private String modalId;

    /* (non-Javadoc)
     * @see com.adobe.cq.sightly.WCMUse#activate()
     */
    @Override
    public void activate() throws Exception {
        try {
            SlingHttpServletRequest slingRequest = this.getRequest();
            Node currentNode = slingRequest.getResource().adaptTo(Node.class);

            //Get property value
            getGeneralInfo(currentNode);
            processItemType(slingRequest, currentNode);

            if (currentNode != null) {
                setModalId(currentNode.getPath().substring(1).replace("/", "-").replace(":", "-"));
            }
        } catch (Exception e) {
            logger.error("[Image With Text Item] Processing Image With Text Item", e);
        }
    }

    private void getGeneralInfo(Node currentNode) {
        itemType = JcrUtils.getStringValue(currentNode, "type");
        title = JcrUtils.getStringValue(currentNode, "title");
        headlineLink = JcrUtils.getStringValue(currentNode, "headlineLink");
        description = JcrUtils.getStringValue(currentNode, "text");
        //cta
        cta1 = getCTAInfo(currentNode, 1);
        cta2 = getCTAInfo(currentNode, 2);

        title = StringUtils.isNotBlank(title) ? title : "";
        description = StringUtils.isNotBlank(description) ? description : "";
        //add style for description
        description = description.replaceFirst("<p>", "<p class=\"video-des-below-heading\">");

        //check internal or external link
        isTitleLink = false;
        if (StringUtils.isNotBlank(headlineLink)) {
            if (!headlineLink.toLowerCase().startsWith("http")) {
                if (!Constants.EXTENSION_PATTERN_LINK.matcher(headlineLink).matches() && !headlineLink.startsWith("#")) {
                    headlineLink += Constants.SUFFIX_HTML;
                    isTitleLink = true;
                }
            }
        }
    }

    private CTA getCTAInfo(Node currentNode, int index) {
        CTA cta = new CTA();
        try {
            //get data
            String title = JcrUtils.getStringValue(currentNode, "titleCTA" + index);
            String link = JcrUtils.getStringValue(currentNode, "linkCTA" + index);
            String buttonType = JcrUtils.getStringValue(currentNode, "buttonType" + index);

            //process data
            String target = CommonUtils.getTargetByLink(link);
            link = CommonUtils.getFormattedLink(link);
            boolean isDisplay = false;
            if (StringUtils.isNotBlank(title) && StringUtils.isNotBlank(link)) {
                isDisplay = true;
            }
            String style = CommonUtils.getBtnTypeStyleByTitle(title, buttonType);
            /*if (StringUtils.isNotBlank(buttonType)) {
                if (StringUtils.containsIgnoreCase(buttonType, "green")) {
                    style = "grnBtn";
                } else if (StringUtils.containsIgnoreCase(buttonType, "white")) {
                    style = "grnBtn whiteBtn";
                } else if (StringUtils.containsIgnoreCase(buttonType, "outline")) {
                    style = "grnBtn outlineBtn";
                } else if (StringUtils.containsIgnoreCase(buttonType, "text")) {
                    style = "link";
                }
            }*/
            //assign info
            cta.setTitle(title);
            cta.setLink(link);
            cta.setTarget(target);
            cta.setStyle(style);
            cta.setButtonType(buttonType);
            cta.setDisplay(isDisplay);
        } catch (Exception e) {
            logger.error("cannot get cta info", e);
            cta = null;
        }
        return cta;
    }

    private void processItemType(SlingHttpServletRequest slingRequest, Node currentNode) throws RepositoryException, JSONException {
        if (StringUtils.isNotBlank(itemType) && itemType.equalsIgnoreCase("video")) {
            String videoPath = JcrUtils.getStringValue(currentNode, "videoPath");
            Video video = VideoUtils.getVideo(slingRequest, videoPath);
            this.video = convertVideoToVideoLibrary(video);
        } else if (StringUtils.isNotBlank(itemType) && itemType.equalsIgnoreCase("library")) {
            //do nothing
        } else {
            image = new Image(slingRequest.getResource(), "image");
            image.setSelector(".img");
        }
    }

    private com.calix.model.VideoLibrary convertVideoToVideoLibrary(Video video) {
        List<Video> videos = new ArrayList<Video>();
        videos.add(video);
        VideoGroup videoGroup = new VideoGroup();
        videoGroup.setVideos(videos);
        List<VideoGroup> videoGroups = new ArrayList<VideoGroup>();
        videoGroups.add(videoGroup);
        com.calix.model.VideoLibrary videoLibrary = new com.calix.model.VideoLibrary();
        videoLibrary.setVideoGroups(videoGroups);
        videoLibrary.setMainVideo(video);
        return videoLibrary;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public Image getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHeadlineLink() {
        return headlineLink;
    }

    public void setHeadlineLink(String headlineLink) {
        this.headlineLink = headlineLink;
    }

    public boolean getIsTitleLink() {
        return isTitleLink;
    }

    public void setIsTitleLink(boolean isTitleLink) {
        this.isTitleLink = isTitleLink;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CTA getCta1() {
        return cta1;
    }

    public void setCta1(CTA cta1) {
        this.cta1 = cta1;
    }

    public CTA getCta2() {
        return cta2;
    }

    public void setCta2(CTA cta2) {
        this.cta2 = cta2;
    }

    public com.calix.model.VideoLibrary getVideo() {
        return video;
    }

    public void setVideo(com.calix.model.VideoLibrary video) {
        this.video = video;
    }

    public String getModalId() {
        return modalId;
    }

    public void setModalId(String modalId) {
        this.modalId = modalId;
    }
}