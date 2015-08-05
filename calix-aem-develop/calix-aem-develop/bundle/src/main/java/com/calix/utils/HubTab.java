package com.calix.utils;

public enum HubTab {
	ALL(1, Constants.ALL_LABEL),
	PRESS(2, Constants.PRESS_RELEASE_LABEL),
	NEWS(3, Constants.NEWS_LABEL),
	EVENTS(4, Constants.CALIX_EVENT),
	SPEAKERS(5, Constants.CALIX_SPEAKER),
	FEATURED(6, Constants.FEATURED_LABEL);
	
	private final int id;
	private final String name;
	
	public int getId() {
		return this.id;
	}
	public String getName() {
		return this.name;
	}
	
	HubTab(final int id, final String name) {
		this.id = id;
		this.name = name;
	}
}
