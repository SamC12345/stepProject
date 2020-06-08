package com.google.sps.servlets;


import com.google.appengine.api.datastore.Entity;

public class Comment{
    private String commentText;
    private long timestamp;

    public Comment(String commentText){
        this.commentText = commentText;
        this.timestamp = System.currentTimeMillis();
    }

    public Comment(Entity commentEntity){
        this.commentText = (String) commentEntity.getProperty("commentText");
        this.timestamp = (long) commentEntity.getProperty("timestamp");
    }

    public String getCommentText(){
        return this.commentText;
    }

    public long getTimeStamp(){
        return this.timestamp;
    }

    public Entity createCommentEntity(){
        Entity commentEntity = new Entity("Comment");
        commentEntity.setProperty("commentText", this.commentText);
        commentEntity.setProperty("timestamp", this.timestamp);
        return commentEntity;
    }
}