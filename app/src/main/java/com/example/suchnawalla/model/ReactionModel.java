package com.example.suchnawalla.model;

public class ReactionModel {
    String userID;
    String noticeID;
    int reaction; // 1 for sad, 2 for happy
    public ReactionModel() {
    }

    public ReactionModel( String userID, String noticeID, int reaction) {
        this.userID = userID;
        this.noticeID = noticeID;
        this.reaction = reaction;
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

    public int getReaction() {
        return reaction;
    }

    public void setReaction(int reaction) {
        this.reaction = reaction;
    }
}
