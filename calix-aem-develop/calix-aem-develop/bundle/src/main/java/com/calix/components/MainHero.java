package com.calix.components;

import com.adobe.cq.sightly.WCMUse;
import com.calix.model.Slide;
import com.calix.model.Video;
import com.calix.model.VideoGroup;
import com.calix.utils.CommonUtils;
import com.calix.utils.JcrUtils;

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

public class MainHero extends WCMUse {

    /**
     * The Constant logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(MainHero.class);

    /**
     * The Constant JCR_CONTENT_NARROW_CONTAINER_VIDEO.
     */
    public static final String JCR_CONTENT_NARROW_CONTAINER_VIDEO = "/jcr:content/narrowContainer/video";

    /**
     * The Constant TITLE.
     */
    public static final String TITLE = "title";

    /**
     * The Constant DESCRIPTION.
     */
    public static final String DESCRIPTION = "description";

    /**
     * The Constant WISTIA_ID.
     */
    public static final String WISTIA_ID = "wistiaId";

    /**
     * The Constant IMAGE_LINK.
     */
    public static final String IMAGE_LINK = "imageLink";
    /**
     * The slides.
     */
    private List<Slide> slides;

    /**
     * The container style.
     */
    private String containerStyle;

    /**
     * The curve style.
     */
    private String curveStyle;

    /**
     * The video path.
     */
    private String videoPath;

    /**
     * The violet bg.
     */
    private String violetBG;

    /**
     * Gets the violet bg.
     *
     * @return the violet bg
     */
    public String getVioletBG() {
        return violetBG;
    }

    /**
     * Gets the container style.
     *
     * @return the container style
     */
    public String getContainerStyle() {
        return containerStyle;
    }

    /**
     * Gets the curve style.
     *
     * @return the curve style
     */
    public String getCurveStyle() {
        return curveStyle;
    }

    /**
     * Gets the slides.
     *
     * @return the slides
     */
    public List<Slide> getSlides() {
        return slides;
    }

    /**
     * Gets the video path.
     *
     * @return the video path
     */
    public String getVideoPath() {
        return videoPath;
    }


    /* (non-Javadoc)
     * @see com.adobe.cq.sightly.WCMUse#activate()
     */
    @Override
    public void activate() throws Exception {
        try {
            SlingHttpServletRequest slingRequest = this.getRequest();
            final Session session = CommonUtils.getSession(slingRequest);
            Node currentNode = slingRequest.getResource().adaptTo(Node.class);
            //get general label
            containerStyle = "heroFullCurvedContainer";
            curveStyle = "";
            violetBG = " violetBG";

            boolean displayTopCurve = JcrUtils.getBooleanValue(currentNode, "displayTopCurves");
            boolean displayBottomCurve = JcrUtils.getBooleanValue(currentNode, "displayBottomCurves");
            boolean shortContainer = JcrUtils.getBooleanValue(currentNode, "shortContainer");

            if (shortContainer) {
                containerStyle = "heroFullShortCurvedContainer";
            }
            if (!displayBottomCurve) {
                violetBG = "";
            }
            //get style of curve
            if (displayTopCurve) {
                curveStyle += "curve-top";
            }
            if (displayBottomCurve) {
                curveStyle += " curve-bottom";
            }
            //get slides
            slides = new ArrayList<Slide>();
            String[] values = JcrUtils.getStringArrayValue(currentNode, "listItems");
            slides = CommonUtils.getSlides(values);

            videoPath = currentNode.getPath().substring(1).replace("/", "-").replace(":", "-") + "-videoLib_";

            //get video
            int count = 0;
            for (Slide slide : slides) {
                String ctaLink = slide.getLinkCTA();
                if (StringUtils.isNotBlank(ctaLink)) {
                    if (ctaLink.contains(".html")) {
                        ctaLink = ctaLink.replace(".html", "");
                    }
                    List<VideoGroup> videoGroups = getListVideoGroup(slingRequest, ctaLink);
                    slide.setVideoGroups(videoGroups);
                    slide.setIsVideo(false);
                    String wistaID = getWistiaId(videoGroups);

                    if (StringUtils.isNotBlank(wistaID)) {
                        slide.setIsVideo(true);
                    }
                    String modalId = videoPath + count + "";
                    slide.setModalId(modalId);
                    count++;

                }
            }

        } catch (Exception ex) {
            logger.error("Exeption Main Hero: ", ex);
        }
    }

    public static String getWistiaId(List<VideoGroup> videoGroups) {
        String wistaID = "";
        if (null != videoGroups && videoGroups.size() > 0) {
            VideoGroup firstVideoGroup = videoGroups.get(0);
            if (null != firstVideoGroup) {
                List<Video> videos = firstVideoGroup.getVideos();
                if (null != videos && videos.size() > 0) {
                    Video firstVideo = videos.get(0);
                    if (null != firstVideo) {
                        wistaID = firstVideo.getWistiaId();
                    }
                }
            }
        }
        return wistaID;
    }


    /**
     * Gets the list video group.
     *
     * @param slingRequest the sling request
     * @param ctaLink      the cta link
     * @return the list video group
     */
    private List<VideoGroup> getListVideoGroup(SlingHttpServletRequest slingRequest, String ctaLink) {
        List<VideoGroup> videoGroups = new ArrayList<VideoGroup>();
        if (StringUtils.isNotBlank(ctaLink)) {
            Video video = new Video();
            video = getVideo(slingRequest, ctaLink);

            String wistaId = video.getWistiaId();
            if (StringUtils.isNotBlank(wistaId)) {
                VideoGroup group = new VideoGroup();
                List<Video> videos = new ArrayList<Video>();
                videos.add(video);
                group.setVideos(videos);
                videoGroups.add(group);
            }
        }

        return videoGroups;
    }
    /**
     * Gets the video.
     *
     * @param slingRequest the sling request
     * @param path         the path
     * @return the video
     */
    private Video getVideo(SlingHttpServletRequest slingRequest, String path) {
        Video video = new Video();
        try {
            Node videoNode = JcrUtils.getNode(slingRequest, path + JCR_CONTENT_NARROW_CONTAINER_VIDEO);

            video.setTitle(JcrUtils.getStringValue(videoNode, TITLE));
            video.setDescription(JcrUtils.getStringValue(videoNode, DESCRIPTION));
            video.setWistiaId(JcrUtils.getStringValue(videoNode, WISTIA_ID));
            video.setImageLink(JcrUtils.getStringValue(videoNode, IMAGE_LINK));
        } catch (RepositoryException e) {
            logger.error("Error when convert search result {}", e);
        }
        return video;
    }
    //get unique modalId
    private String getModalId(Node currentNode) {

        String modalId = StringUtils.EMPTY;
        try {
            modalId = currentNode.getPath().substring(1).replace("/", "-").replace(":", "-");
        } catch (RepositoryException e) {
            logger.error("Error when get modalId {}", e);
        }
        return modalId;
    }
}
