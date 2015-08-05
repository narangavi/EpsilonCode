package com.calix.components;

import java.util.List;

import javax.jcr.Node;
import javax.jcr.Session;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUse;
import com.calix.model.CTA;
import com.calix.model.GenericItem;
import com.calix.model.Video;
import com.calix.model.VideoGroup;
import com.calix.utils.CommonUtils;
import com.calix.utils.Constants;
import com.calix.utils.JcrUtils;
import com.calix.utils.VideoUtils;

/**
 * Spotlight Class.
 *
 * @author epsilon-Thao
 */

public class GenericContent extends WCMUse {

    /**
     * The Constant logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(GenericContent.class);

    /**
     * The generic content.
     */
    private GenericItem item1;
    private GenericItem item2;
    private String compTitle;
    private String curveStyle;
    private String containerStyle;
    private Boolean displayOneColumn;

    public GenericItem getItem1() {
        return item1;
    }

    public GenericItem getItem2() {
        return item2;
    }

    public Boolean getDisplayOneColumn() {
        return displayOneColumn;
    }

    public String getCurveStyle() {
        return curveStyle;
    }

    public String getContainerStyle() {
        return containerStyle;
    }

    public String getCompTitle() {
        return compTitle;
    }

    /* (non-Javadoc)
     * @see com.adobe.cq.sightly.WCMUse#activate()
     */
    @Override
    public void activate() throws Exception {
        SlingHttpServletRequest slingRequest = this.getRequest();
        final Session session = CommonUtils.getSession(slingRequest);
        Node currentNode = slingRequest.getResource().adaptTo(Node.class);

        //get general label
        boolean displayTopCurves = JcrUtils.getBooleanValue(currentNode, "displayTopCurves");
        boolean displayBottomCurves = JcrUtils.getBooleanValue(currentNode, "displayBottomCurves");
        boolean shortContainer = JcrUtils.getBooleanValue(currentNode, "shortContainer");
        boolean displayBG = JcrUtils.getBooleanValue(currentNode, "displayBG");
        String numberCol = JcrUtils.getStringValue(currentNode, "columns");
        displayOneColumn = (StringUtils.containsIgnoreCase(numberCol, "one") ? true : false);
        curveStyle = "";
        //get curve style
        containerStyle = "heroFullCurvedContainer";
        if (displayTopCurves) {
            curveStyle += " curve-top";
        }
        if (displayBottomCurves) {
            curveStyle += " curve-bottom";
        }

        curveStyle += displayBG ? " both-background" : " no-background";
        compTitle = JcrUtils.getStringValue(currentNode, "compTitle");
        //fill data for generic
        item1 = getObjectWithType(1, currentNode, slingRequest);
        item2 = getObjectWithType(2, currentNode, slingRequest);
        if (displayOneColumn) {
            if (!item1.getDisplayData()) {
                if (null != item2 && item2.getType().equalsIgnoreCase(Constants.TEXT_TYPE)) {
                    item1 = item2;
                }
            }
        }
    }

    /**
     * Convert node to CTA.
     *
     * @param item the CTA
     * @return the CTA
     */
    private static CTA convertNodeToCTA(Node currentNode, int index, int column) {
        CTA cta = null;
        if (null != currentNode) {
            cta = new CTA();
            String title = JcrUtils.getStringValue(currentNode, "titleCTA" + index);
            String link = JcrUtils.getStringValue(currentNode, "linkCTA" + index);
            String target = CommonUtils.getTargetByLink(link);
            link = CommonUtils.getFormattedLink(link);
            String btnType = JcrUtils.getStringValue(currentNode, "btnType" + index);
            cta.setTitle(title);
            cta.setLink(link);
            cta.setButtonType(btnType);
            cta.setDisplay(false);
            cta.setTarget(target);
            cta.setStyle("grnBtn");
            if (StringUtils.isNotBlank(title) && StringUtils.isNotBlank(link)) {
                cta.setDisplay(true);
            }

            if (StringUtils.isNotBlank(btnType)) {
                if (StringUtils.containsIgnoreCase(btnType, "green")) {
                    if (StringUtils.isNotBlank(title) && title.length() > 8) {
                        cta.setStyle("grnBtnLg");
                    } else {
                        cta.setStyle("grnBtn");
                    }
                } else if (StringUtils.containsIgnoreCase(btnType, "white")) {
                    cta.setStyle("grnBtn whiteBtn");
                    if (StringUtils.isNotBlank(title) && title.length() > 8) {
                        cta.setStyle("grnBtnLg whiteBtn");
                    }
                } else if (StringUtils.containsIgnoreCase(btnType, "outline")) {
                    cta.setStyle("grnBtn outlineBtn");
                    if (StringUtils.isNotBlank(title) && title.length() > 8) {
                        cta.setStyle("grnBtnLg outlineBtn");
                    }
                } else if (StringUtils.containsIgnoreCase(btnType, "text")) {
                    cta.setStyle("link");
                    if (StringUtils.isNotBlank(title) && title.length() > 8) {
                        cta.setStyle("linkLg");
                    }
                }
            }
        }
        return cta;
    }

    private static CTA convertNodeToCTA2(Node currentNode, int index, int itemIndex) {
        CTA cta = null;
        if (null != currentNode) {
            cta = new CTA();
            String title = JcrUtils.getStringValue(currentNode, "titleCTA" + index + itemIndex);
            String link = JcrUtils.getStringValue(currentNode, "linkCTA" + index + itemIndex);
            String target = CommonUtils.getTargetByLink(link);
            link = CommonUtils.getFormattedLink(link);
            String btnType = JcrUtils.getStringValue(currentNode, "btnType" + index + itemIndex);
            cta.setTitle(title);
            cta.setLink(link);
            cta.setButtonType(btnType);
            cta.setDisplay(false);
            cta.setTarget(target);
            cta.setStyle("grnBtn");
            if (StringUtils.isNotBlank(title) && StringUtils.isNotBlank(link)) {
                cta.setDisplay(true);
            }
            cta.setStyle(CommonUtils.getBtnTypeStyleByTitle(title, btnType));
        }
        return cta;
    }

    private GenericItem getObjectWithType(int index, Node currentNode, SlingHttpServletRequest slingRequest) {
        GenericItem genericItem = new GenericItem();
        Boolean displayBG = JcrUtils.getBooleanValue(currentNode, "displayBG" + index);
        genericItem.setBgStyle(displayBG ? "backgroundColor" : "");
        genericItem.setDisplayBG(displayBG);
        try {
            String type = getProperties().get("type" + index, StringUtils.EMPTY);
            if (StringUtils.isNotBlank(type)) {
                if (StringUtils.containsIgnoreCase(type, Constants.IMAGE_TYPE)) {
                    String imagePath = JcrUtils.getStringValue(currentNode, "image" + index);
                    final Session session = CommonUtils.getSession(slingRequest);
                    Node node = session.getNode(imagePath);
                    String caption = JcrUtils.getStringValue(node, "jcr:content/metadata/dc:captiontext");
                    genericItem.setImagePath(imagePath);
                    genericItem.setCaption(caption);
                    genericItem.setType(Constants.IMAGE_TYPE);
                    genericItem.setDisplayData(false);
                } else if (StringUtils.containsIgnoreCase(type, Constants.TEXT_TYPE)) {
                    genericItem.setCta1(convertNodeToCTA2(currentNode, index, 1));
                    genericItem.setCta2(convertNodeToCTA2(currentNode, index, 2));


                    String title = JcrUtils.getStringValue(currentNode, "title" + index);
                    String desc = JcrUtils.getStringValue(currentNode, "desc" + index);
                    genericItem.setTitle(title);
                    genericItem.setDesc(desc);
                    genericItem.setType(Constants.TEXT_TYPE);
                    genericItem.setDisplayData((StringUtils.isNotBlank(title) || StringUtils.isNotBlank(desc)) ? true : false);
                } else if (StringUtils.equalsIgnoreCase(type, Constants.VIDEOS_TYPE + index)) {
                    genericItem.setBgStyle(displayBG ? " videoPreview backgroundColor" : " videoPreview");
                    genericItem.setType(Constants.VIDEOS_TYPE);
                    genericItem.setDisplayData(false);
                } else if (StringUtils.equalsIgnoreCase(type, Constants.VIDEO_TYPE + index)) {
                    genericItem.setBgStyle(displayBG ? " videoPreview backgroundColor" : " videoPreview");
                    genericItem.setType(Constants.VIDEO_TYPE);
                    genericItem.setDisplayData(false);
                    // get main video
                    String videoPath = JcrUtils.getStringValue(currentNode, "video" + index);
                    List<VideoGroup> videos = VideoUtils.getListVideoGroup(slingRequest, videoPath);
                    Video video = VideoUtils.getFirstVideoFromList(videos);
                    String modalId = VideoUtils.getModalId(currentNode);
                    genericItem.setMainVideo(video);
                    genericItem.setVideoGroups(videos);
                    genericItem.setModalId(modalId);

                }
            }
        } catch (Exception ex) {
            logger.error("Exception - getObjectWithType: ", ex);
        }
        return genericItem;
    }
}
