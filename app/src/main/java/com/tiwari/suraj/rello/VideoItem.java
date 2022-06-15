package com.tiwari.suraj.rello;

public class VideoItem {
    public  String videoDescription, videoTag, videoTitle,videoUrl;

    public VideoItem(String videoDescription, String videoTag, String videoTitle, String videoUrl) {
        this.videoDescription = videoDescription;
        this.videoTag = videoTag;
        this.videoTitle = videoTitle;
        this.videoUrl = videoUrl;
    }

    public VideoItem() {

    }

    public String getVideoDescription() {
        return videoDescription;
    }

    public void setVideoDescription(String videoDescription) {
        this.videoDescription = videoDescription;
    }

    public String getVideoTag() {
        return videoTag;
    }

    public void setVideoTag(String videoTag) {
        this.videoTag = videoTag;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
