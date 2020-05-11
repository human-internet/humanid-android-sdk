package com.humanid.filmreview.domain.content;

import com.humanid.filmreview.data.base.RequestMethod;
import com.humanid.filmreview.data.content.review.GetReviewRequest;
import com.humanid.filmreview.data.content.review.GetReviewRequest.OnGetReviewCallback;
import com.humanid.filmreview.data.content.review.PostReviewRequest;
import com.humanid.filmreview.data.content.review.PostReviewRequest.OnPostCommentCallback;
import com.humanid.filmreview.data.content.review.model.AddReviewRequest;
import com.humanid.filmreview.data.content.review.model.PostReviewResponse;
import com.humanid.filmreview.data.content.review.model.ReviewRequest;
import com.humanid.filmreview.data.content.review.model.ReviewResponse;

public class ContentInteractor implements ContentUseCase {

    private PostReviewRequest mPostReviewRequest;

    private GetReviewRequest mGetReviewRequest;

    private static ContentUseCase contentUseCase;

    public ContentInteractor() {
        mPostReviewRequest = new PostReviewRequest();
        mGetReviewRequest = new GetReviewRequest();
    }

    public static ContentUseCase getInstance() {
        if (contentUseCase == null) {
            contentUseCase = new ContentInteractor();
        }
        return contentUseCase;
    }

    @Override
    public void submitComment(final String id, final String title, final String message,
            final OnPostCommentCallback onPostCommentCallback) {
        mPostReviewRequest.setId(id);
        mPostReviewRequest
                .setRequest(new AddReviewRequest(title + " - " + message));
        mPostReviewRequest.setOnPostCommentCallback(onPostCommentCallback);
        mPostReviewRequest.setRequestMethod(RequestMethod.POST);
        mPostReviewRequest.setResponse(new PostReviewResponse());
        mPostReviewRequest.execute();
    }

    @Override
    public void getReview(final String id, OnGetReviewCallback onGetReviewCallback) {
        mGetReviewRequest.setId(id);
        mGetReviewRequest.setOnGetReviewCallback(onGetReviewCallback);
        mGetReviewRequest.setRequest(new ReviewRequest());
        mGetReviewRequest.setRequestMethod(RequestMethod.GET);
        mGetReviewRequest.setResponse(new ReviewResponse());
        mGetReviewRequest.execute();
    }
}
