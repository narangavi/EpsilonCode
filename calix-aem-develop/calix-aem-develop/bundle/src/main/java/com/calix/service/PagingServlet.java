package com.calix.service;


import java.io.IOException;
import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.commons.lang.StringUtils;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.calix.model.Article;
import com.calix.utils.CommonUtils;
import com.calix.utils.Constants;
import com.calix.utils.HubTab;
import com.calix.utils.JcrUtils;
import com.calix.utils.QueryBuilderUtils;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;


/**
 * The Class PagingServlet.
 */
@SlingServlet(paths = "/bin/calix/servlets/loadmore", methods = {"POST"}, metatype = true, extensions = { "json" })
public class PagingServlet extends SlingAllMethodsServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3068451120187999746L;

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(PagingServlet.class);
	
	private String path1;
	private String path2;
	private String from;
	private String to;
	private String type1;
	private String type2;
	private boolean isPressAndNews;
    private String month;
    private String year;
    private String[] values;

    /* (non-Javadoc)
     * @see org.apache.sling.api.servlets.SlingAllMethodsServlet#doPost(org.apache.sling.api.SlingHttpServletRequest, org.apache.sling.api.SlingHttpServletResponse)
     */
    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServerException, IOException {
        try {
            response.setContentType("application/json; charset=UTF-8");
            buildQuery(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Builds the query.
     *
     * @param request the request
     * @param response the response
     * @throws ServerException the server exception
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws JSONException the JSON exception
     */
    protected void buildQuery(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServerException, IOException, JSONException {
    	JSONArray jsonArray = new JSONArray(); 
    	JSONObject jsonObject = new JSONObject();
    	path1 = request.getParameter("path1");
    	path2 = request.getParameter("path2");
    	from = request.getParameter("from");
		to = request.getParameter("to");
		type1 = request.getParameter("type1");
		type2 = request.getParameter("type2");
		String isPressAndNewsStr = request.getParameter("isPressAndNews");
		isPressAndNews = StringUtils.equalsIgnoreCase(isPressAndNewsStr, "false") ? false : true;

        month = request.getParameter("month");
        year = request.getParameter("year");

		List<Article> articles = new ArrayList<Article>();
		values = new String[2];
        values[0] = Constants.PRESS_NEWS_RELEASE_RESOURCE_TYPE;
        values[1] = Constants.EVENT_SPEAKER_DETAIL_RESOURCE_TYPE;

		if(StringUtils.isNotBlank(path1) && StringUtils.isNotBlank(path2)){
    		articles = getAllContent(new String[]{path1, path2},from, to, request);
	        jsonArray = convertArticleToJsonArray(articles);
	        jsonObject.put("articles", jsonArray);
    	} else if(StringUtils.isNotBlank(path1)){
            Map<String, String> params = QueryBuilderUtils.buildParam(Constants.NT_BASE, from, to, "", "", path1);
            QueryBuilderUtils.buildOrderParam(params, "desc", "@date");
            QueryBuilderUtils.buildMultiplePropertyValues(params, Constants.SLING_RESOURCE_TYPE, values);
            QueryBuilderUtils.buildPropertyOperation(params, "1", Constants.TITLE, Constants.EXISTS_OPERATION);
    		SearchResult result = QueryBuilderUtils.executequery(request, params);
	        articles = convertSearchResultToArticles(result, type1, isPressAndNews, request);
	        jsonArray = convertArticleToJsonArray(articles);
	        jsonObject.put("articles", jsonArray);
    	} else if(StringUtils.isNotBlank(path2)){
    		Map<String, String> params = QueryBuilderUtils.buildParam(Constants.NT_BASE, from, to, "", "", path2);
            QueryBuilderUtils.buildOrderParam(params, "desc", "@date");
            QueryBuilderUtils.buildMultiplePropertyValues(params, Constants.SLING_RESOURCE_TYPE, values);
            QueryBuilderUtils.buildPropertyOperation(params, "1", Constants.TITLE, Constants.EXISTS_OPERATION);
    		SearchResult result = QueryBuilderUtils.executequery(request, params);
	        articles = convertSearchResultToArticles(result, type2, isPressAndNews, request);
	        jsonArray = convertArticleToJsonArray(articles);
	        jsonObject.put("articles", jsonArray);
    	}  
    	
    	response.getWriter().write(jsonObject.toString());
    }

    /**
     * Convert search result to articles.
     *
     * @param result the result
     * @param type the type
     * @param slingRequest the sling request
     * @return the list
     */
    private List<Article> convertSearchResultToArticles(SearchResult result, String type, boolean isPressAndNews, SlingHttpServletRequest slingRequest) {
        List<Article> articles = new ArrayList<Article>();
        try {
            if (result != null) {
                String headline = StringUtils.EMPTY;
                String subhead = StringUtils.EMPTY;
                String createDate = StringUtils.EMPTY;
                String image = StringUtils.EMPTY;
                String source = StringUtils.EMPTY;
                String speakerName = StringUtils.EMPTY;
                String headshot = StringUtils.EMPTY;
                String eventLocation = StringUtils.EMPTY;
                Article article = null;
                Iterator<Node> pageResults = result.getNodes();
                String modifiedDate = "";
                while(pageResults.hasNext()){
                    article = new Article();
                    
                    Node articleNode = pageResults.next();
                    Page page = QueryBuilderUtils.getPageByNode(slingRequest, JcrUtils.findPageByNode(slingRequest, articleNode));
                    Node pageNode = JcrUtils.getNode(slingRequest, page.getPath());
                    Node parent = pageNode.getParent();
                    
                    boolean isPN = JcrUtils.hasNodeByPagePath(slingRequest, pageNode.getPath(), Constants.SLING_RESOURCE_TYPE, Constants.PRESS_NEWS_RELEASE_RESOURCE_TYPE);
                    boolean isES = JcrUtils.hasNodeByPagePath(slingRequest, pageNode.getPath(), Constants.SLING_RESOURCE_TYPE, Constants.EVENT_SPEAKER_DETAIL_RESOURCE_TYPE);

                    if (isPN) {
                    	articleNode = JcrUtils.getNodeByPagePath(slingRequest, pageNode.getPath(), Constants.SLING_RESOURCE_TYPE, Constants.PRESS_NEWS_RELEASE_RESOURCE_TYPE);
	                    source = JcrUtils.getStringValue(articleNode, "name");
                    } else if (isES) {
                    	articleNode = JcrUtils.getNodeByPagePath(slingRequest, pageNode.getPath(), Constants.SLING_RESOURCE_TYPE, Constants.EVENT_SPEAKER_DETAIL_RESOURCE_TYPE);
                        headshot = JcrUtils.getStringValue(articleNode, "headImage");
                    	speakerName = JcrUtils.getStringValue(articleNode, "name");
                    }
                    
                    createDate = JcrUtils.getStringValue(articleNode, "date");
                    if (StringUtils.isNotBlank(createDate)) {
                        Date date = CommonUtils.convertStringToDateNoTimeZone(Constants.DATE_FORMAT_YYYY_MM_DD, createDate.substring(0,10));
                        modifiedDate = CommonUtils.formatDate(Constants.DATE_FORMAT_MMM_DD_YYYY, date);
                        if (StringUtils.isNotBlank(month) && !StringUtils.equalsIgnoreCase(month, "0") && 
                                StringUtils.isNotBlank(year) && !StringUtils.equalsIgnoreCase(year, "0")) {
                            Date dateFilter = CommonUtils.convertStringToDateNoTimeZone(Constants.DATE_FORMAT_YYYY_MM_DD, year + "-" + month + "-01");
                            if (dateFilter.after(date)) {
                                break;
                            }
                        }
                    }
                    
                    headline = JcrUtils.getStringValue(articleNode, "title");
                    subhead = JcrUtils.getStringValue(articleNode, "description");
                    image = JcrUtils.getStringValue(articleNode, "mainImage");
                    eventLocation = JcrUtils.getStringValue(articleNode, "eventLocation");

                    article.setDate(modifiedDate);
                    article.setHeadline(headline);
                    article.setSubhead(subhead);
                    article.setImage(image);
                    article.setSource(source);
                    article.setSpeakerName(speakerName);
                    article.setHeadshot(headshot);
                    article.setEventLocation(eventLocation);
                    
                    String currentPath = parent.getPath();
                    String contentType = StringUtils.isNotBlank(type) ?  type : StringUtils.EMPTY;
                    if (isPressAndNews) {
                    	if(StringUtils.isNotBlank(path1) && currentPath.startsWith(path1)){
                    		contentType = type1;
                    		article.setTabId(HubTab.PRESS.getId());
                    	} else if(StringUtils.isNotBlank(path2) && currentPath.startsWith(path2)){
                    		contentType = type2;
                    		article.setTabId(HubTab.NEWS.getId());
                    	}
                    } else {
                    	if(StringUtils.isNotBlank(path1) && currentPath.startsWith(path1)){
                    		contentType = type1;
                    		article.setTabId(HubTab.EVENTS.getId());
                    	} else if(StringUtils.isNotBlank(path2) && currentPath.startsWith(path2)){
                    		contentType = type2;
                    		article.setTabId(HubTab.SPEAKERS.getId());
                    	}
                    }
                    if (StringUtils.isNotBlank(headshot)) {
                        article.setCssClass(Constants.SPEAKER_CLASS);
                    } else if (StringUtils.isNotBlank(image)) {
                        article.setCssClass(Constants.VIDEO_CLASS);
                    } else {
                        article.setCssClass(Constants.EVENT_CLASS);
                    }
                    article.setType(contentType);
                    article.setLinkAction(page.getPath().concat(Constants.DOT_HTML));
                    articles.add(article);
                }

            }
        } catch (RepositoryException e) {
        	e.printStackTrace();
        }
        return articles;
    }
    
	/**
	 * Gets the all content.
	 *
	 * @param paths the paths
	 * @param start the start
	 * @param total the total
	 * @param slingRequest the sling request
	 * @return the all content
	 */
	private List<Article> getAllContent(String[] paths, String start, String total, SlingHttpServletRequest slingRequest){
		List<Article> articles = new ArrayList<Article>();
		if (paths != null && paths.length > 0) {
			Map<String, String> params = QueryBuilderUtils.buildMultiParam(Constants.NT_BASE, start, total, "", "", paths);
            QueryBuilderUtils.buildOrderParam(params, "desc", "@date");
            QueryBuilderUtils.buildMultiplePropertyValues(params, Constants.SLING_RESOURCE_TYPE, values);
            QueryBuilderUtils.buildPropertyOperation(params, "1", Constants.TITLE, Constants.EXISTS_OPERATION);
			SearchResult result = QueryBuilderUtils.executequery(slingRequest, params);
			articles = convertSearchResultToArticles(result, "", isPressAndNews, slingRequest);
		}
		return articles;
	}
    
    /**
     * Convert article to json array.
     *
     * @param articles the articles
     * @return the JSON array
     * @throws JSONException the JSON exception
     */
    private JSONArray convertArticleToJsonArray(List<Article> articles) throws JSONException{
    	JSONArray jsonArray = new JSONArray();
    	
    	if(articles != null && !articles.isEmpty()){
    		JSONObject jsonObject;
    		for (Article article : articles) {
    			jsonObject = new JSONObject();
    			jsonObject.put("TabId", article.getTabId());
    			jsonObject.put("date", article.getDate());
    			jsonObject.put("headLine", article.getHeadline());
    			jsonObject.put("image", article.getImage());
    			jsonObject.put("leadIn", article.getLeadin());
    			jsonObject.put("link", article.getLinkAction());
    			jsonObject.put("source", article.getSource());
                jsonObject.put("speakerName", article.getSpeakerName());
                jsonObject.put("headshot", article.getHeadshot());
    			jsonObject.put("subHead", article.getSubhead());
    			jsonObject.put("type", article.getType());
    			jsonObject.put("eventLocation", article.getEventLocation());
    			jsonArray.put(jsonObject);
			}
    		
    	}
    	
    	
    	return jsonArray;
    			
    }
    
}