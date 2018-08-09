package com.sergejs.blog.entity;

public class Post {

    private String id;
    private String timestamp;
    private String authorId;
    private String title;
    private String shortText;
    private String longText;

    public Post(){

    }
    public Post(String id, String timestamp, String authorId, String title, String shortText, String longText) {
        this.id = id;
        this.timestamp = timestamp;
        this.authorId = authorId;
        this.title = title;
        this.shortText = shortText;
        this.longText = longText;
    }

    public Post(String timestamp, String authorId, String title, String shortText, String longText) {
        //this.id = id;
        this.timestamp = timestamp;
        this.authorId = authorId;
        this.title = title;
        this.shortText = shortText;
        this.longText = longText;
    }

    @Override
    public String toString(){
        return String.format("Post[id=%s, timestampt=%s, author=%s, title=%s", id, timestamp, authorId, title);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortText() {
        return shortText;
    }

    public void setShortText(String shortText) {
        this.shortText = shortText;
    }

    public String getLongText() {
        return longText;
    }

    public void setLongText(String longText) {
        this.longText = longText;
    }
}
