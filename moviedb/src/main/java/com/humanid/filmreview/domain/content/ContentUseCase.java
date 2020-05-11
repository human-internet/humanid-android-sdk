package com.humanid.filmreview.domain.content;

import com.humanid.filmreview.data.content.review.GetReviewRequest.OnGetReviewCallback;
import com.humanid.filmreview.data.content.review.PostReviewRequest.OnPostCommentCallback;

public interface ContentUseCase {
    void submitComment(String id, String title, String message, OnPostCommentCallback onPostCommentCallback);

    void getReview(String id, OnGetReviewCallback onGetReviewCallback);
}
