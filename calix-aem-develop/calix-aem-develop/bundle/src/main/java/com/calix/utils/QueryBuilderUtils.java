package com.calix.utils;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

/**
 * The Class QueryBuilderUtils.
 */
public class QueryBuilderUtils {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(QueryBuilderUtils.class);

	/** The Constant START_POSITION. */
	private static final String START_POSITION = "p.offset";
	
	/** The Constant TOTAL_ITEM. */
	private static final String TOTAL_ITEM = "p.limit";
	
	/** The Constant TYPE. */
	private static final String TYPE = "type";
	
	/** The Constant ROOT_PATH. */
	private static final String ROOT_PATH = "path";
	
	/** The Constant ORDER_BY. */
	private static final String ORDER_BY = "orderby";
	
	/** The Constant SORT. */
	private static final String SORT = "orderby.sort";
	
	/** The Constant ORDERBY_PREFIX. */
	private static final String ORDERBY_PREFIX = "@jcr:content/cq:";
	
	private static final String TAG_ID = "tagid";
	private static final String TAG_ID_PROPERTY = "tagid.property";

	/**
	 * Builds the param.
	 *
	 * @param totalItem the total item
	 * @param sortType the sort type
	 * @param orderByField the order by field
	 * @param rootPath the root path
	 * @return the map
	 */
	public static Map<String, String> buildParam(int totalItem, String sortType, String orderByField, String rootPath) {
		final Map<String, String> params = new HashMap<String, String>();
		rootPath = rootPath == null ? StringUtils.EMPTY : rootPath;
		params.put(ROOT_PATH, rootPath);
		params.put(TYPE, "cq:Page");
		params.put(START_POSITION, "0");
		params.put(TOTAL_ITEM, String.valueOf(totalItem));
		if (StringUtils.isNotBlank(orderByField)) {
			String orderBy = ORDERBY_PREFIX + orderByField;
			params.put(ORDER_BY, orderBy);
		}
		if (StringUtils.isNotBlank(sortType)) {
			params.put(SORT, sortType);
		}
		return params;
	}
	
	public static Map<String, String> buildParam(String type, int totalItem, String sortType, String orderByField, String rootPath) {
		final Map<String, String> params = new HashMap<String, String>();
		rootPath = rootPath == null ? StringUtils.EMPTY : rootPath;
		params.put(ROOT_PATH, rootPath);
		if (StringUtils.isNotBlank(type)) {
			params.put(TYPE, type);
		}
		params.put(START_POSITION, "0");
		params.put(TOTAL_ITEM, String.valueOf(totalItem));
		if (StringUtils.isNotBlank(orderByField)) {
			params.put(ORDER_BY, orderByField);
		}
		if (StringUtils.isNotBlank(sortType)) {
			params.put(SORT, sortType);
		}
		return params;
	}
	
	public static Map<String, String> buildParam(String from, String to, String sortType, String orderByField, String rootPath) {
		final Map<String, String> params = new HashMap<String, String>();
		rootPath = rootPath == null ? StringUtils.EMPTY : rootPath;
		params.put(ROOT_PATH, rootPath);
		params.put(START_POSITION, from);
		params.put(TOTAL_ITEM, to);
		if (StringUtils.isNotBlank(orderByField)) {
			String orderBy = ORDERBY_PREFIX + orderByField;
			params.put(ORDER_BY, orderBy);
		}
		if (StringUtils.isNotBlank(sortType)) {
			params.put(SORT, sortType);
		}
		return params;
	}
	
	public static Map<String, String> buildParam(String type, String from, String to, String sortType, String orderByField, String rootPath) {
		final Map<String, String> params = buildParam(from, to, sortType, orderByField, rootPath);
		if (StringUtils.isNotBlank(type)) {
			params.put(TYPE, type);
		}
		return params;
	}
	
	/**
	 * Executequery.
	 *
	 * @param slingRequest the sling request
	 * @param params the params
	 * @return the search result
	 */
	public static SearchResult executequery(SlingHttpServletRequest slingRequest, Map<String, String> params){
		SearchResult result = null;
		if (slingRequest != null) {
			final ResourceResolver resourceResolver = slingRequest.getResourceResolver();
			final QueryBuilder queryBuilder = resourceResolver.adaptTo(QueryBuilder.class);
			final Session session = slingRequest.getResourceResolver().adaptTo(Session.class);
			Query query = queryBuilder.createQuery(PredicateGroup.create(params), session);
			result = query.getResult();
		}
		return result;
	}
	
	/**
	 * Gets the page by node.
	 *
	 * @param slingRequest the sling request
	 * @param currentNode the current node
	 * @return the page by node
	 */
	public static Page getPageByNode(SlingHttpServletRequest slingRequest, Node currentNode){
		 final PageManager pageMagager = CommonUtils.getPageManager(slingRequest);
		 Page page= null;
		 try {
			page = pageMagager.getPage(currentNode.getPath());
		} catch (RepositoryException e) {
			logger.error("Error when get page from node {}", currentNode);
		}
		 return page;
	}
	
	public static Map<String, String> buildMultiParam(String type, String from, String totalItem, String sortType, String orderByField, String... rootPaths) {
		final Map<String, String> params = buildMultiParam(from, totalItem, sortType, orderByField, rootPaths);
		if (StringUtils.isNotBlank(type)) {
			params.put("type", type);
		}
	    return params;
	}
	
	public static Map<String, String> buildMultiParam(String from, String totalItem, String sortType, String orderByField, String... rootPaths) {
		final Map<String, String> params = new HashMap<String, String>();
		if (rootPaths.length == 0) {
			params.put("path", StringUtils.EMPTY);
		} else {
		    params.put("group.p.or", "true");
		    for (int i = 0; i < rootPaths.length; i++) {
		    	params.put("group."+(i+1)+"_path", rootPaths[i]);
		    }
		}
	    
	    params.put("p.offset", from);
	    params.put("p.limit", totalItem);
	    if (StringUtils.isNotBlank(orderByField)) {
	        String orderBy = "@jcr:content/cq:" + orderByField;
	        params.put("orderby", orderBy);
	    } 
	    if (StringUtils.isNotBlank(sortType)) {
	        params.put("orderby.sort", sortType);
	    }
	    return params;
	}

	public static Map<String, String> buildOrderParam(Map params, String sortType, String orderByField) {
        if (StringUtils.isNotBlank(orderByField)) {
			params.put(ORDER_BY, orderByField);
		}
		if (StringUtils.isNotBlank(sortType)) {
			params.put(SORT, sortType);
		}
        return params;
	}

	public static Map<String, String> buildMultiplePropertyValues(Map params, String property, String... values) {
		params.put("property", property);
        for (int i = 0; i < values.length; i++) {
            params.put("property."+(i+1)+"_value", values[i]);
        }
        return params;
	}
	
	public static Map<String, String> buildRootParam(Map<String, String> params, int startPosition, int totalItem, String rootPath) {
		rootPath = rootPath == null ? StringUtils.EMPTY : rootPath;
		params.put(ROOT_PATH, rootPath);
		params.put(START_POSITION, String.valueOf(startPosition));
		params.put(TOTAL_ITEM, String.valueOf(totalItem));
		return params;
    }
	
	public static Map<String, String> buildTagParam(Map<String, String> params, String tagId, String tagProp) {
		params.put(TAG_ID, tagId);
		params.put(TAG_ID_PROPERTY, tagProp);
		return params;
    }
	
	public static Map<String, String> buildPropertyOperation(Map<String, String> params, String index, String property, String operation) {
		params.put(index+"_property", property);
		params.put(index+"_propert.operation", operation);
		return params;
    }
}
