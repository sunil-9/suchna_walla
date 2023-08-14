package com.example.suchnawalla.model;

public class NoticeModel {
    String id;
    String title;
    String desc;
    String date;
    int happy;
    int sad;
    String documentUrl;
    String documentName;
    String imageUrl;



    public NoticeModel() {
    }

    public NoticeModel(String id, String title, String desc, String date, int happy, int sad, String documentUrl, String documentName, String imageUrl) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.date = date;
        this.happy = happy;
        this.sad = sad;
        this.documentUrl = documentUrl;
        this.documentName = documentName;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getHappy() {
        return happy;
    }

    public void setHappy(int happy) {
        this.happy = happy;
    }

    public int getSad() {
        return sad;
    }

    public void setSad(int sad) {
        this.sad = sad;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }
}
