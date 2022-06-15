package com.tiwari.suraj.rello;

public class VideoInfo {


        // string variable for
        // storing employee name.
        private String videoName;

        // string variable for storing
        // employee contact number
        private String videoDescription;

        // string variable for storing
        // employee address.
        private String videoTag;



        // an empty constructor is
        // required when using
        // Firebase Realtime Database.
        public VideoInfo() {

        }

        // created getter and setter methods
        // for all our variables.
        public String getVideoName() {
            return videoName;
        }

        public void setVideoName(String videoName) {
            this.videoName = videoName;
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
    }


