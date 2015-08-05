package com.calix.components;

import com.adobe.cq.sightly.WCMUse;
import com.calix.model.Article;
import com.calix.utils.*;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.*;

/**
 * EventSpeakersDetail Class.
 *
 * @author epsilon-tuanhx
 */

public class PressNewsRelease extends WCMUse {

    /**
     * The Constant logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(PressNewsRelease.class);

    private static final String CONTENT_TYPE_NEWS = "news";
    private static final String ARTICLE_PRESS = "/content/calix/en/site/home/press-release";
    private static final String ARTICLE_NEWS = "/content/calix/en/site/home/news-articles";

    private String maxArticle;
    private String title;
    private String name;
    private String link;
    private String description;
    private String image;

    private String content;
    private String date;
    private String linkLabel;
    private String styleHeadShot;
    private List<Article> articles;
    private Integer limitItems;
    private String eventLocation;

    private String titleCTA;
    private String linkCTA;
    private String sytleCTA;

    /* (non-Javadoc)
     * @see com.adobe.cq.sightly.WCMUse#activate()
     */
    @Override
    public void activate() throws Exception {

        SlingHttpServletRequest slingRequest = this.getRequest();
        Session session = CommonUtils.getSession(slingRequest);
        Node currentNode = slingRequest.getResource().adaptTo(Node.class);

        maxArticle = getProperties().get("maxArticle", "");
        limitItems = org.apache.commons.lang3.StringUtils.isNotBlank(maxArticle) ? Integer.valueOf(maxArticle) : 20;

        title = getProperties().get("title", "");
        name = getProperties().get("name", "");
        link = getProperties().get("link", "");
        description = getProperties().get("description", "");
        image = getProperties().get("mainImage", "");
        content = getProperties().get("content", "");
        // date in diaglog : dd/mm/yyyy
        // date in node : DD/MM/YYYY[T]HH:mmZ
        // date in html: dd/mm/yyyy
        String dateFromCRX = getProperties().get("date", "");
        if (StringUtils.isNotBlank(dateFromCRX)) {
            date = converFormatDate_from_YYYY_MM_DD_to_MMDDYYYY(dateFromCRX.substring(0, 10));
        }
        linkLabel = getProperties().get("linkLabel", "");
        eventLocation = getProperties().get("eventLocation", "");


        // ------------------------------------CTA button --------------------------------------------------
        // -------------------------------------------------------------------------------------------------

        titleCTA = getProperties().get("titleCTA", " Back to Press & News Hub");
        linkCTA = getProperties().get("linkCTA", "");
		if(StringUtils.isNotBlank(linkCTA) && !linkCTA.toLowerCase().startsWith("http")){
				linkCTA = linkCTA.concat(".html");
		}
        String buttonType = JcrUtils.getStringValue(currentNode, "buttonType");

        if (StringUtils.isNotBlank(buttonType)) {
            if (StringUtils.containsIgnoreCase(buttonType, "green")) {
                sytleCTA = "grnBtn";
            } else if (StringUtils.containsIgnoreCase(buttonType, "white")) {
                sytleCTA = "grnBtn whiteBtn";
            } else if (StringUtils.containsIgnoreCase(buttonType, "outline")) {
                sytleCTA = "grnBtn outlineBtn";
            } else if (StringUtils.containsIgnoreCase(buttonType, "text")) {
                sytleCTA = "link";
            }
        }

        sytleCTA = sytleCTA + " backPressNews";
        // ----------------------------------CTA button ----------------------------------------------------
        // -------------------------------------------------------------------------------------------------

        String contentType = JcrUtils.getStringValue(currentNode, "contentType");
        if (org.apache.commons.lang3.StringUtils.isNotBlank(contentType)) {
            if (org.apache.commons.lang3.StringUtils.containsIgnoreCase(contentType, CONTENT_TYPE_NEWS)) {
                // if News page need Content
                name = " " + name;
            } else { // if Press page no name
                name = null;
            }
        }

        // ----------------------------------get Recent Articles -------------------------------------------
        // -------------------------------------------------------------------------------------------------

        Map<String, String> params = null;
        SearchResult result = null;
        //String[] values = {Constants.FULLWIDTH_TEMPLATE_RESOURCE_TYPE, Constants.SPLITCOLUMN_TEMPLATE_RESOURCE_TYPE};
        String[] values = {Constants.PRESS_NEWS_RELEASE_RESOURCE_TYPE};
        articles = new ArrayList<Article>();

        String[] paths = {ARTICLE_NEWS, ARTICLE_PRESS};
        params = buildMultiParam(limitItems, "", "", paths);
        QueryBuilderUtils.buildOrderParam(params, "desc", "@date");
        QueryBuilderUtils.buildMultiplePropertyValues(params, Constants.SLING_RESOURCE_TYPE, values);
        QueryBuilderUtils.buildPropertyOperation(params, "1", Constants.TITLE, Constants.EXISTS_OPERATION);
        result = QueryBuilderUtils.executequery(slingRequest, params);
        articles = convertSearchResultToArticles(result, "", slingRequest);

        // ----------------------------------get Recent Articles -------------------------------------------
        // -------------------------------------------------------------------------------------------------

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
            params.put("path", org.apache.commons.lang3.StringUtils.EMPTY);
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
        if (org.apache.commons.lang3.StringUtils.isNotBlank(orderByField)) {
            String orderBy = "@jcr:content/cq:" + orderByField;
            params.put("orderby", orderBy);
        }
        if (org.apache.commons.lang3.StringUtils.isNotBlank(sortType)) {
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
                        modifiedDate = CommonUtils.formatDate(Constants.DATE_FORMAT_MMMM_DD_YYYY, date);
                    }
                    image = JcrUtils.getStringValue(articleNode, "mainImage");
                    source = JcrUtils.getStringValue(articleNode, "name");
                    eventLocation = JcrUtils.getStringValue(articleNode, "eventLocation");

                    article.setDate(modifiedDate);
                    article.setHeadline(headline);
                    article.setSubhead(subhead);
                    if (parent.getPath().startsWith(ARTICLE_NEWS)) {
                        article.setTabName(HubTab.NEWS.getName());

                    } else if (parent.getPath().startsWith(ARTICLE_PRESS)) {
                        article.setTabName(HubTab.PRESS.getName());
                    }
                    article.setImage(image);
                    article.setDate(modifiedDate);
                    article.setSource(source);
                    article.setEventLocation(eventLocation == null || eventLocation.equals("") ? "San Francisco, CA" : eventLocation);
                    // Arun said that need to insert eventLocation , need to update, temporary: San Francisco, CA
                    article.setLinkAction(page.getPath() + Constants.DOT_HTML);

                    articles.add(article);
                }

            }
        } catch (RepositoryException e) {
            logger.error("Error when convert serch result {}", e);
        }
        return articles;
    }

    // convert String YYYY-MM-DD  to MM/DD/YYYY
    public String converFormatDate_from_YYYY_MM_DD_to_MMDDYYYY(String dateNode) {

        // String array : 0: YYYY; 1: MM; 2: DD
        String[] part = dateNode.split("-");

        String dateHtml = part[1] + "/" + part[2] + "/" + part[0];

        return dateHtml;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public String getMaxArticle() {
        return maxArticle;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public Integer getLimitItems() {
        return limitItems;
    }

    public String getStyleHeadShot() {
        return styleHeadShot;
    }

    public String getLinkLabel() {
        return linkLabel;
    }

    public String getTitleCTA() {
        return titleCTA;
    }

    public String getLinkCTA() {
        return linkCTA;
    }

    public String getSytleCTA() {
        return sytleCTA;
    }
}
