// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import com.google.sps.servlets.Comment;
import com.google.sps.servlets.CommentDAO;
import com.google.sps.servlets.CommentUtils;

@WebServlet("/comment")
public class CommentServlet extends HttpServlet {

  private CommentDAO commentDAO = new CommentDAO();
  private CommentUtils commentUtils = new CommentUtils();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    long commentLimit = getCommentLimitFromRequest(request);
    ArrayList<Comment> commentList = commentDAO.getComments(commentLimit);
    String commentJSON = commentUtils.convertCommentsToJson(commentList);
    respondWithString(response, commentJSON);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Comment comment = getCommentFromRequest(request);
    commentDAO.addComment(comment);
    respondWithString(response, comment.getCommentText());
  }

  @Override
  public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
    commentDAO.deleteComments();
    respondWithString(response, "All Comments Deleted!");
  }

  //------------------------------------------------------------------------------------------------------
  //UTILITY FUNCTIONS
  //------------------------------------------------------------------------------------------------------
  
  private Comment getCommentFromRequest(HttpServletRequest request){
    String commentText = getParameter(request, "commentText", "An Error Occurred: commentText not found");
    return new Comment(commentText);
  }

  private long getCommentLimitFromRequest(HttpServletRequest request){
    String commentLimit = getParameter(request, "commentLimit", "0");
    return Long.valueOf(commentLimit);
  }

  private String getParameter(HttpServletRequest request, String name, String defaultValue) {
    String value = request.getParameter(name);
    if (value == null) {
      return defaultValue;
    }
    return value;
  }

  private void respondWithString(HttpServletResponse response, String message) throws IOException {
    response.setContentType("text/html;");
    response.getWriter().println(message);
  }
}
  