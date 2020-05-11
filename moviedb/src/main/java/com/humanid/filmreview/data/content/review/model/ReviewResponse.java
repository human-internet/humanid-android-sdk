package com.humanid.filmreview.data.content.review.model;

import com.google.gson.annotations.SerializedName;
import com.humanid.filmreview.data.base.ResponseModel;

public class ReviewResponse extends ResponseModel {
    @SerializedName("data")
    private ReviewData reviewData;

    public ReviewData getReviewData() {
        return reviewData;
    }

    public void setReviewData(final ReviewData reviewData) {
        this.reviewData = reviewData;
    }
}
