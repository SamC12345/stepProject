package com.google.sps.servlets;

import com.google.sps.servlets.Comment;
import java.util.ArrayList;
import com.google.gson.Gson;

public class CommentUtils{

    public CommentUtils(){ }

    public String convertCommentsToJson(ArrayList<Comment> comments) {
    Gson gson = new Gson();
    String json = gson.toJson(comments);
    return json;
  }

}