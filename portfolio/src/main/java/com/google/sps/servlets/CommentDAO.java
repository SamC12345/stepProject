package com.google.sps.servlets;

import com.google.sps.servlets.Comment;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.FetchOptions;
import java.util.ArrayList;
import java.util.List;
import java.lang.Iterable;


public class CommentDAO{
    DatastoreService datastore;

    public CommentDAO(){
        this.datastore = DatastoreServiceFactory.getDatastoreService();
    }

    public void addComment(Comment comment){
        this.datastore.put(comment.createCommentEntity());
    }

    public ArrayList<Comment> getComments(long commentLimit){
        PreparedQuery results = getCommentsPreparedQuery();
        FetchOptions options = createOptionsForGetComments(commentLimit);
        List<Entity> limitedResults = results.asList(options);
        ArrayList<Comment> commentList = convertCommentEntityListToCommentList(limitedResults);

        return commentList;
    }

    public void deleteComments(){
        PreparedQuery results = getCommentsPreparedQuery();
        deleteCommentsInPreparedQuery(results);
    }

//------------------------------------------------------------------------------------------------------
//UTILITY FUNCTIONS
//------------------------------------------------------------------------------------------------------
    private PreparedQuery getCommentsPreparedQuery(){
        Query query = new Query("Comment").addSort("timestamp", SortDirection.DESCENDING);
        PreparedQuery results = this.datastore.prepare(query);
        return results;
    }

    private FetchOptions createOptionsForGetComments(long commentLimit){
        FetchOptions options = FetchOptions.Builder.withDefaults();
        if(commentLimit != 0){
            options.limit((int) commentLimit);
        }
        return options;
    }

    private ArrayList<Comment> convertCommentEntityListToCommentList(List<Entity> CommentEntityList){
        ArrayList<Comment> commentList = new ArrayList<Comment>();
        CommentEntityList.forEach((commentEntity) -> commentList.add(new Comment(commentEntity)));
        return commentList;
    }

    private void deleteCommentsInPreparedQuery(PreparedQuery results){
        Iterable<Entity> resultList = results.asIterable();
        resultList.forEach((commentEntity) -> datastore.delete(commentEntity.getKey()));
    }
}