package com.calix.model;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class Slide.
 */
public class Slide {

	/** The path image. */
	private String pathImage;

	/** The leadin. */
	private String leadin;

	/** The headline. */
	private String headline;//main line

	/** The subhead. */
	private String subhead; //by line

	/** The caption. */
	private String caption;

	/** The title cta. */
	private String titleCTA;

	/** The link cta. */
	private String linkCTA;

	/** The align type. */
	private String alignType;

	/** The button type. */
	private String buttonType;

	/** The target cta. */
	private String targetCTA;

	/** The video groups. */
	private List<VideoGroup> videoGroups;

	/** The modal id. */
	private String modalId;

	private Boolean isVideo;

	public Boolean getIsVideo() {
		return isVideo;
	}

	public void setIsVideo(Boolean isVideo) {
		this.isVideo = isVideo;
	}

	/**
	 * Gets the path image.
	 *
	 * @return the path image
	 */


	public String getPathImage() {
		return pathImage;
	}

	/**
	 * Sets the path image.
	 *
	 * @param pathImage the new path image
	 */
	public void setPathImage(String pathImage) {
		this.pathImage = pathImage;
	}

	/**
	 * Gets the leadin.
	 *
	 * @return the leadin
	 */
	public String getLeadin() {
		return leadin;
	}

	/**
	 * Sets the leadin.
	 *
	 * @param leadin the new leadin
	 */
	public void setLeadin(String leadin) {
		this.leadin = leadin;
	}

	/**
	 * Gets the headline.
	 *
	 * @return the headline
	 */
	public String getHeadline() {
		return headline;
	}

	/**
	 * Sets the headline.
	 *
	 * @param headline the new headline
	 */
	public void setHeadline(String headline) {
		this.headline = headline;
	}

	/**
	 * Gets the subhead.
	 *
	 * @return the subhead
	 */
	public String getSubhead() {
		return subhead;
	}

	/**
	 * Sets the subhead.
	 *
	 * @param subhead the new subhead
	 */
	public void setSubhead(String subhead) {
		this.subhead = subhead;
	}

	/**
	 * Gets the caption.
	 *
	 * @return the caption
	 */
	public String getCaption() {
		return caption;
	}

	/**
	 * Sets the caption.
	 *
	 * @param caption the new caption
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}

	/**
	 * Gets the title cta.
	 *
	 * @return the title cta
	 */
	public String getTitleCTA() {
		return titleCTA;
	}

	/**
	 * Sets the title cta.
	 *
	 * @param titleCTA the new title cta
	 */
	public void setTitleCTA(String titleCTA) {
		this.titleCTA = titleCTA;
	}

	/**
	 * Gets the link cta.
	 *
	 * @return the link cta
	 */
	public String getLinkCTA() {
		return linkCTA;
	}

	/**
	 * Sets the link cta.
	 *
	 * @param linkCTA the new link cta
	 */
	public void setLinkCTA(String linkCTA) {
		this.linkCTA = linkCTA;
	}

	/**
	 * Gets the align type.
	 *
	 * @return the align type
	 */
	public String getAlignType() {
		return alignType;
	}

	/**
	 * Sets the align type.
	 *
	 * @param alignType the new align type
	 */
	public void setAlignType(String alignType) {
		this.alignType = alignType;
	}

	/**
	 * Gets the button type.
	 *
	 * @return the button type
	 */
	public String getButtonType() {
		return buttonType;
	}

	/**
	 * Sets the button type.
	 *
	 * @param buttonType the new button type
	 */
	public void setButtonType(String buttonType) {
		this.buttonType = buttonType;
	}

	/**
	 * Gets the target cta.
	 *
	 * @return the target cta
	 */
	public String getTargetCTA() {
		return targetCTA;
	}

	/**
	 * Sets the target cta.
	 *
	 * @param targetCTA the new target cta
	 */
	public void setTargetCTA(String targetCTA) {
		this.targetCTA = targetCTA;
	}

	/**
	 * Gets the video groups.
	 *
	 * @return the video groups
	 */
	public List<VideoGroup> getVideoGroups() {
		return videoGroups;
	}

	/**
	 * Sets the video groups.
	 *
	 * @param videoGroups the new video groups
	 */
	public void setVideoGroups(List<VideoGroup> videoGroups) {
		this.videoGroups = videoGroups;
	}

	/**
	 * Gets the modal id.
	 *
	 * @return the modal id
	 */
	public String getModalId() {
		return modalId;
	}

	/**
	 * Sets the modal id.
	 *
	 * @param modalId the new modal id
	 */
	public void setModalId(String modalId) {
		this.modalId = modalId;
	}

}
