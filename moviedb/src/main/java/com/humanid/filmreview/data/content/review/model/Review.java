package com.humanid.filmreview.data.content.review.model;

import com.google.gson.annotations.SerializedName;

public class Review {
    @SerializedName("comment")
    private String comment;

    @SerializedName(("userName"))
    private String userName;

    public String getComment() {
        return comment;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }
}
