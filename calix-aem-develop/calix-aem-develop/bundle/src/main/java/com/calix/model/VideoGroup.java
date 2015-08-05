package com.calix.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class VideoGroup.
 */
public class VideoGroup {
    private String title;
    private List<Video> videos;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public VideoGroup(){
        this.title = "";
        this.setVideos(new ArrayList<Video>());
    }
}
