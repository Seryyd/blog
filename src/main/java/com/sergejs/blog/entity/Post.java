package com.sergejs.blog.entity;

import org.springframework.context.annotation.Bean;


public class Post {

    private int id;
    private String timestamp;
    private String authorId;
    private String title;
    private String shortText;
    private String longText;

    public Post(){

    }
    public Post(int id, String timestamp, String authorId, String title, String shortText, String longText) {
        this.id = id;
        this.timestamp = timestamp;
        this.authorId = authorId;
        this.title = title;
        this.shortText = shortText;
        this.longText = longText;
    }

    @Override
    public String toString(){
        return String.format("Post[id=%d, timestampt=%s, author=%s, title=%s", id, timestamp, authorId, title);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
