package com.software.acuity.splick.models.home;

import java.util.ArrayList;
import java.util.List;

public class Comment {

    String user_id = "";
    String comment = "";
    CommentUser commentUser;

    public Comment(){

    }

    public Comment(String user_id, String comment, CommentUser commentUsers) {
        this.user_id = user_id;
        this.comment = comment;
        this.commentUser = commentUsers;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public CommentUser getCommentUser() {
        return commentUser;
    }

    public void setCommentUser(CommentUser commentUser) {
        this.commentUser = commentUser;
    }
}
