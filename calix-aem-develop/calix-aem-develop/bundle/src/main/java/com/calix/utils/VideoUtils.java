package com.calix.utils;

import com.calix.model.Video;
import com.calix.model.VideoGroup;
import com.calix.model.VideoLibrary;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * VideoUtils Class.
 *
 * @author epsilon-khoa
 */

public class VideoUtils {

    /** The Constant logger. */
    private static final Logger  logger = LoggerFactory.getLogger(VideoUtils.class);
    public static final String PATH = "path";
    public static final String PROPERTY = "property";
    public static final String PROPERTY_VALUE = "property.value";
    public static final String SLING_RESOURCE_TYPE = "sling:resourceType";
    public static final String JCR_CONTENT = "/jcr:content";
    public static final String GROUP = "group";
    public static final String VIDEOS = "videos";

    public static Video getVideo(final SlingHttpServletRequest slingRequest, final String path){
        Video video = null;
        try {
            logger.info("Method getVideo() - video path: " + path);
            if(!StringUtils.isBlank(path)) {
                final ResourceResolver resourceResolver = slingRequest.getResourceResolver();
                final QueryBuilder queryBuilder = resourceResolver.adaptTo(QueryBuilder.class);
                Session session = CommonUtils.getSession(slingRequest);
                Map<String, String> map = new HashMap<String, String>();
                map.put(PATH, path + JCR_CONTENT);
                map.put(PROPERTY, SLING_RESOURCE_TYPE);
                map.put(PROPERTY_VALUE, "calix/components/ui/video");
                Query query = queryBuilder.createQuery(PredicateGroup.create(map), session);

                SearchResult result = query.getResult();
                if (result.getHits().size() > 0) {
                    //get video path
                    String videoPath = result.getHits().get(0).getPath();
                    try {
                        Node videoNode = JcrUtils.getNode(slingRequest, videoPath);
                        video = getVideoFromNode(videoNode);
                    } catch (RepositoryException e) {
                        logger.error("Error when get info from video node", e);
                        video = null;
                    }
                }
            }
        }
        catch(Exception e){
            logger.error("Error when search video node: ", e);
        }
        return video;
    }

    private static Video getVideoFromNode(final Node videoNode) {
        Video video = null;
        if(videoNode != null) {
            video = new Video();
            video.setTitle(JcrUtils.getStringValue(videoNode, Video.TITLE));
            video.setDescription(JcrUtils.getStringValue(videoNode, Video.DESCRIPTION));
            video.setWistiaId(JcrUtils.getStringValue(videoNode, Video.WISTIA_ID));
            video.setImageLink(JcrUtils.getStringValue(videoNode, Video.IMAGE_LINK));
        }
        return video;
    }

    public static VideoLibrary getVideoLibraryFromProperty(final SlingHttpServletRequest slingRequest, final Node currentNode, final String propertyName) throws RepositoryException, JSONException {
        VideoLibrary library = new VideoLibrary();
        List<VideoGroup> videoGroups = new ArrayList<VideoGroup>();
        Property property = null;
        try {
            if (currentNode.hasProperty(propertyName)) {
                property = currentNode.getProperty(propertyName);
            }
            logger.info("Method getVideoLibraryFromProperty() - property[" + propertyName + "]: " + property);
            if (property != null) {
                JSONObject groupJSON = null, videoJSON = null;
                Value[] values = null;

                if (property.isMultiple()) {
                    values = property.getValues();
                } else {
                    values = new Value[1];
                    values[0] = property.getValue();
                }
                int i = 0;
                for (Value val : values) {
                    VideoGroup group = new VideoGroup();
                    groupJSON = new JSONObject(val.getString());
                    group.setTitle(groupJSON.get(GROUP).toString());
                    if (groupJSON.has(VIDEOS)) {
                        JSONArray videos = (JSONArray) groupJSON.get(VIDEOS);

                        if (videos != null) {
                            for (int index = 0, length = videos.length(); index < length; index++) {
                                videoJSON = (JSONObject) videos.get(index);
                                Video video = getVideo(slingRequest, videoJSON.get(PATH).toString());
                                if(null != video){
	                                video.setId(i);
	                                group.getVideos().add(video);
	                                library.setNumOfRelated(++i);
                                }
                            }
                        }
                    }
                    videoGroups.add(group);
                }
                library.setVideoGroups(videoGroups);
                library.setMainVideo(getFirstVideoFromList(videoGroups));
            } else {
                library = null;
            }
        } catch (Exception e){
            logger.error("Cannot get list video groups: ", e);
            library = null;
        }
        return library;
    }

    public static Video getFirstVideoFromList(List<VideoGroup> videoGroups) {
        if(videoGroups != null && videoGroups.size() > 0){
            if(videoGroups.get(0).getVideos() != null && videoGroups.get(0).getVideos().size() > 0){
                return videoGroups.get(0).getVideos().get(0);
            }
        }
        return null;
    }
	
	 /**
     * Gets the list video group.
     *
     * @param slingRequest the sling request
     * @param ctaLink the cta link
     * @return the list video group
     */
    public static List<VideoGroup> getListVideoGroup(SlingHttpServletRequest slingRequest, String ctaLink){
        List<VideoGroup> videoGroups = new ArrayList<VideoGroup>();
        if(StringUtils.isNotBlank(ctaLink)){
            Video video = new Video();
            video = getVideo(slingRequest, ctaLink);

            String wistaId = video.getWistiaId();
            if(StringUtils.isNotBlank(wistaId)){
                VideoGroup group = new VideoGroup();
                List<Video> videos = new ArrayList<Video>();
                videos.add(video);
                group.setVideos(videos);
                videoGroups.add(group);
            }

        }

        return videoGroups;
    }
	
	 public static String getModalId(Node currentNode){

        String modalId = StringUtils.EMPTY;
        try {
            modalId = currentNode.getPath().substring(1).replace("/", "-").replace(":", "-") + "-videoLib_";
        } catch (RepositoryException e) {
            logger.error("Error when get modalId {}", e);
        }
        return modalId;
    }

    public static com.calix.model.VideoLibrary convertVideoToVideoLibrary(Video video) {
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
}