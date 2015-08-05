package com.calix.utils;

import java.util.regex.Pattern;

public class Constants {

    public static final String FORWARD_SLASH = "/";
    public static final String JCR_PRIMARY_TYPE = "jcr:primaryType";
    public static final String CQ_PAGE = "cq:Page";
    public static final String DOT_HTML = ".html";
    public static final String TAGS_PATH = "/etc/tags";
	public static final String NT_BASE = "nt:base";
	public static final String EXISTS_OPERATION = "exists";

    public static final String ALL = "all";
    public static final String PAGE = "page";
    public static final String STATIC = "static";
    public static final String JCR_CONTENT_SLING_RESOURCE_TYPE = "@jcr:content/sling:resourceType";
    public static final String SLING_RESOURCE_TYPE = "sling:resourceType";



    public static final String TWITTER_STATUS = "status";
    public static final String DATE_FORMAT_MMM_DD_YYYY = "MMM dd, yyyy";
    public static final String DATE_FORMAT_MMMM_DD_YYYY = "MMMM dd, yyyy";
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String TWITTER_URL = "https://twitter.com/";


    //Type
    public static final String IMAGE_TYPE = "image";
    public static final String TEXT_TYPE = "text";
    public static final String VIDEOS_TYPE = "videos";
    public static final String VIDEO_TYPE = "video";
    public static final String SPEAKERS_TYPE = "speaker";
    public static final String TWITTER_TYPE ="twitter";
    public static final String NEWS_TYPE ="news";
    public static final String EVENT_TYPE ="event";
    public static final String PRESS_RELEASE_TYPE = "pRelease";
    public static final String VIDEO_LIBRARY_TYPE = "videoLibrary";

    public static final String LIMIT_IMAGE = "5";

    //Label
    public static final String DEFAULT_SPOTLIGHT_TITLE = "Calix in the Media";
    public static final String PHOTO_LABEL = "Photo from the field";
    public static final String FEATURED_VIDEO_LABEL = "Featured Video";
    public static final String VIEW_ALL_LABEL = "View All";
    public static final String TWITTER_LABEL ="Twitter";
    public static final String NEWS_LABEL ="News";
    public static final String EVENT_LABEL ="Events";
    public static final String SPEAKERS_LABEL = "Speakers";
    public static final String PRESS_RELEASE_LABEL = "Press Releases";
    public static final String ALL_LABEL = "All";
    public static final String MY_MODAL = "myModal";
    public static final String CALIX_EVENT = "Calix Event";
    public static final String CALIX_SPEAKER = "Calix Speaker";
    public static final String FEATURED_LABEL = "Featured";
    public static final String LOAD_MORE_LABEL = "Load more";

    //Rules
    public static final String DEFAULT_CHARACTER = "150";
    //name
    public static final String IMAGES_NAME = "images";

    //format link
    public static final Pattern EXTENSION_PATTERN_LINK = Pattern.compile(".*\\.\\w{2,4}$");
    public static final String SUFFIX_HTML = ".html";
    public static final String PREFIX_HTTP = "http://";
    public static final String PREFIX_HTTPS = "https://";
    
	public static final String EXTERNAL_ICON = "externalIcon";

    // css
    public static final String VIDEO_CLASS = "videoModule";
    public static final String EVENT_CLASS = "eventModule";
    public static final String SPEAKER_CLASS = "speakerModule";

    // templage path
    public static final String FULLWIDTH_TEMPLATE_RESOURCE_TYPE = "calix/components/page/fullwidth";
    public static final String SPLITCOLUMN_TEMPLATE_RESOURCE_TYPE = "calix/components/page/splitcolumn";

    //video
    public static final String JCR_CONTENT_NARROW_CONTAINER_VIDEO = "/jcr:content/narrowContainer/video";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String WISTIA_ID = "wistiaId";
    public static final String IMAGE_LINK = "imageLink";
    public static final String RELATED_LABEL = "Related Videos";
    
    // QueryBuilder params
    public static final String PROPERTY = "property";
    public static final String PROPERTY_VALUE = "property.value";
    
    // Content Urls
    public static final String LEADER_PAGE = "/content/calix/en/site/leader";
    
    // Components
    public static final String PROFILE_RESOURCE_TYPE = "calix/components/ui/profile";
    public static final String PRESS_NEWS_RELEASE_RESOURCE_TYPE = "calix/components/ui/press-news-release";
    public static final String EVENT_SPEAKER_DETAIL_RESOURCE_TYPE = "calix/components/ui/event-or-speaker-detail";

    // Leadership tab name
    public static final String EXECUTIVE = "executive";
    public static final String DIRECTORS = "directors";
    
    // Properties
    public static final String PROFILE_DEPARTMENT = "department";
    
    // Target
	public static final String BLANK_TARGET = "_blank";
	public static final String SEFL_TARGET = "_self";
}