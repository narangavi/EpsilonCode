package com.calix.components;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jcr.AccessDeniedException;
import javax.jcr.ItemNotFoundException;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUse;
import com.calix.model.Article;
import com.calix.model.Tab;
import com.calix.utils.CommonUtils;
import com.calix.utils.Constants;
import com.calix.utils.HubTab;
import com.calix.utils.JcrUtils;
import com.calix.utils.QueryBuilderUtils;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;


/**
 * The Class EventSpeaker.
 */
public class EventSpeaker extends WCMUse {

    /**
     * The Constant logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(EventSpeaker.class);

    /**
     * The Constant TOTAL_ITEMS.
     */
    private static final String TOTAL_ITEMS = "limitItems";

    /**
     * The Constant EVENTS_TABNAME.
     */
    private static final String EVENTS_TABNAME = "eventstab";

    /**
     * The Constant SPEAKERS_TABNAME.
     */
    private static final String SPEAKERS_TABNAME = "speakerstab";

    /**
     * The Constant FEATURED_TABNAME.
     */
    private static final String FEATURED_TABNAME = "featuredtab";

    /**
     * The Constant DISPLAY_ALL.
     */
    private static final String DISPLAY_ALL = "viewAll";

    /**
     * The Constant DISPLAY_EVENTS.
     */
    private static final String DISPLAY_EVENTS = "viewevents";

    /**
     * The Constant DISPLAY_SPEAKERS.
     */
    private static final String DISPLAY_SPEAKERS = "viewspeakers";

    /**
     * The Constant DISPLAY_FEATURED.
     */
    private static final String DISPLAY_FEATURED = "viewfeatured";

    /**
     * The Constant EVENTS_PATH.
     */
    private static final String EVENTS_PATH = "eventspath";

    /**
     * The Constant SPEAKERS_PATH.
     */
    private static final String SPEAKERS_PATH = "speakerspath";

    private int limitItems;
    private String loadmoreLabel;
    private String eventsPath;

    private String speakersPath;

    private String calixEvent;
    private String calixSpeaker;

    private String[] values;

    public String getCalixEvent() {
        return calixEvent;
    }


    public String getCalixSpeaker() {
        return calixSpeaker;
    }


    public String getEventsPath() {
        return eventsPath;
    }


    public String getSpeakersPath() {
        return speakersPath;
    }


    /**
     * The tabs.
     */
    private List<Tab> tabs;

    /**
     * Gets the tabs.
     *
     * @return the tabs
     */
    public List<Tab> getTabs() {
        return tabs;
    }


    public int getLimitItems() {
        return limitItems;
    }


    public void setLimitItems(int limitItems) {
        this.limitItems = limitItems;
    }

    public String getLoadmoreLabel() {
        return loadmoreLabel;
    }


    /* (non-Javadoc)
     * @see com.adobe.cq.sightly.WCMUse#activate( )
     */
    @Override
    public void activate() throws Exception {

        SlingHttpServletRequest slingRequest = this.getRequest();
        Node currentNode = slingRequest.getResource().adaptTo(Node.class);

        // get all configure values from dialog
        String limitItemsValue = getProperties().get(TOTAL_ITEMS, StringUtils.EMPTY);
        limitItems = StringUtils.isNotBlank(limitItemsValue) ? Integer.valueOf(limitItemsValue) : 20;
        loadmoreLabel = getProperties().get("loadmoreLabel", Constants.LOAD_MORE_LABEL);
        eventsPath = getProperties().get(EVENTS_PATH, StringUtils.EMPTY);
        speakersPath = getProperties().get(SPEAKERS_PATH, StringUtils.EMPTY);
        calixEvent = getProperties().get(EVENTS_TABNAME, Constants.CALIX_EVENT);
        calixSpeaker = getProperties().get(SPEAKERS_TABNAME, Constants.CALIX_SPEAKER);
        values = new String[1];
        values[0] = Constants.EVENT_SPEAKER_DETAIL_RESOURCE_TYPE;
        // get tab contents for events and speakers
        tabs = getEventAndSpeakerTabs(currentNode, slingRequest, limitItems);
    }


    /**
     * Gets the all content.
     *
     * @param paths        the paths
     * @param limitItems   the limit items
     * @param slingRequest the sling request
     * @return the all content
     */
    private List<Article> getAllContent(String[] paths, int limitItems, SlingHttpServletRequest slingRequest) {
        List<Article> articles = new ArrayList<Article>();
        if (paths != null && paths.length > 0) {
            Map<String, String> params = buildMultiParam(limitItems, "", "", paths);
            QueryBuilderUtils.buildOrderParam(params, "desc", "@date");
            QueryBuilderUtils.buildMultiplePropertyValues(params, Constants.SLING_RESOURCE_TYPE, values);
            QueryBuilderUtils.buildPropertyOperation(params, "1", Constants.TITLE, Constants.EXISTS_OPERATION);
            SearchResult result = QueryBuilderUtils.executequery(slingRequest, params);
            articles = convertSearchResultToArticles(result, "", slingRequest);
        }
        return articles;
    }

    /**
     * Gets the events content.
     *
     * @param eventsPath   the events path
     * @param limitItems   the limit items
     * @param slingRequest the sling request
     * @return the events content
     */
    private List<Article> getEventsContent(String eventsPath, int limitItems, SlingHttpServletRequest slingRequest) {
        List<Article> articles = new ArrayList<Article>();
        if (StringUtils.isNotBlank(eventsPath)) {
            Map<String, String> params = QueryBuilderUtils.buildParam(Constants.NT_BASE, limitItems, "", "", eventsPath);
            QueryBuilderUtils.buildOrderParam(params, "desc", "@date");
            QueryBuilderUtils.buildMultiplePropertyValues(params, Constants.SLING_RESOURCE_TYPE, values);
            QueryBuilderUtils.buildPropertyOperation(params, "1", Constants.TITLE, Constants.EXISTS_OPERATION);
            SearchResult result = QueryBuilderUtils.executequery(slingRequest, params);
            String eventTabName = getProperties().get(EVENTS_TABNAME, Constants.CALIX_EVENT);
            articles = convertSearchResultToArticles(result, eventTabName, slingRequest);
        }
        return articles;
    }


    /**
     * Gets the speakers content.
     *
     * @param speakersPath the speakers path
     * @param limitItems   the limit items
     * @param slingRequest the sling request
     * @return the speakers content
     */
    private List<Article> getSpeakersContent(String speakersPath, int limitItems, SlingHttpServletRequest slingRequest) {
        List<Article> articles = new ArrayList<Article>();
        if (StringUtils.isNotBlank(speakersPath)) {
            Map<String, String> params = QueryBuilderUtils.buildParam(Constants.NT_BASE, limitItems, "", "", speakersPath);
            QueryBuilderUtils.buildOrderParam(params, "desc", "@date");
            QueryBuilderUtils.buildMultiplePropertyValues(params, Constants.SLING_RESOURCE_TYPE, values);
            QueryBuilderUtils.buildPropertyOperation(params, "1", Constants.TITLE, Constants.EXISTS_OPERATION);
            SearchResult result = QueryBuilderUtils.executequery(slingRequest, params);
            String eventTabName = getProperties().get(SPEAKERS_TABNAME, Constants.CALIX_SPEAKER);
            articles = convertSearchResultToArticles(result, eventTabName, slingRequest);
        }
        return articles;
    }


    private List<Article> getFeaturedContent(String[] featuredPaths, int limitItems, SlingHttpServletRequest slingRequest) {
        List<Article> articles = new ArrayList<Article>();
        final Session session = CommonUtils.getSession(slingRequest);
        if (ArrayUtils.isNotEmpty(featuredPaths)) {
            String featuredPath = StringUtils.EMPTY;
            String headline = StringUtils.EMPTY;
            String subhead = StringUtils.EMPTY;
            String createDate = StringUtils.EMPTY;
            String modifiedDate = StringUtils.EMPTY;
            String imagePath = StringUtils.EMPTY;
            String contentType = StringUtils.EMPTY;
            String speakerName = StringUtils.EMPTY;
            String headshot = StringUtils.EMPTY;
            String eventLocation = StringUtils.EMPTY;
            Article article = null;
            try {
                for (int i = 0; i < featuredPaths.length; i++) {
                    article = new Article();
                    featuredPath = featuredPaths[i];
                    Node pageNode = session.getNode(featuredPath);
                    Page page = QueryBuilderUtils.getPageByNode(slingRequest, pageNode);

                    if (!JcrUtils.hasNodeByPagePath(slingRequest, featuredPath, Constants.SLING_RESOURCE_TYPE, Constants.EVENT_SPEAKER_DETAIL_RESOURCE_TYPE)) {
                        continue;
                    }
                    Node articleNode = JcrUtils.getNodeByPagePath(slingRequest, featuredPath, Constants.SLING_RESOURCE_TYPE, Constants.EVENT_SPEAKER_DETAIL_RESOURCE_TYPE);
                    headline = JcrUtils.getStringValue(articleNode, "title");
                    if (StringUtils.isBlank(headline)) {
                        continue;
                    }
                    subhead = JcrUtils.getStringValue(articleNode, "description");
                    createDate = JcrUtils.getStringValue(articleNode, "date");
                    if (StringUtils.isNotBlank(createDate)) {
                        Date date = CommonUtils.convertStringToDateNoTimeZone(Constants.DATE_FORMAT_YYYY_MM_DD, createDate.substring(0, 10));
                        modifiedDate = CommonUtils.formatDate(Constants.DATE_FORMAT_MMM_DD_YYYY, date);
                    }
                    imagePath = JcrUtils.getStringValue(articleNode, "mainImage");
                    speakerName = JcrUtils.getStringValue(articleNode, "name");
                    headshot = JcrUtils.getStringValue(articleNode, "headImage");
                    eventLocation = JcrUtils.getStringValue(articleNode, "eventLocation");

                    article.setDate(modifiedDate);
                    article.setHeadline(headline);
                    article.setSubhead(subhead);
                    article.setImage(imagePath);
                    article.setSpeakerName(speakerName);
                    article.setHeadshot(headshot);
                    article.setEventLocation(eventLocation);
                    if (StringUtils.isNotBlank(headshot)) {
                        article.setCssClass(Constants.SPEAKER_CLASS);
                    } else if (StringUtils.isNotBlank(imagePath)) {
                        article.setCssClass(Constants.VIDEO_CLASS);
                    } else {
                        article.setCssClass(Constants.EVENT_CLASS);
                    }
                    contentType = getContentType(pageNode);
                    if (StringUtils.isNotBlank(contentType)) {
                        if (Constants.CALIX_EVENT.equalsIgnoreCase(contentType)) {
                            article.setTabId(HubTab.EVENTS.getId());
                        } else if (Constants.CALIX_SPEAKER.equalsIgnoreCase(contentType)) {
                            article.setTabId(HubTab.SPEAKERS.getId());
                        }
                    }
                    article.setType(contentType);
                    article.setLinkAction(page.getPath().concat(Constants.DOT_HTML));
                    articles.add(article);
                }

            } catch (Exception ex) {
                LOGGER.error("Errors When get Featured content {}", ex);
            }
        }

        return articles;
    }


    private String getContentType(Node currentNode) throws AccessDeniedException, ItemNotFoundException, RepositoryException {
        String contentType = StringUtils.EMPTY;
        if (currentNode != null) {
            String eventsPath = getProperties().get(EVENTS_PATH, StringUtils.EMPTY);
            String speakersPath = getProperties().get(SPEAKERS_PATH, StringUtils.EMPTY);
            String currentPath = currentNode.getParent().getPath();
            if (currentPath.startsWith(eventsPath)) {
                contentType = Constants.CALIX_EVENT;
                //article.setTabId(1);
            } else if (currentPath.startsWith(speakersPath)) {
                contentType = Constants.CALIX_SPEAKER;
                //article.setTabId(2);
            }

        }
        return contentType;
    }


    /**
     * Builds the param.
     *
     * @param totalItem    the total item
     * @param sortType     the sort type
     * @param orderByField the order by field
     * @param rootPaths    the root paths
     * @return the map
     */
    public static Map<String, String> buildMultiParam(int totalItem, String sortType, String orderByField, String... rootPaths) {
        final Map<String, String> params = new HashMap<String, String>();
        if (rootPaths.length == 0) {
            params.put("path", StringUtils.EMPTY);
        } else {
            params.put("group.p.or", "true");
            for (int i = 0; i < rootPaths.length; i++) {
                params.put("group." + (i + 1) + "_path", rootPaths[i]);
            }
        }

        params.put("type", Constants.NT_BASE);
        params.put("p.offset", "0");
        params.put("p.limit", String.valueOf(totalItem));
        if (StringUtils.isNotBlank(orderByField)) {
            String orderBy = "@jcr:content/cq:" + orderByField;
            params.put("orderby", orderBy);
        }
        if (StringUtils.isNotBlank(sortType)) {
            params.put("orderby.sort", sortType);
        }
        return params;
    }


    /**
     * Gets the event and speaker tabs.
     *
     * @param currentNode  the current node
     * @param slingRequest the sling request
     * @param totalItem    the total item
     * @return the event and speaker tabs
     */
    private List<Tab> getEventAndSpeakerTabs(Node currentNode, SlingHttpServletRequest slingRequest, int totalItem) {

        List<Tab> tabs = new ArrayList<Tab>();

        if (currentNode != null) {
            boolean showAlltab = JcrUtils.getBooleanValue(currentNode, DISPLAY_ALL);
            boolean showEventTab = JcrUtils.getBooleanValue(currentNode, DISPLAY_EVENTS);
            boolean showSpeakerTab = JcrUtils.getBooleanValue(currentNode, DISPLAY_SPEAKERS);
            boolean showFeaturedTab = JcrUtils.getBooleanValue(currentNode, DISPLAY_FEATURED);

            if (showAlltab) {
                Tab allTab = new Tab();
                allTab.setTabId(HubTab.ALL.getId());
                allTab.setTabName(HubTab.ALL.getName());
                allTab.setActivated(Boolean.TRUE);

                // get content for tab all
                String eventsPath = getProperties().get(EVENTS_PATH, StringUtils.EMPTY);
                String speakersPath = getProperties().get(SPEAKERS_PATH, StringUtils.EMPTY);
                String[] paths = {eventsPath, speakersPath};
                List<Article> articles = getAllContent(paths, totalItem, slingRequest);
                allTab.setContents(articles);
                tabs.add(allTab);
            }

            if (showEventTab) {
                Tab eventTab = new Tab();
                String eventTabName = getProperties().get(EVENTS_TABNAME, Constants.CALIX_EVENT);
                eventTab.setTabName(eventTabName);
                boolean isActivated = (showAlltab == Boolean.FALSE) ? Boolean.TRUE : Boolean.FALSE;
                eventTab.setActivated(isActivated);
                eventTab.setTabId(HubTab.EVENTS.getId());
                // get content for events tab
                String eventsPath = getProperties().get(EVENTS_PATH, StringUtils.EMPTY);
                List<Article> articles = getEventsContent(eventsPath, totalItem, slingRequest);
                eventTab.setContents(articles);
                tabs.add(eventTab);

            }

            if (showSpeakerTab) {
                Tab speakerTab = new Tab();
                String speakerTabName = getProperties().get(SPEAKERS_TABNAME, Constants.CALIX_SPEAKER);
                speakerTab.setTabName(speakerTabName);
                boolean isActivated = (showAlltab == Boolean.FALSE) ? Boolean.TRUE : Boolean.FALSE;
                isActivated = (showEventTab == Boolean.FALSE) ? Boolean.TRUE : Boolean.FALSE;
                speakerTab.setActivated(isActivated);
                speakerTab.setTabId(HubTab.SPEAKERS.getId());
                // get content for speakers tab
                String speakersPath = getProperties().get(SPEAKERS_PATH, StringUtils.EMPTY);
                List<Article> articles = getSpeakersContent(speakersPath, totalItem, slingRequest);
                speakerTab.setContents(articles);
                tabs.add(speakerTab);
            }

            if (showFeaturedTab) {
                Tab featuredTab = new Tab();
                String featuredTagName = getProperties().get(FEATURED_TABNAME, Constants.FEATURED_LABEL);
                featuredTab.setTabName(featuredTagName);
                boolean isActivated = (showAlltab == Boolean.FALSE) ? Boolean.TRUE : Boolean.FALSE;
                isActivated = (showEventTab == Boolean.FALSE) ? Boolean.TRUE : Boolean.FALSE;
                isActivated = (showSpeakerTab == Boolean.FALSE) ? Boolean.TRUE : Boolean.FALSE;
                featuredTab.setActivated(isActivated);
                featuredTab.setTabId(HubTab.FEATURED.getId());
                //
                String[] featuredPaths = getProperties().get("featuredPaths", String[].class);
                List<Article> articles = getFeaturedContent(featuredPaths, totalItem, slingRequest);
                featuredTab.setContents(articles);
                tabs.add(featuredTab);
            }
        }

        return tabs;
    }


    /**
     * Convert search result to Articles.
     *
     * @param result       the result
     * @param type         the type
     * @param slingRequest the sling request
     * @return the list
     */
    private List<Article> convertSearchResultToArticles(SearchResult result, String type, SlingHttpServletRequest slingRequest) {
        List<Article> articles = new ArrayList<Article>();
        try {
            if (result != null) {
                String headline = StringUtils.EMPTY;
                String subhead = StringUtils.EMPTY;
                String createDate = StringUtils.EMPTY;
                String imagePath = StringUtils.EMPTY;
                String speakerName = StringUtils.EMPTY;
                String headshot = StringUtils.EMPTY;
                String eventLocation = StringUtils.EMPTY;
                Article article = null;
                Iterator<Node> pageResults = result.getNodes();
                String modifiedDate = "";
                while (pageResults.hasNext()) {
                    article = new Article();

                    Node articleNode = pageResults.next();
                    Page page = QueryBuilderUtils.getPageByNode(slingRequest, JcrUtils.findPageByNode(slingRequest, articleNode));
                    Node pageNode = JcrUtils.getNode(slingRequest, page.getPath());
                    Node parent = pageNode.getParent();

                    headline = JcrUtils.getStringValue(articleNode, "title");
                    subhead = JcrUtils.getStringValue(articleNode, "description");

                    createDate = JcrUtils.getStringValue(articleNode, "date");
                    if (StringUtils.isNotBlank(createDate)) {
                        Date date = CommonUtils.convertStringToDateNoTimeZone(Constants.DATE_FORMAT_YYYY_MM_DD, createDate.substring(0, 10));
                        modifiedDate = CommonUtils.formatDate(Constants.DATE_FORMAT_MMM_DD_YYYY, date);
                    }
                    imagePath = JcrUtils.getStringValue(articleNode, "mainImage");
                    speakerName = JcrUtils.getStringValue(articleNode, "name");
                    headshot = JcrUtils.getStringValue(articleNode, "headImage");
                    eventLocation = JcrUtils.getStringValue(articleNode, "eventLocation");

                    article.setDate(modifiedDate);
                    article.setHeadline(headline);
                    article.setSubhead(subhead);
                    article.setImage(imagePath);
                    article.setSpeakerName(speakerName);
                    article.setHeadshot(headshot);
                    article.setEventLocation(eventLocation);
                    if (StringUtils.isNotBlank(headshot)) {
                        article.setCssClass(Constants.SPEAKER_CLASS);
                    } else if (StringUtils.isNotBlank(imagePath)) {
                        article.setCssClass(Constants.VIDEO_CLASS);
                    } else {
                        article.setCssClass(Constants.EVENT_CLASS);
                    }
                    String contentType = StringUtils.isNotBlank(type) ? type : StringUtils.EMPTY;
                    String eventsPath = getProperties().get(EVENTS_PATH, StringUtils.EMPTY);
                    String speakersPath = getProperties().get(SPEAKERS_PATH, StringUtils.EMPTY);
                    String currentPath = parent.getPath();
                    if (StringUtils.isBlank(type)) {
                        if (currentPath.startsWith(eventsPath)) {
                            contentType = calixEvent;
                            article.setTabId(HubTab.EVENTS.getId());
                        } else if (currentPath.startsWith(speakersPath)) {
                            contentType = calixSpeaker;
                            article.setTabId(HubTab.SPEAKERS.getId());
                        }

                    } else {
                        contentType = type;
                        article.setTabId(HubTab.EVENTS.getId());
                        if (StringUtils.startsWith(currentPath, speakersPath)) {
                            article.setTabId(HubTab.SPEAKERS.getId());
                        }
                    }
                    article.setType(contentType);
                    article.setLinkAction(page.getPath().concat(Constants.DOT_HTML));
                    articles.add(article);
                }

            }
        } catch (RepositoryException e) {
            LOGGER.error("Error when convert serch result {}", e);
        }
        return articles;
    }
}
