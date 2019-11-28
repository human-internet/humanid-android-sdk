package com.humanid.sample.auth.app1.utils.databinding;

import android.text.TextUtils;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class CustomBindingAdapter {

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        if (TextUtils.isEmpty(url)) {
            imageView.setImageDrawable(null);
        } else {
            Glide.with(imageView.getContext())
                    .load(url)
                    .into(imageView);
        }
    }
}
