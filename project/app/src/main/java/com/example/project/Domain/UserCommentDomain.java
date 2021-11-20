package com.example.project.Domain;

import java.io.Serializable;

public class UserCommentDomain implements Serializable {
    private String pic;
    private String username;
    private String comment;

    public UserCommentDomain(String pic, String username, String comment) {
        this.pic = pic;
        this.username = username;
        this.comment = comment;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
