package com.tiwari.suraj.rello;

public class Comment  {
    private String usernameComment,userComment;

    public Comment(String usernameComment, String userComment) {
        this.usernameComment = usernameComment;
        this.userComment = userComment;
    }
    public Comment(){

    }

    public String getUsernameComment() {
        return usernameComment;
    }

    public void setUsernameComment(String usernameComment) {
        this.usernameComment = usernameComment;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }


}
