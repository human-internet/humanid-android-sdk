package com.humanid.filmreview.data.content.review;

import com.humanid.filmreview.BuildConfig;
import com.humanid.filmreview.data.base.BaseRequest;
import com.humanid.filmreview.data.content.review.model.AddReviewRequest;
import com.humanid.filmreview.data.content.review.model.PostReviewResponse;

public class PostReviewRequest extends BaseRequest<AddReviewRequest, PostReviewResponse> {

    private OnPostCommentCallback onPostCommentCallback;

    private String id;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public OnPostCommentCallback getOnPostCommentCallback() {
        return onPostCommentCallback;
    }

    public void setOnPostCommentCallback(final OnPostCommentCallback onPostCommentCallback) {
        this.onPostCommentCallback = onPostCommentCallback;
    }

    @Override
    protected void onRequestSuccess(final PostReviewResponse response) {
        getOnPostCommentCallback().onPostCommentSuccess();
    }

    @Override
    protected String getUrl() {
        return BuildConfig.DEMO_APP + "movies/"+ getId() + "/comment";
    }

    @Override
    protected void onLoading() {
        getOnPostCommentCallback().onLoading();
    }

    @Override
    protected void onRequestFailure(final String message) {
        getOnPostCommentCallback().onPostCommentFailed(message);
    }

    public interface OnPostCommentCallback{
        void onLoading();

        void onPostCommentSuccess();

        void onPostCommentFailed(String message);
    }
}
