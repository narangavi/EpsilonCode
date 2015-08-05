package com.calix.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class VideoGroup.
 */
public class VideoLibrary {
    private int numOfRelated;
    private Video mainVideo;
    private List<VideoGroup> videoGroups;

    public int getNumOfRelated() {
        return numOfRelated;
    }

    public void setNumOfRelated(int numOfRelated) {
        this.numOfRelated = numOfRelated;
    }

    public Video getMainVideo() {
        return mainVideo;
    }

    public void setMainVideo(Video mainVideo) {
        this.mainVideo = mainVideo;
    }

    public List<VideoGroup> getVideoGroups() {
        return videoGroups;
    }

    public void setVideoGroups(List<VideoGroup> videoGroups) {
        this.videoGroups = videoGroups;
    }

    public VideoLibrary(){
        this.numOfRelated = 0;
        this.setVideoGroups(new ArrayList<VideoGroup>());
    }
}
