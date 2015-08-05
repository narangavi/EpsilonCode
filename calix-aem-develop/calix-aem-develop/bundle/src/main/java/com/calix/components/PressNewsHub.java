package com.calix.components;

import com.adobe.cq.sightly.WCMUse;
import com.calix.model.Article;
import com.calix.model.Tab;
import com.calix.utils.*;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import java.util.*;

/**
 * PressNewsHub Class.
 *
 * @author epsilon-duong
 */

public class PressNewsHub extends WCMUse {

    /**
     * The Constant logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(PressNewsHub.class);

    private int yearNo = 5;
    private List<Article> news;
    private List<Article> pressReleases;
    private List<Article> articles;
    private Boolean viewAll;
    private Boolean viewPress;
    private Boolean viewNews;
    private Integer limitItems;
    private String loadmoreLabel;
    private String newsTagName;
    private String newsPath;
    private String pressTagName;
    private String pressPath;
    private int month;
    private int year;
    private List<Integer> months;
    private List<Integer> years;

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

    public Integer getLimitItems() {
        return limitItems;
    }

    public String getLoadmoreLabel() {
        return loadmoreLabel;
    }

    public String getNewsTagName() {
        return newsTagName;
    }

    public String getPressTagName() {
        return pressTagName;
    }

    public String getNewsPath() {
        return newsPath;
    }

    public String getPressPath() {
        return pressPath;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public List<Integer> getMonths() {
        return months;
    }

    public List<Integer> getYears() {
        return years;
    }

    /* (non-Javadoc)
     * @see com.adobe.cq.sightly.WCMUse#activate()
     */
    @Override
    public void activate() throws Exception {
        SlingHttpServletRequest slingRequest = this.getRequest();

        Node currentNode = slingRequest.getResource().adaptTo(Node.class);
        viewAll = JcrUtils.getBooleanValue(currentNode, "viewAll");
        viewPress = JcrUtils.getBooleanValue(currentNode, "viewPress");
        viewNews = JcrUtils.getBooleanValue(currentNode, "viewNews");
        String limitItemsStr = getProperties().get("limitItems", "");
        loadmoreLabel = getProperties().get("loadmoreLabel", Constants.LOAD_MORE_LABEL);
        pressTagName = getProperties().get("pressTagName", HubTab.PRESS.getName());
        newsTagName = getProperties().get("newsTagName", HubTab.NEWS.getName());
        pressPath = getProperties().get("pressPath", "");
        newsPath = getProperties().get("newsPath", "");
        limitItems = StringUtils.isNotBlank(limitItemsStr) ? Integer.valueOf(limitItemsStr) : 20;
        String datetime = CommonUtils.formatDate("yyyy-MM", new Date());
        year = Integer.parseInt(datetime.split("-")[0]);
        month = Integer.parseInt(datetime.split("-")[1]);
        months = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        years = new ArrayList<Integer>();
        int i = 0;
        while (0 <= yearNo) {
            years.add(year - yearNo);
            yearNo--;
            i++;
        }
        tabs = new ArrayList<Tab>();
        Tab tab = new Tab();
        Map<String, String> params = null;
        SearchResult result = null;
        boolean isActivated = Boolean.FALSE;

        String[] values = {Constants.PRESS_NEWS_RELEASE_RESOURCE_TYPE};
        // get articles
        articles = new ArrayList<Article>();
        if (StringUtils.isNotBlank(pressPath)
                && StringUtils.isNotBlank(newsPath) && viewAll) {
            String[] paths = {pressPath, newsPath};
            params = buildMultiParam(limitItems, "", "", paths);
            QueryBuilderUtils.buildOrderParam(params, "desc", "@date");
            QueryBuilderUtils.buildMultiplePropertyValues(params, Constants.SLING_RESOURCE_TYPE, values);
            QueryBuilderUtils.buildPropertyOperation(params, "1", Constants.TITLE, Constants.EXISTS_OPERATION);
            result = QueryBuilderUtils.executequery(slingRequest, params);
            articles = convertSearchResultToArticles(result, "", slingRequest);
            isActivated = viewAll;

            tab = new Tab();
            tab.setTabId(HubTab.ALL.getId());
            tab.setTabName(HubTab.ALL.getName());
            tab.setActivated(isActivated);
            tab.setContents(articles);
            tabs.add(tab);
        }
        //get press release
        pressReleases = new ArrayList<Article>();
        if (StringUtils.isNotBlank(pressPath) && viewPress) {
            params = QueryBuilderUtils.buildParam(Constants.NT_BASE, limitItems, "", "", pressPath);
            QueryBuilderUtils.buildOrderParam(params, "desc", "@date");
            QueryBuilderUtils.buildMultiplePropertyValues(params, Constants.SLING_RESOURCE_TYPE, values);
            QueryBuilderUtils.buildPropertyOperation(params, "1", Constants.TITLE, Constants.EXISTS_OPERATION);
            result = QueryBuilderUtils.executequery(slingRequest, params);
            pressReleases = convertSearchResultToArticles(result, pressTagName, slingRequest);
            isActivated = (viewAll == Boolean.FALSE) ? Boolean.TRUE : Boolean.FALSE;

            tab = new Tab();
            tab.setTabId(HubTab.PRESS.getId());
            tab.setTabName(pressTagName);
            tab.setActivated(isActivated);
            tab.setContents(pressReleases);
            tabs.add(tab);
        }
        //get news
        news = new ArrayList<Article>();
        if (StringUtils.isNotBlank(newsPath) && viewNews) {
            params = QueryBuilderUtils.buildParam(Constants.NT_BASE, limitItems, "", "", newsPath);
            QueryBuilderUtils.buildOrderParam(params, "desc", "@date");
            QueryBuilderUtils.buildMultiplePropertyValues(params, Constants.SLING_RESOURCE_TYPE, values);
            QueryBuilderUtils.buildPropertyOperation(params, "1", Constants.TITLE, Constants.EXISTS_OPERATION);
            result = QueryBuilderUtils.executequery(slingRequest, params);
            news = convertSearchResultToArticles(result, newsTagName, slingRequest);
            isActivated = (viewPress == Boolean.FALSE) ? Boolean.TRUE : Boolean.FALSE;

            tab = new Tab();
            tab.setTabId(HubTab.NEWS.getId());
            tab.setTabName(newsTagName);
            tab.setActivated(isActivated);
            tab.setContents(news);
            tabs.add(tab);
        }
    }

    /**
     * Builds the param.
     *
     * @param totalItem    the total item
     * @param sortType     the sort type
     * @param orderByField the order by field
     * @param rootPath     the root path
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
        if (totalItem > 0) {
            params.put("p.limit", String.valueOf(totalItem));
        }
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
                String modifiedDate = StringUtils.EMPTY;
                String image = StringUtils.EMPTY;
                String source = StringUtils.EMPTY;
                String eventLocation = StringUtils.EMPTY;
                Article article = null;
                Iterator<Node> pageResults = result.getNodes();
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
                    image = JcrUtils.getStringValue(articleNode, "mainImage");
                    source = JcrUtils.getStringValue(articleNode, "name");
                    eventLocation = JcrUtils.getStringValue(articleNode, "eventLocation");

                    article.setDate(modifiedDate);
                    article.setHeadline(headline);
                    article.setSubhead(subhead);
                    if (StringUtils.isNotBlank(type)) {
                        article.setType(type);
                        article.setTabId(HubTab.NEWS.getId());
                        if (parent.getPath().startsWith(pressPath)) {
                            article.setTabId(HubTab.PRESS.getId());
                        }
                    } else {
                        article.setType(newsTagName);
                        article.setTabId(HubTab.NEWS.getId());
                        if (parent.getPath().startsWith(pressPath)) {
                            article.setType(pressTagName);
                            article.setTabId(HubTab.PRESS.getId());
                        }
                    }
                    article.setImage(image);
                    if (StringUtils.isNotBlank(image)) {
                        article.setCssClass(Constants.VIDEO_CLASS);
                    } else {
                        article.setCssClass(Constants.EVENT_CLASS);
                    }
                    article.setDate(modifiedDate);
                    article.setSource(source);
                    article.setEventLocation(eventLocation);
                    article.setLinkAction(page.getPath() + Constants.DOT_HTML);

                    articles.add(article);
                }
            }
        } catch (RepositoryException e) {
            logger.error("Error when convert serch result {}", e);
        }
        return articles;
    }
}
