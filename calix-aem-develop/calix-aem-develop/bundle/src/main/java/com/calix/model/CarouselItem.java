package com.calix.model;

/**
 * The Class CarouselItem.
 */
public class CarouselItem {

	/** The date. */
	String date = "";
	
	/** The type. */
	String type = "";
	
	/** The description. */
	String description = "";
	
	/** The link action. */
	String linkAction = "";
	
	/** The link target. */
	String linkTarget = "_self";

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Sets the date.
	 *
	 * @param date the new date
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the link action.
	 *
	 * @return the link action
	 */
	public String getLinkAction() {
		return linkAction;
	}

	/**
	 * Sets the link action.
	 *
	 * @param linkAction the new link action
	 */
	public void setLinkAction(String linkAction) {
		this.linkAction = linkAction;
	}
	
	/**
	 * Gets the link target.
	 *
	 * @return the link target
	 */
	public String getLinkTarget() {
		return linkTarget;
	}

	/**
	 * Sets the link action.
	 *
	 * @param linkAction the new link action
	 */
	public void setLinkTarget(String linkTarget) {
		this.linkTarget = linkTarget;
	}
}
