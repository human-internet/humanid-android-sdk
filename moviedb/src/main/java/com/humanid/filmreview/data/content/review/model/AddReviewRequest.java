package com.humanid.filmreview.data.content.review.model;

import com.google.gson.annotations.SerializedName;
import com.humanid.filmreview.data.base.RequestModel;
import org.json.JSONException;
import org.json.JSONObject;

public class AddReviewRequest extends RequestModel {
    @SerializedName("comment")
    private String comment;

    public AddReviewRequest(final String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    @Override
    protected String getJsonString() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("comment", getComment());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
}
