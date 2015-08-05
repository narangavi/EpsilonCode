package com.calix.model;

/**
 * The Class TextAreaItem.
 */
public class TextArea {

	/** The head line. */
	private String headLine;
	
	/** The body text. */
	private String bodyText;
	
	/** The image path. */
	private String imagePath;
	
	/** The image position. */
	private String imagePosition;
	
	
	/** The display background. */
	private boolean displayBackground;
	
	/** The primary cta. */
	private CtaButton primaryCta;
	
	/** The secondary cta. */
	private CtaButton secondaryCta;
	
	
	/**
	 * Gets the css class name.
	 *
	 * @return the css class name
	 */
	public String getCssClassName() {
		return cssClassName;
	}

	/**
	 * Sets the css class name.
	 *
	 * @param cssClassName the new css class name
	 */
	public void setCssClassName(String cssClassName) {
		this.cssClassName = cssClassName;
	}

	/** The css class name. */
	private String cssClassName;
	
	/**
	 * Gets the head line.
	 *
	 * @return the head line
	 */
	public String getHeadLine() {
		return headLine;
	}
	
	/**
	 * Sets the head line.
	 *
	 * @param headLine the new head line
	 */
	public void setHeadLine(String headLine) {
		this.headLine = headLine;
	}
	
	/**
	 * Gets the body text.
	 *
	 * @return the body text
	 */
	public String getBodyText() {
		return bodyText;
	}
	
	/**
	 * Sets the body text.
	 *
	 * @param bodyText the new body text
	 */
	public void setBodyText(String bodyText) {
		this.bodyText = bodyText;
	}
	
	/**
	 * Gets the image path.
	 *
	 * @return the image path
	 */
	public String getImagePath() {
		return imagePath;
	}
	
	/**
	 * Sets the image path.
	 *
	 * @param imagePath the new image path
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	/**
	 * Gets the image position.
	 *
	 * @return the image position
	 */
	public String getImagePosition() {
		return imagePosition;
	}
	
	/**
	 * Sets the image position.
	 *
	 * @param imagePosition the new image position
	 */
	public void setImagePosition(String imagePosition) {
		this.imagePosition = imagePosition;
	}
	
	/**
	 * Gets the primary cta.
	 *
	 * @return the primary cta
	 */
	public CtaButton getPrimaryCta() {
		return primaryCta;
	}
	
	/**
	 * Sets the primary cta.
	 *
	 * @param primaryCta the new primary cta
	 */
	public void setPrimaryCta(CtaButton primaryCta) {
		this.primaryCta = primaryCta;
	}
	
	/**
	 * Gets the secondary cta.
	 *
	 * @return the secondary cta
	 */
	public CtaButton getSecondaryCta() {
		return secondaryCta;
	}
	
	/**
	 * Sets the secondary cta.
	 *
	 * @param secondaryCta the new secondary cta
	 */
	public void setSecondaryCta(CtaButton secondaryCta) {
		this.secondaryCta = secondaryCta;
	}

	/**
	 * Checks if is display background.
	 *
	 * @return true, if is display background
	 */
	public boolean isDisplayBackground() {
		return displayBackground;
	}

	/**
	 * Sets the display background.
	 *
	 * @param displayBackground the new display background
	 */
	public void setDisplayBackground(boolean displayBackground) {
		this.displayBackground = displayBackground;
	}
	
	
}