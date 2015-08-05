package com.calix.components;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.Twitter;

import com.adobe.cq.sightly.WCMUse;
import com.calix.model.CarouselItem;
import com.calix.model.Feed;
import com.calix.utils.CommonUtils;
import com.calix.utils.Constants;
import com.calix.utils.JcrUtils;
import com.calix.utils.QueryBuilderUtils;
import com.calix.utils.TwitterUtils;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;

/**
 * NewsCarousel Class.
 *
 * @author epsilon-Huy
 */
public class NewsCarousel extends WCMUse {
	
	/** The Constant logger. */
	private static final Logger  logger = LoggerFactory.getLogger(NewsCarousel.class);

	/** The carousel items. */
	private List<CarouselItem> carouselItems;
	
	/**
	 * Gets the news carousel items.a
	 *
	 * @return the news carousel items
	 */
	public List<CarouselItem> getNewsCarouselItems(){
		return carouselItems;
	}
	
	/* (non-Javadoc)
	 * @see com.adobe.cq.sightly.WCMUse#activate()
	 */
	@Override
	public void activate() throws Exception {
		String typeofcontent = getProperties().get("typeofcontent", StringUtils.EMPTY);
		String nodePath = StringUtils.EMPTY;
		String type = StringUtils.EMPTY;
		String characterlimit = getProperties().get("characterlimit", "140");
		String totalitem = getProperties().get("totalitem", "1");
		SlingHttpServletRequest  slingRequest =  getRequest();
		carouselItems = new ArrayList<CarouselItem>();
		
		if(typeofcontent.equalsIgnoreCase(Constants.TWITTER_TYPE)){
			// content type is Twitter
			type = getProperties().get("twitterlabel", Constants.TWITTER_TYPE);
			String screenName = getProperties().get("screenname", StringUtils.EMPTY);
			Twitter twitter = TwitterUtils.getTwitterInstance();
			List<Feed> feeds = TwitterUtils.getNewsFeedByScreenName(twitter, screenName, Integer.valueOf(totalitem));
			carouselItems = convertFeedsToCarouselItems(feeds, screenName, type, Integer.valueOf(characterlimit));
			
		}else{
			// other content type (event, news, press release)
			if(typeofcontent.equalsIgnoreCase(Constants.PRESS_RELEASE_TYPE)){
				nodePath = getProperties().get("pressreleasepath", StringUtils.EMPTY);
				type = getProperties().get("pressreleaselabel", Constants.PRESS_RELEASE_LABEL);
			}else if(typeofcontent.equalsIgnoreCase(Constants.EVENT_TYPE)){
				nodePath = getProperties().get("eventpath", StringUtils.EMPTY);
				type = getProperties().get("eventlabel", Constants.EVENT_LABEL);
			}else if(typeofcontent.equalsIgnoreCase(Constants.NEWS_TYPE)){
				nodePath = getProperties().get("newspath", StringUtils.EMPTY);
				type = getProperties().get("newslabel", Constants.NEWS_LABEL);
			}
			
			if(StringUtils.isNotBlank(nodePath)){ // check node path
				int characterlimitInt = Integer.valueOf(characterlimit);
				carouselItems = getCarouselByRootPath(nodePath, type, slingRequest, characterlimitInt);
			}
		}
	}
	/**
	 * Gets the items.
	 *
	 * @param rootPath the events path
	 * @param limitItems the limit items
	 * @param slingRequest the sling request
	 * @return the events content
	 */
	private List<CarouselItem> getCarouselByRootPath(String path, String type, SlingHttpServletRequest slingRequest, int characterLimit){
		List<CarouselItem> carouselItems = new ArrayList<CarouselItem>();
		String[] values = new String[1];

		String resourceType = Constants.EVENT_SPEAKER_DETAIL_RESOURCE_TYPE;
		if(StringUtils.containsIgnoreCase(type, Constants.PRESS_RELEASE_TYPE) || StringUtils.containsIgnoreCase(type, Constants.NEWS_TYPE)){
			resourceType = Constants.PRESS_NEWS_RELEASE_RESOURCE_TYPE;
		}
		values[0] = resourceType;
		if (StringUtils.isNotBlank(path)) {
			Map<String, String> params = QueryBuilderUtils.buildParam(Constants.NT_BASE, 0, "", "", path);
			QueryBuilderUtils.buildOrderParam(params, "desc", "@date");
			QueryBuilderUtils.buildMultiplePropertyValues(params, Constants.SLING_RESOURCE_TYPE, values);
			SearchResult result = QueryBuilderUtils.executequery(slingRequest, params);
			carouselItems = convertSearchResultToCarousels(result, type , slingRequest, characterLimit);
		}
		return carouselItems;
	}
	/**
	 * Convert search result to Carousel Item.
	 *
	 * @param result the result
	 * @param type the type
	 * @param slingRequest the sling request
	 * @return the list
	 */
	private List<CarouselItem> convertSearchResultToCarousels(SearchResult result, String type,
														   SlingHttpServletRequest slingRequest, int characterLimit){
		List<CarouselItem> carouselItems = new ArrayList<CarouselItem>();
		try {
			if (result != null) {
				final Iterator<Node> pageResults = result.getNodes();
				int index = 1;
				while (pageResults.hasNext()) {
					final Node node = pageResults.next();
					CarouselItem item = convertNodeToCarouselItem(node, type);
					index++;
					if (null != item) {
						carouselItems.add(item);
					}
				}
			}
		}catch (Exception ex){
			logger.error("Exception - convertSearchResultToSpotlight: ", ex);
		}
		return carouselItems;
	}

	private CarouselItem convertNodeToCarouselItem(Node node, String type){
		CarouselItem item = new CarouselItem();
		try {
			if (null != node) {
				String title = JcrUtils.getStringValue(node, "title");
				String createDate = JcrUtils.getStringValue(node, "date");
				String modifiedDate = "";
				if (StringUtils.isNotBlank(createDate)) {
					Date date = CommonUtils.convertStringToDateNoTimeZone(Constants.DATE_FORMAT_YYYY_MM_DD, createDate);
					modifiedDate = CommonUtils.formatDate(Constants.DATE_FORMAT_MMM_DD_YYYY, date);
				}
				//update data for spotlight
				item.setDate(modifiedDate);
				item.setDate(modifiedDate);
				item.setDescription(title);
				item.setType(type);
				//
				String pagePath = node.getPath() + ".html";
				String linkTarget = getCtaTarget(pagePath);
				item.setLinkAction(pagePath);
				item.setLinkTarget(linkTarget);
			}
		}catch (Exception ex){
			logger.error("Exception - convertNodeToCarouselItem: ", ex);
		}
		return  item;
	}
	/**
	 * Convert feeds to carousel items.
	 *
	 * @param feeds the feeds
	 * @param screenName the screen name
	 * @return the list
	 */
	private List<CarouselItem> convertFeedsToCarouselItems(List<Feed> feeds, String screenName, String type, int characterLimit){
		carouselItems = new ArrayList<CarouselItem>();
		if(feeds != null){
			CarouselItem carouselItem = null;
			String description = StringUtils.EMPTY;
			String date = StringUtils.EMPTY;;
			for (Feed feed : feeds) {
				carouselItem = new CarouselItem();
				date = CommonUtils.formatDate(Constants.DATE_FORMAT_MMM_DD_YYYY,feed.getCreateDate());
				carouselItem.setDate(date);
				description = feed.getFeedContent();
				if(description != null && description.length() > characterLimit){
					description = description.substring(0,characterLimit);
				}
				carouselItem.setDescription(description);
				String actionLink = feed.getFeedLink();
				String linkTarget = getCtaTarget(actionLink);
				carouselItem.setLinkAction(feed.getFeedLink());
				carouselItem.setLinkTarget(linkTarget);
				carouselItem.setType(type);
				carouselItems.add(carouselItem);
			}
		}
		return carouselItems;
	}
	/**
 * Gets the cta target.
 *
 * @param link
 * @return the cta target
 */
private String getCtaTarget(String link){
	String target = "_self";
	if(StringUtils.isNotBlank(link)) {
		if (link.toLowerCase().startsWith("http")) {
				target = "_blank";
		}
	}
	return target;
}
}
