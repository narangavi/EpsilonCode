package com.calix.model;

/**
 * The Class Video.
 */
public class Video {
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String WISTIA_ID = "wistiaId";
    public static final String IMAGE_LINK = "imageLink";
    private int id;
    private String title;
    private String description;
    private String wistiaId;
    private String imageLink;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWistiaId() {
        return wistiaId;
    }

    public void setWistiaId(String wistiaId) {
        this.wistiaId = wistiaId;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public Video(){
        this.title = "";
        this.description = "";
        this.wistiaId = "";
        this.imageLink = "";
    }
}
