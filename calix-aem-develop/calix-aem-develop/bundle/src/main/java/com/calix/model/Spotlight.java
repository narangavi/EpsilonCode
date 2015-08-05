package com.calix.model;

/**
 * The Class Spotlight.
 */
public class Spotlight {

	String date = "";
	String type = "";
	String description = "";
	String linkAction = "";
    String title = "";
	String image = "";
	String label = "";
	String wistiaId = "";
	String shortWistiaId = "";
	String target = "";

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLinkAction() {
		return linkAction;
	}

	public void setLinkAction(String linkAction) {
		this.linkAction = linkAction;
	}

	public String getWistiaId() {
		return wistiaId;
	}

	public void setWistiaId(String wistiaId) {
		this.wistiaId = wistiaId;
	}

	public String getShortWistiaId() {
		return shortWistiaId;
	}

	public void setShortWistiaId(String shortWistiaId) {
		this.shortWistiaId = shortWistiaId;
	}

	public Spotlight(){}
	public Spotlight(String image, String date, String desc){
		this.image = image;
		this.date = date;
		this.description = desc;
	}

}
