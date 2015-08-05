package com.calix.model;

import java.util.List;

/**
 * The Class Box.
 */
public class Box {
    private String photoLabel;
    private String viewAllLabel;
    private String featuredVideoLabel;
    private String eventLabel;
    private String newsLabel;
    private String pReleaseLabel;
    private String spearkerLabel;
    private boolean displayVideo;
    private boolean displayVideos;
    private boolean displayImage;
    private boolean displaySpotlight;
    private List<Spotlight> spotlightDisplay;
    private List<Spotlight> spotlightList;
    private String idModal;
    private String idCarousel;
    private List<VideoGroup> videoGroups;
    private Video video;

    public void setIdCarousel(String idCarousel) {
        this.idCarousel = idCarousel;
    }

    public String getIdCarousel() {
        return idCarousel;
    }

    public String getIdImageLib() {
        return idImageLib;
    }

    public void setIdImageLib(String idImageLib) {
        this.idImageLib = idImageLib;
    }

    private String idImageLib;

    public List<Spotlight> getSpotlightDisplay() {
        return spotlightDisplay;
    }

    public void setSpotlightDisplay(List<Spotlight> spotlightDisplay) {
        this.spotlightDisplay = spotlightDisplay;
    }

    public List<Spotlight> getSpotlightList() {
        return spotlightList;
    }

    public void setSpotlighList(List<Spotlight> spotlightList) {
        this.spotlightList = spotlightList;
    }

    public String getPhotoLabel() {
        return photoLabel;
    }

    public void setPhotoLabel(String photoLabel) {
        this.photoLabel = photoLabel;
    }

    public String getViewAllLabel() {
        return viewAllLabel;
    }

    public void setViewAllLabel(String viewAllLabel) {
        this.viewAllLabel = viewAllLabel;
    }

    public String getFeaturedVideoLabel() {
        return featuredVideoLabel;
    }

    public void setFeaturedVideoLabel(String featuredVideoLabel) {
        this.featuredVideoLabel = featuredVideoLabel;
    }

    public String getEventLabel() {
        return eventLabel;
    }

    public void setEventLabel(String eventLabel) {
        this.eventLabel = eventLabel;
    }

    public String getNewsLabel() {
        return newsLabel;
    }

    public void setNewsLabel(String newsLabel) {
        this.newsLabel = newsLabel;
    }

    public String getpReleaseLabel() {
        return pReleaseLabel;
    }

    public void setpReleaseLabel(String pReleaseLabel) {
        this.pReleaseLabel = pReleaseLabel;
    }

    public String getSpearkerLabel() {
        return spearkerLabel;
    }

    public void setSpearkerLabel(String spearkerLabel) {
        this.spearkerLabel = spearkerLabel;
    }

    public boolean isDisplayVideo() {
        return displayVideo;
    }

    public void setDisplayVideo(boolean displayVideo) {
        this.displayVideo = displayVideo;
    }

    public boolean isDisplayImage() {
        return displayImage;
    }

    public boolean isDisplayVideos() {
        return displayVideos;
    }

    public void setDisplayVideos(boolean displayVideos) {
        this.displayVideos = displayVideos;
    }

    public void setDisplayImage(boolean displayImage) {
        this.displayImage = displayImage;
    }

    public boolean isDisplaySpotlight() {
        return displaySpotlight;
    }

    public void setDisplaySpotlight(boolean displaySpotlight) {
        this.displaySpotlight = displaySpotlight;
    }

    public String getIdModal() {
        return idModal;
    }

    public void setIdModal(String idModal) {
        this.idModal = idModal;
    }

    public List<VideoGroup> getVideoGroups() {
        return videoGroups;
    }

    public void setVideoGroups(List<VideoGroup> videoGroups) {
        this.videoGroups = videoGroups;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public Box(){
        this.photoLabel = "";
        this.viewAllLabel = "";
        this.featuredVideoLabel = "";
        this.eventLabel = "";
        this.newsLabel = "";
        this.pReleaseLabel = "";
        this.spearkerLabel = "";
        this.displayImage = false;
        this.displayVideo = false;
        this.spotlightDisplay = null;
        this.spotlightList = null;
        this.idModal = "";
        this.videoGroups = null;
        this.video = null;
    }
}
