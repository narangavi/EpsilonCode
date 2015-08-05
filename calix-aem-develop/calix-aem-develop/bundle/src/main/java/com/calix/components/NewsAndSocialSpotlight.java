package com.calix.components;

import com.adobe.cq.sightly.WCMUse;
import com.calix.model.Box;
import com.calix.model.Feed;
import com.calix.model.Spotlight;
import com.calix.utils.*;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Twitter;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.day.cq.wcm.api.Page;

import java.util.*;

/**
 * Spotlight Class.
 *
 * @author epsilon-Thao
 */

public class NewsAndSocialSpotlight extends WCMUse {

    /**
     * The Constant logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(NewsAndSocialSpotlight.class);

    private Box box1;
    private Box box2;
    private List<Spotlight> spotlight1;
    private List<Spotlight> spotlight2;
    private List<Spotlight> spotlight3;
    private String titleLabel;

    public String getDisplayCurve() {
        return displayCurve;
    }

    public void setDisplayCurve(String displayCurve) {
        this.displayCurve = displayCurve;
    }

    private String displayCurve;

    public Box getBox1() {
        return box1;
    }

    public Box getBox2() {
        return box2;
    }

    public List<Spotlight> getSpotlight1() {
        return spotlight1;
    }

    public List<Spotlight> getSpotlight2() {
        return spotlight2;
    }

    public List<Spotlight> getSpotlight3() {
        return spotlight3;
    }

    public String getTitleLabel() {
        return titleLabel;
    }

    /* (non-Javadoc)
     * @see com.adobe.cq.sightly.WCMUse#activate()
     */
    @Override
    public void activate() throws Exception {
        SlingHttpServletRequest slingRequest = this.getRequest();
        final Session session = CommonUtils.getSession(slingRequest);
        Node currentNode = slingRequest.getResource().adaptTo(Node.class);
        String idLable = "";
        String idCarousel = "";
        if (null != currentNode) {
            idLable = currentNode.getParent().getName() + "_" + currentNode.getName();
            idCarousel = "myCarousel_" + idLable;
        }
        boolean displayTopCurve = JcrUtils.getBooleanValue(currentNode, "displayTopCurves");
        boolean displayBottomCurve = JcrUtils.getBooleanValue(currentNode, "displayBottomCurves");
        titleLabel = getProperties().get("title", Constants.DEFAULT_SPOTLIGHT_TITLE);
        displayCurve = "";
        if (displayTopCurve) {
            displayCurve += " curve-top";
        }
        if (displayBottomCurve) {
            displayCurve += " curve-bottom";
        }

        //get limit character
        Integer limitCharacter = 5;
        String limitCharacterString = getProperties().get("limitCharacter", Constants.DEFAULT_CHARACTER);

        if (org.apache.commons.lang3.StringUtils.isNotBlank(limitCharacterString)) {
            limitCharacter = Integer.valueOf(limitCharacterString);
        }
        //get limit images for feature 1 and 2
        String limitImageString = getProperties().get("limitImage", Constants.LIMIT_IMAGE);
        Integer limitImage = 5;
        if (org.apache.commons.lang3.StringUtils.isNotBlank(limitImageString)) {
            limitImage = Integer.valueOf(limitImageString);
        }
        //get Box 1
        box1 = getObjectWithFeatureType(1, limitImage, slingRequest, idLable, idCarousel, session, limitCharacter);
        //get Box 2
        box2 = getObjectWithFeatureType(2, limitImage, slingRequest, idLable, idCarousel, session, limitCharacter);

        //get spotlight 1
        spotlight1 = getObjectWithSpotlightType(1, slingRequest, limitCharacter);
        spotlight2 = getObjectWithSpotlightType(2, slingRequest, limitCharacter);
        spotlight3 = getObjectWithSpotlightType(3, slingRequest, limitCharacter);
    }

    /**
     * Convert feeds to spotlight.
     *
     * @param feeds      the feeds
     * @param screenName the screen name
     * @return the list
     */
    private List<Spotlight> convertFeedsToSpotlight(List<Feed> feeds, String screenName, int characterLimit) {
        String label = getProperties().get("twitterLabel", Constants.TWITTER_LABEL);
        List<Spotlight> spotlights = new ArrayList<Spotlight>();
        if (feeds != null) {
            Spotlight spotlight = null;
            String title = org.apache.commons.lang3.StringUtils.EMPTY;
            String date = org.apache.commons.lang3.StringUtils.EMPTY;
            ;
            for (Feed feed : feeds) {
                spotlight = new Spotlight();
                date = CommonUtils.formatDate(Constants.DATE_FORMAT_MMM_DD_YYYY, feed.getCreateDate());
                title = feed.getFeedContent();
                if (title != null && title.length() > characterLimit) {
                    title = title.substring(0, characterLimit);
                }
                spotlight.setDate(date);
                spotlight.setLinkAction(feed.getFeedLink());
                spotlight.setTarget(CommonUtils.getTargetByLink(feed.getFeedLink()));
                spotlight.setType(Constants.TWITTER_TYPE);
                spotlight.setLabel(label);
                spotlight.setTitle(title);
                spotlights.add(spotlight);
            }
        }
        return spotlights;
    }

    /**
     * Convert search result to Spotlight.
     *
     * @param result       the result
     * @param type         the type
     * @param slingRequest the sling request
     * @return the list
     */
    private Spotlight getSpotlighByPath(String path, String type,
                                        SlingHttpServletRequest slingRequest, int characterLimit) {
        Spotlight spotlight = null;
        String title = org.apache.commons.lang3.StringUtils.EMPTY;
        String createDate = org.apache.commons.lang3.StringUtils.EMPTY;
        String modifiedDate = "";
        String imagePath = "";
        //get limit character
        Integer limitCharacter = 5;
        String limitCharacterString = getProperties().get("limitCharacter", Constants.DEFAULT_CHARACTER);
        try {
            String resourceType = Constants.EVENT_SPEAKER_DETAIL_RESOURCE_TYPE;
            if (StringUtils.containsIgnoreCase(type, Constants.PRESS_RELEASE_TYPE)) {
                resourceType = Constants.PRESS_NEWS_RELEASE_RESOURCE_TYPE;
            }
            Node node = JcrUtils.getNodeByPagePath(slingRequest, path, Constants.SLING_RESOURCE_TYPE, resourceType);
            if (null != node) {
                spotlight = new Spotlight();
                title = JcrUtils.getStringValue(node, "title");
                createDate = JcrUtils.getStringValue(node, "date");
                if (StringUtils.isNotBlank(createDate)) {
                    Date date = CommonUtils.convertStringToDateNoTimeZone(Constants.DATE_FORMAT_YYYY_MM_DD, createDate);
                    modifiedDate = CommonUtils.formatDate(Constants.DATE_FORMAT_MMM_DD_YYYY, date);
                }
                imagePath = JcrUtils.getStringValue(node, "mainImage");
                if (StringUtils.isNotBlank(title) && title.length() > characterLimit) {
                    title = title.substring(0, characterLimit);
                }
                spotlight.setDate(modifiedDate);
                spotlight.setTitle(title);
                spotlight.setImage(imagePath);
                spotlight.setType(type);
                spotlight.setLabel(getSpotlightLabel(type));
                spotlight.setLinkAction(path + Constants.DOT_HTML);
                spotlight.setTarget(CommonUtils.getTargetByLink(path));
            }
        } catch (Exception e) {
            logger.error("Error when convert search result {}", e);
        }
        return spotlight;
    }

    /**
     * Gets the events content.
     *
     * @param eventsPath   the events path
     * @param limitItems   the limit items
     * @param slingRequest the sling request
     * @return the events content
     */
    private List<Spotlight> getSpotlightByRootPath(String path, String type, SlingHttpServletRequest slingRequest, int characterLimit) {
        List<Spotlight> spotlightList = new ArrayList<Spotlight>();
        String[] values = new String[1];

        String resourceType = Constants.EVENT_SPEAKER_DETAIL_RESOURCE_TYPE;
        if (StringUtils.containsIgnoreCase(type, Constants.PRESS_RELEASE_TYPE) || StringUtils.containsIgnoreCase(type, Constants.NEWS_TYPE)) {
            resourceType = Constants.PRESS_NEWS_RELEASE_RESOURCE_TYPE;
        }
        values[0] = resourceType;
        if (StringUtils.isNotBlank(path)) {
            Map<String, String> params = QueryBuilderUtils.buildParam(Constants.NT_BASE, 1, "", "", path);
            QueryBuilderUtils.buildOrderParam(params, "desc", "@date");
            QueryBuilderUtils.buildMultiplePropertyValues(params, Constants.SLING_RESOURCE_TYPE, values);
            SearchResult result = QueryBuilderUtils.executequery(slingRequest, params);
            spotlightList = convertSearchResultToSpotlight(result, type, slingRequest, characterLimit);
        }
        return spotlightList;
    }

    /**
     * Convert search result to Spotlight.
     *
     * @param result       the result
     * @param type         the type
     * @param slingRequest the sling request
     * @return the list
     */
    private List<Spotlight> convertSearchResultToSpotlight(SearchResult result, String type,
                                                           SlingHttpServletRequest slingRequest, int characterLimit) {
        List<Spotlight> spotlights = new ArrayList<Spotlight>();
        try {
            if (result != null) {
                final Iterator<Node> pageResults = result.getNodes();
                while (pageResults.hasNext()) {
                    final Node node = pageResults.next();
                    Spotlight item = convertNodeToSpotlight(node, type, slingRequest);
                    if (null != item) {
                        spotlights.add(item);
                    }
                }
            }
        } catch (Exception ex) {
            logger.error("Exception - convertSearchResultToSpotlight: ", ex);
        }
        return spotlights;
    }

    private Spotlight convertNodeToSpotlight(Node node, String type, SlingHttpServletRequest slingRequest) {
        Spotlight item = new Spotlight();
        try {
            if (null != node) {
                String title = JcrUtils.getStringValue(node, "title");
                String createDate = JcrUtils.getStringValue(node, "date");
                String modifiedDate = "";
                if (StringUtils.isNotBlank(createDate)) {
                    Date date = CommonUtils.convertStringToDateNoTimeZone(Constants.DATE_FORMAT_YYYY_MM_DD, createDate);
                    modifiedDate = CommonUtils.formatDate(Constants.DATE_FORMAT_MMM_DD_YYYY, date);
                }
                String imagePath = JcrUtils.getStringValue(node, "mainImage");
                //update data for spotlight
                item.setTitle(title);
                item.setDate(modifiedDate);
                item.setImage(imagePath);
                item.setLabel(getSpotlightLabel(type));
                Page page = QueryBuilderUtils.getPageByNode(slingRequest, JcrUtils.findPageByNode(slingRequest, node));
                String path = page.getPath();
                item.setTarget(CommonUtils.getTargetByLink(path));
                item.setLinkAction(path + Constants.DOT_HTML);
            }
        } catch (Exception ex) {
            logger.error("Exception - convertNodeToSpotlight: ", ex);
        }
        return item;
    }

    //get label of type
    private String getSpotlightLabel(String type) {
        //get label from dialog
        String label = Constants.EVENT_LABEL;
        if (StringUtils.containsIgnoreCase(type, Constants.EVENT_TYPE)) {
            label = getProperties().get("eventLabel", Constants.EVENT_LABEL);
        } else if (StringUtils.containsIgnoreCase(type, Constants.NEWS_TYPE)) {
            label = getProperties().get("newsLabel", Constants.NEWS_LABEL);
        } else if (StringUtils.containsIgnoreCase(type, Constants.PRESS_RELEASE_TYPE)) {
            label = getProperties().get("pressRealseLabel", Constants.PRESS_RELEASE_LABEL);
        } else if (StringUtils.containsIgnoreCase(type, Constants.SPEAKERS_TYPE)) {
            label = getProperties().get("speakerLabel", Constants.SPEAKERS_LABEL);
        } else if (StringUtils.containsIgnoreCase(type, Constants.TWITTER_TYPE)) {
            label = getProperties().get("twitterLabel", Constants.TWITTER_LABEL);
        }
        return label;
    }

    private Box getObjectWithFeatureType(int index, int limitImage, SlingHttpServletRequest slingRequest,
                                         String idLabel, String idCarousel, Session session, int limitCharacter) {
        Box box = new Box();
        String featureType = getProperties().get("featureType" + index, StringUtils.EMPTY);
        String viewAllLabel = getProperties().get("viewAllLabel", Constants.VIEW_ALL_LABEL);
        String photoLabel = getProperties().get("photoLabel", Constants.PHOTO_LABEL);
        String featuredVideoLabel = getProperties().get("featuredVideoLabel", Constants.FEATURED_VIDEO_LABEL);
        box.setPhotoLabel(photoLabel);
        box.setFeaturedVideoLabel(featuredVideoLabel);
        box.setDisplayImage(false);//set default
        box.setDisplayVideo(false);
        box.setDisplayVideos(false);
        List<Spotlight> spotlights = null;
        String[] spotlightPaths = null;
        //get limit character
        try {
            if (StringUtils.isNotBlank(featureType)) {
                featureType = featureType.toLowerCase();
                if (StringUtils.containsIgnoreCase(featureType, Constants.IMAGE_TYPE)) {
                    box.setDisplayImage(true);
                    spotlights = new ArrayList<Spotlight>();
                    String[] images = getProperties().get("images" + index, String[].class);

                    if (images != null && images.length > 0) {
                        for (String imagePath : images) {
                            if (StringUtils.isNotBlank(imagePath)) {
                                //get caption date and text for image
                                Node node = session.getNode(imagePath);
                                String date = JcrUtils.getStringValue(node, "jcr:content/metadata/dc:captiondate");
                                String text = JcrUtils.getStringValue(node, "jcr:content/metadata/dc:captiontext");
                                spotlights.add(new Spotlight(imagePath, date, text));
                            }
                        }
                    }
                    box.setSpotlighList(spotlights);
                    //update the number of spotlight display on carousel
                    if (limitImage < spotlights.size()) {
                        spotlights = spotlights.subList(0, limitImage);
                        box.setViewAllLabel(viewAllLabel);
                        box.setIdModal(idLabel + index);
                        box.setIdCarousel(idCarousel + index);
                    }
                    box.setSpotlightDisplay(spotlights);
                } else if (StringUtils.equalsIgnoreCase(featureType, Constants.VIDEO_TYPE + index)) {
                    box.setDisplayVideo(true);
                    String videoPath = getProperties().get("video" + index, "");
                    if (StringUtils.isNotBlank(videoPath)) {
                        box.setVideoGroups(CommonUtils.getListVideoGroup(slingRequest, videoPath));
                        box.setVideo(CommonUtils.getVideo(slingRequest, videoPath));
                        box.setIdModal(idLabel + "Video" + index);
                    }
                } else if (StringUtils.equalsIgnoreCase(featureType, Constants.VIDEO_LIBRARY_TYPE + index)) {
                    box.setDisplayVideos(true);
                } else if (StringUtils.containsIgnoreCase(featureType, Constants.EVENT_TYPE)) {
                    spotlightPaths = getProperties().get("events" + index, String[].class);
                } else if (StringUtils.containsIgnoreCase(featureType, Constants.NEWS_TYPE)) {
                    spotlightPaths = getProperties().get("events" + index, String[].class);
                } else if (StringUtils.containsIgnoreCase(featureType, Constants.PRESS_RELEASE_TYPE)) {
                    spotlightPaths = getProperties().get("pReleases" + index, String[].class);
                } else if (StringUtils.containsIgnoreCase(featureType, Constants.SPEAKERS_TYPE)) {
                    spotlightPaths = getProperties().get("speakers" + index, String[].class);
                }
                if (null != spotlightPaths && spotlightPaths.length > 0) {
                    spotlights = new ArrayList<Spotlight>();
                    for (String path : spotlightPaths) {
                        Spotlight item = getSpotlighByPath(path, featureType, slingRequest, limitCharacter);
                        if (null != item && StringUtils.isNotBlank(item.getImage())) {
                            box.setDisplaySpotlight(true);
                            spotlights.add(item);
                        }
                    }
                    box.setSpotlighList(spotlights);
                    //update the number of spotlight display on carousel
                    if (limitImage < spotlights.size()) {
                        spotlights = spotlights.subList(0, limitImage);
                        box.setViewAllLabel(viewAllLabel);
                        box.setIdModal(idLabel + index);
                        box.setIdCarousel(idCarousel + index);
                    }
                    box.setSpotlightDisplay(spotlights);
                }
            }
        } catch (Exception ex) {
            logger.error("Exception: getObjectWithFeatureType: ", ex);
        }
        return box;
    }

    private List<Spotlight> getObjectWithSpotlightType(int index, SlingHttpServletRequest slingRequest, int limitCharacter) {
        String spotlightType = getProperties().get("spotlight" + index, StringUtils.EMPTY);
        String rootPath = "";
        String userID = "";
        List<Spotlight> spotlights = new ArrayList<Spotlight>();
        if (StringUtils.isNotBlank(spotlightType)) {
            //get events
            if (StringUtils.containsIgnoreCase(spotlightType, Constants.EVENT_TYPE)) {
                rootPath = getProperties().get("eventS" + index, StringUtils.EMPTY);
            } else if (StringUtils.containsIgnoreCase(spotlightType, Constants.NEWS_TYPE)) {
                rootPath = getProperties().get("newsS" + index, StringUtils.EMPTY);
            } else if (StringUtils.containsIgnoreCase(spotlightType, Constants.PRESS_RELEASE_TYPE)) {
                rootPath = getProperties().get("pReleaseS" + index, StringUtils.EMPTY);
            } else if (StringUtils.containsIgnoreCase(spotlightType, Constants.SPEAKERS_TYPE)) {
                rootPath = getProperties().get("speakerS" + index, StringUtils.EMPTY);
            }
            if (StringUtils.containsIgnoreCase(spotlightType, Constants.TWITTER_TYPE)) {
                userID = getProperties().get("userIDS" + index, StringUtils.EMPTY);
                if (org.apache.commons.lang3.StringUtils.isNotBlank(userID)) {
                    Twitter twitter = TwitterUtils.getTwitterInstance();
                    List<Feed> feeds = TwitterUtils.getNewsFeedByScreenName(twitter, userID, Integer.valueOf(1));
                    spotlights = convertFeedsToSpotlight(feeds, userID, limitCharacter);
                }
            } else if (StringUtils.isNotBlank(rootPath)) {
                if (org.apache.commons.lang3.StringUtils.isNotBlank(rootPath)) {
                    spotlights = getSpotlightByRootPath(rootPath, spotlightType, slingRequest, limitCharacter);
                }
            }
        }
        return spotlights;
    }
}
