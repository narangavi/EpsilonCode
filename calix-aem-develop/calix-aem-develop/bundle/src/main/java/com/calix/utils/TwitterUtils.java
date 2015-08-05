package com.calix.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import com.calix.model.Feed;

/**
 * The Class TwitterUtils.
 * 
 * @author HuyDQ
 * 
 */
public class TwitterUtils {

	/** The twitter. */
	private static Twitter twitter = null;

	/** The Constant logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(TwitterUtils.class);
	
	/** The Constant consumerKey. */
	private static final String CONSUMER_KEY = "y1se8XF3UsQqocxGfhvHFYn0m";
	
	/** The Constant consumerKeySecret. */
	private static final String CONSUMER_KEYSECRET = "eT4wHC4QSUhFNspPbD9dZX9St4JkxdmUQF9fMaNMMbI6uXpGbj";
	
	/** The Constant accessToken. */
	private static final String ACCESSTOKEN = "614211579-tLNvlNzj091771NOomc8dc785VkVH3zdfSYG7vq8";
	
	/** The Constant accessTokenSecret. */
	private static final String ACCESSTOKEN_SECRET = "884gDqTxf05NVxvGk2HptujpyUXKtU739Co3IG4aycdUt";
	
	/** The Constant FLASH. */
	private static final String FLASH = "/";
	

	/**
	 * Gets the twitter instance.
	 *
	 * @return the twitter instance
	 */
	public static Twitter getTwitterInstance() {
		if (twitter == null) {
			twitter = TwitterFactory.getSingleton();
			twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEYSECRET);
			twitter.setOAuthAccessToken(new AccessToken(ACCESSTOKEN, ACCESSTOKEN_SECRET));
		}
		return twitter;
	}

	/**
	 * Gets the news feed.
	 *
	 * @param twitterInstance the twitter instance
	 * @param screenName the screen name
	 * @param totalFeed the total feed
	 * @return the news feed
	 */
	public static List<Feed> getNewsFeedByScreenName(Twitter twitterInstance, String screenName, int totalFeed) {
		List<Feed> feeds = new ArrayList<Feed>();
		int startFeedPosition = NumberUtils.INTEGER_ONE;
		try {
			screenName = screenName == null ? StringUtils.EMPTY : screenName;
			ResponseList<Status> statuses = twitter.getUserTimeline(screenName, new Paging(startFeedPosition, totalFeed));
			if (statuses != null && !statuses.isEmpty()) {
				Feed feed = null;
				for (Status status : statuses) {
					feed = new Feed();
					Date createDate = status.getCreatedAt();
					createDate = getDateGMT(createDate);
					feed.setCreateDate(createDate);
					feed.setFeedContent(status.getText());
					feed.setFeedLink(getFeedLink(screenName, status.getId()));
					feeds.add(feed);
				}
			}
		} catch (TwitterException e) {
			LOGGER.error("Error when get new feed from Twitter API {}", e);
		}

		return feeds;
	}
	
	/**
	 * Gets the date GMT.
	 *
	 * @param date the date
	 * @return the date GMT
	 */
	private static Date getDateGMT(Date date){
		Date dateGMT = new Date();
		if (date != null) {
			TimeZone timeZone = TimeZone.getTimeZone("ICT");
			GregorianCalendar gregorianCalendar = new GregorianCalendar(timeZone);
			gregorianCalendar.setTime(date);
			gregorianCalendar.set(GregorianCalendar.HOUR, -8);
			dateGMT = gregorianCalendar.getTime();
		}
		return dateGMT;
	}
	
	/**
	 * Gets the feed link.
	 *
	 * @param screenName the screen name
	 * @param feedId the feed id
	 * @return the feed link
	 */
	private static String getFeedLink(String screenName, long feedId){
		StringBuffer feedLink = new StringBuffer(StringUtils.EMPTY);
		if(StringUtils.isNotBlank(screenName)){
			feedLink.append(Constants.TWITTER_URL);
			feedLink.append(screenName);
			feedLink.append(FLASH);
			feedLink.append(Constants.TWITTER_STATUS);
			feedLink.append(FLASH);
			feedLink.append(feedId);
		}
		return feedLink.toString();
	}

}
