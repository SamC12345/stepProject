package com.google.sps.servlets;

import com.google.sps.servlets.Comment;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import java.util.ArrayList;


public class CommentDAO{
    DatastoreService datastore;

    public CommentDAO(){
        this.datastore = DatastoreServiceFactory.getDatastoreService();
    }

    public void addComment(Comment comment){
        this.datastore.put(comment.createCommentEntity());
    }

    public ArrayList<Comment> getAllComments(){
        Query query = new Query("Comment").addSort("timestamp", SortDirection.DESCENDING);
        PreparedQuery results = this.datastore.prepare(query);

        ArrayList<Comment> commentList = new ArrayList<Comment>();
        for (Entity commentEntity : results.asIterable()) {
            commentList.add(new Comment(commentEntity));
        }
        return commentList;
    }
}