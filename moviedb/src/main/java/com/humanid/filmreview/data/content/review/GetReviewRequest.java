package com.humanid.filmreview.data.content.review;

import com.humanid.filmreview.BuildConfig;
import com.humanid.filmreview.data.base.BaseRequest;
import com.humanid.filmreview.data.content.review.model.ReviewRequest;
import com.humanid.filmreview.data.content.review.model.ReviewResponse;
import com.humanid.filmreview.model.Review;
import java.util.ArrayList;

public class GetReviewRequest extends BaseRequest<ReviewRequest, ReviewResponse> {

    private OnGetReviewCallback onGetReviewCallback;

    private String id;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public OnGetReviewCallback getOnGetReviewCallback() {
        return onGetReviewCallback;
    }

    public void setOnGetReviewCallback(
            final OnGetReviewCallback onGetReviewCallback) {
        this.onGetReviewCallback = onGetReviewCallback;
    }

    @Override
    protected void onRequestSuccess(final ReviewResponse response) {
        ArrayList<Review> reviews = new ArrayList<>();
        for(com.humanid.filmreview.data.content.review.model.Review review: response.getReviewData().getReviews()){
            Review userReview = new Review(review.getComment(), review.getUserName());
            reviews.add(userReview);
        }
        getOnGetReviewCallback().onGetReviewSuccess(reviews);
    }

    @Override
    protected String getUrl() {
        return BuildConfig.DEMO_APP + "movies/" + getId() + "/comments?limit=30";
    }

    @Override
    protected void onLoading() {
        getOnGetReviewCallback().onLoading();
    }

    @Override
    protected void onRequestFailure(final String message) {
        getOnGetReviewCallback().onGetReviewFailed(message);
    }

    @Override
    protected void onTokenExpired() {
        getOnGetReviewCallback().onUnauthorized();
    }

    public interface OnGetReviewCallback{
        void onLoading();

        void onGetReviewSuccess(ArrayList<Review> reviews);

        void onGetReviewFailed(String message);

        void onUnauthorized();
    }
}
