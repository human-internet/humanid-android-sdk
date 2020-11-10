package com.humanid.filmreview.data.content.review.model;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class ReviewData {
    @SerializedName("comments")
    private ArrayList<Review> reviews = new ArrayList<>();

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(final ArrayList<Review> reviews) {
        this.reviews = reviews;
    }
}
