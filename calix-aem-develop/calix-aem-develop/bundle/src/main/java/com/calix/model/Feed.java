package com.calix.model;

import java.util.Date;

/**
 * The Class Feed.
 */
public class Feed {
	
	/** The create date. */
	private Date createDate;
	
	/** The feed content. */
	private String feedContent;
	
	/** The feed link. */
	private String feedLink;


	/**
	 * Gets the creates the date.
	 *
	 * @return the creates the date
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * Sets the creates the date.
	 *
	 * @param createDate the new creates the date
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * Gets the feed content.
	 *
	 * @return the feed content
	 */
	public String getFeedContent() {
		return feedContent;
	}

	/**
	 * Sets the feed content.
	 *
	 * @param feedContent the new feed content
	 */
	public void setFeedContent(String feedContent) {
		this.feedContent = feedContent;
	}

	/**
	 * Gets the feed link.
	 *
	 * @return the feed link
	 */
	public String getFeedLink() {
		return feedLink;
	}

	/**
	 * Sets the feed link.
	 *
	 * @param feedLink the new feed link
	 */
	public void setFeedLink(String feedLink) {
		this.feedLink = feedLink;
	}

}
