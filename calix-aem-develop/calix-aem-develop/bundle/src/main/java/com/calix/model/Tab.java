package com.calix.model;

import java.util.List;

/**
 * The Class Tab.
 */
public class Tab {

	/** The tab name. */
	private int tabId = 0;

	/** The tab name. */
	private String tabName = "";
    
    /** The activated. */
    private boolean activated = false;
    
    
    /** The contents. */
    private List<Article> contents;

	/**
	 * Gets the tab name.
	 *
	 * @return the tab name
	 */
	public int getTabId() {
		return tabId;
	}
	
	/**
	 * Sets the tab name.
	 *
	 * @param tabName the new tab name
	 */
	public void setTabId(int tabId) {
		this.tabId = tabId;
	}

	/**
	 * Gets the tab name.
	 *
	 * @return the tab name
	 */
	public String getTabName() {
		return tabName;
	}
	
	/**
	 * Sets the tab name.
	 *
	 * @param tabName the new tab name
	 */
	public void setTabName(String tabName) {
		this.tabName = tabName;
	}
	
	/**
	 * Checks if is activated.
	 *
	 * @return true, if is activated
	 */
	public boolean isActivated() {
		return activated;
	}
	
	/**
	 * Sets the activated.
	 *
	 * @param activated the new activated
	 */
	public void setActivated(boolean activated) {
		this.activated = activated;
	}
	

	/**
	 * Gets the contents.
	 *
	 * @return the contents
	 */
	public List<Article> getContents() {
		return contents;
	}

	/**
	 * Sets the contents.
	 *
	 * @param contents the new contents
	 */
	public void setContents(List<Article> contents) {
		this.contents = contents;
	}

}
