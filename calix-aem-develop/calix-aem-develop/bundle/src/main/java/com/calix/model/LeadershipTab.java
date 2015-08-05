package com.calix.model;

import java.util.List;

public class LeadershipTab {
	private String name;
	private String label;
	private String description;
	private List<ProfileItem> profiles;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<ProfileItem> getProfiles() {
		return profiles;
	}
	public void setProfiles(List<ProfileItem> profiles) {
		this.profiles = profiles;
	}
}
