package com.example.suchnawalla.model;

public class CommentModel {
    String id;
    String userID;
    String noticeID;
    String comment;

    public CommentModel() {
    }

    public CommentModel(String id, String userID, String noticeID, String comment) {
        this.id = id;
        this.userID = userID;
        this.noticeID = noticeID;
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getNoticeID() {
        return noticeID;
    }

    public void setNoticeID(String noticeID) {
        this.noticeID = noticeID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
