package com.calix.model;

/**
 * The Class FullWidthFeature.
 */
public class FullWidthFeatureItem {

	/** The display top curve. */
	private boolean displayTopCurve;
	
	/** The display bottom curve. */
	private boolean displayBottomCurve;
	
	/** The display secondary text area. */
	private boolean displaySecondaryTextArea;
	
	/** The primary text area. */
	private TextArea primaryTextArea;
	
	/** The secondary text area. */
	private TextArea secondaryTextArea;

	/**
	 * Checks if is display top curve.
	 *
	 * @return true, if is display top curve
	 */
	public boolean isDisplayTopCurve() {
		return displayTopCurve;
	}

	/**
	 * Sets the display top curve.
	 *
	 * @param displayTopCurve the new display top curve
	 */
	public void setDisplayTopCurve(boolean displayTopCurve) {
		this.displayTopCurve = displayTopCurve;
	}

	/**
	 * Checks if is display bottom curve.
	 *
	 * @return true, if is display bottom curve
	 */
	public boolean isDisplayBottomCurve() {
		return displayBottomCurve;
	}

	/**
	 * Sets the display bottom curve.
	 *
	 * @param displayBottomCurve the new display bottom curve
	 */
	public void setDisplayBottomCurve(boolean displayBottomCurve) {
		this.displayBottomCurve = displayBottomCurve;
	}

	/**
	 * Checks if is display secondary text area.
	 *
	 * @return true, if is display secondary text area
	 */
	public boolean isDisplaySecondaryTextArea() {
		return displaySecondaryTextArea;
	}

	/**
	 * Sets the display secondary text area.
	 *
	 * @param displaySecondaryTextArea the new display secondary text area
	 */
	public void setDisplaySecondaryTextArea(boolean displaySecondaryTextArea) {
		this.displaySecondaryTextArea = displaySecondaryTextArea;
	}

	/**
	 * Gets the primary text area.
	 *
	 * @return the primary text area
	 */
	public TextArea getPrimaryTextArea() {
		return primaryTextArea;
	}

	/**
	 * Sets the primary text area.
	 *
	 * @param primaryTextArea the new primary text area
	 */
	public void setPrimaryTextArea(TextArea primaryTextArea) {
		this.primaryTextArea = primaryTextArea;
	}

	/**
	 * Gets the secondary text area.
	 *
	 * @return the secondary text area
	 */
	public TextArea getSecondaryTextArea() {
		return secondaryTextArea;
	}

	/**
	 * Sets the secondary text area.
	 *
	 * @param secondaryTextArea the new secondary text area
	 */
	public void setSecondaryTextArea(TextArea secondaryTextArea) {
		this.secondaryTextArea = secondaryTextArea;
	}

}
