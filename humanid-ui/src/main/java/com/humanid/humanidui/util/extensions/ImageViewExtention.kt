package com.nbs.humanidui.util.extensions

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target


fun ImageView.setImageResource(c: Context, imageRes: Int) {
    Glide.with(c)
            .load(imageRes)
            .into(this)
}

fun ImageView.setImageResource(c: Context, imageRes: Int, placeholderResourceId: Int) {

    val options = RequestOptions()
            .centerCrop()
            .error(placeholderResourceId)

    Glide.with(c)
            .load(imageRes)
            .apply(options)
            .into(this)
}

fun ImageView.setImageResource(c: Context, imageRes: Int, placeHolderDrawable: Drawable) {

    val options = RequestOptions()
            .centerCrop()
            .error(placeHolderDrawable)

    Glide.with(c)
            .load(imageRes)
            .apply(options)
            .into(this)
}

fun ImageView.setImageResource(c: Context, imageRes: Int, placeHolderResourceId: Int, errorResourceId: Int) {
    val options = RequestOptions()
            .centerCrop()
            .placeholder(placeHolderResourceId)
            .error(errorResourceId)

    Glide.with(c)
            .load(imageRes)
            .apply(options)
            .into(this)
}

fun ImageView.setImageResource(c: Context, imageRes: Int, placeHolderDrawable: Drawable, errorDrawable: Drawable) {
    val options = RequestOptions()
            .centerCrop()
            .placeholder(placeHolderDrawable)
            .error(errorDrawable)

    Glide.with(c)
            .load(imageRes)
            .apply(options)
            .into(this)
}

fun ImageView.setImageResource(c: Context, imageRes: Int, progressBar: ProgressBar) {
    progressBar.visible()
    Glide.with(c)
            .load(imageRes)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                        e: GlideException?,
                        model: Any,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                ): Boolean {
                    progressBar.gone()
                    return false
                }

                override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                ): Boolean {
                    progressBar.gone()
                    return false
                }
            }).into(this)
}

fun ImageView.setImageResource(c: Context, imageRes: Int, progressBar: ProgressBar, errorResourceId: Int) {
    val options = RequestOptions()
            .error(errorResourceId)

    progressBar.visible()

    Glide.with(c)
            .load(imageRes)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                        e: GlideException?,
                        model: Any,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                ): Boolean {
                    progressBar.gone()
                    return false
                }

                override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                ): Boolean {
                    progressBar.gone()
                    return false
                }
            })
            .apply(options)
            .into(this)
}

fun ImageView.setImageResource(c: Context, imageRes: Int, progressBar: ProgressBar, errorDrawable: Drawable) {
    val options = RequestOptions()
            .error(errorDrawable)

    progressBar.visible()

    Glide.with(c)
            .load(imageRes)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                        e: GlideException?,
                        model: Any,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                ): Boolean {
                    progressBar.gone()
                    return false
                }

                override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                ): Boolean {
                    progressBar.gone()
                    return false
                }
            })
            .apply(options)
            .into(this)
}

//endregion

//region setImageDrawable

fun ImageView.setImageDrawable(c: Context, imageDrawable: Drawable) {
    Glide.with(c)
            .load(imageDrawable)
            .into(this)
}

fun ImageView.setImageDrawable(c: Context, drawable: Drawable, placeholderResourceId: Int) {

    val options = RequestOptions()
            .centerCrop()
            .error(placeholderResourceId)

    Glide.with(c)
            .load(drawable)
            .apply(options)
            .into(this)
}

fun ImageView.setImageDrawable(c: Context, drawable: Drawable, placeHolderDrawable: Drawable) {

    val options = RequestOptions()
            .centerCrop()
            .error(placeHolderDrawable)

    Glide.with(c)
            .load(drawable)
            .apply(options)
            .into(this)
}

fun ImageView.setImageDrawable(
        c: Context,
        imageDrawable: Drawable,
        placeHolderResourceId: Int,
        errorResourceId: Int
) {
    val options = RequestOptions()
            .centerCrop()
            .placeholder(placeHolderResourceId)
            .error(errorResourceId)

    Glide.with(c)
            .load(imageDrawable)
            .apply(options)
            .into(this)
}

fun ImageView.setImageDrawable(
        c: Context,
        imageDrawable: Drawable,
        placeHolderDrawable: Drawable,
        errorDrawable: Drawable
) {
    val options = RequestOptions()
            .centerCrop()
            .placeholder(placeHolderDrawable)
            .error(errorDrawable)

    Glide.with(c)
            .load(imageDrawable)
            .apply(options)
            .into(this)
}

fun ImageView.setImageDrawable(c: Context, imageDrawable: Drawable, progressBar: ProgressBar, errorResourceId: Int) {
    val options = RequestOptions()
            .error(errorResourceId)

    progressBar.visible()

    Glide.with(c)
            .load(imageDrawable)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                        e: GlideException?,
                        model: Any,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                ): Boolean {
                    progressBar.gone()
                    return false
                }

                override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                ): Boolean {
                    progressBar.gone()
                    return false
                }
            })
            .apply(options)
            .into(this)
}

fun ImageView.setImageDrawable(
        c: Context,
        imageDrawable: Drawable,
        progressBar: ProgressBar,
        errorDrawable: Drawable
) {
    val options = RequestOptions()
            .error(errorDrawable)

    progressBar.visible()

    Glide.with(c)
            .load(imageDrawable)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                        e: GlideException?,
                        model: Any,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                ): Boolean {
                    progressBar.gone()
                    return false
                }

                override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                ): Boolean {
                    progressBar.gone()
                    return false
                }
            })
            .apply(options)
            .into(this)
}

//endregion

//region setImageUrl
fun ImageView.setImageUrl(c: Context, imageUrl: String) {
    Glide.with(c)
            .load(imageUrl)
            .into(this)
}

fun ImageView.setImageUrl(c: Context, imageUrl: String, placeholderResourceId: Int) {

    val options = RequestOptions()
            .centerCrop()
            .error(placeholderResourceId)

    Glide.with(c)
            .load(imageUrl)
            .apply(options)
            .into(this)
}

fun ImageView.setImageUrl(c: Context, imageUrl: String, placeHolderDrawable: Drawable) {

    val options = RequestOptions()
            .centerCrop()
            .error(placeHolderDrawable)

    Glide.with(c)
            .load(imageUrl)
            .apply(options)
            .into(this)
}

fun ImageView.setImageUrl(c: Context, imageUrl: String, placeHolderResourceId: Int, errorResourceId: Int) {
    val options = RequestOptions()
            .centerCrop()
            .placeholder(placeHolderResourceId)
            .error(errorResourceId)

    Glide.with(c)
            .load(imageUrl)
            .apply(options)
            .into(this)
}

fun ImageView.setImageUrl(c: Context, imageUrl: String, placeHolderDrawable: Drawable, errorDrawable: Drawable) {
    val options = RequestOptions()
            .centerCrop()
            .placeholder(placeHolderDrawable)
            .error(errorDrawable)

    Glide.with(c)
            .load(imageUrl)
            .apply(options)
            .into(this)
}

fun ImageView.setImageUrl(context: Context, imageUrl: String, progressBar: ProgressBar) {
    progressBar.visible()
    Glide.with(context)
            .load(imageUrl)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                        e: GlideException?,
                        model: Any,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                ): Boolean {
                    progressBar.gone()
                    return false
                }

                override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                ): Boolean {
                    progressBar.gone()
                    return false
                }
            }).into(this)
}

fun ImageView.setImageUrl(c: Context, imageUrl: String, progressBar: ProgressBar, errorResourceId: Int) {
    val options = RequestOptions()
            .error(errorResourceId)

    progressBar.visible()
    Glide.with(c)
            .load(imageUrl)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                        e: GlideException?,
                        model: Any,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                ): Boolean {
                    progressBar.gone()
                    return false
                }

                override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                ): Boolean {
                    progressBar.gone()
                    return false
                }
            })
            .apply(options)
            .into(this)
}

fun ImageView.setImageUrl(c: Context, url: String, progressBar: ProgressBar, errorDrawable: Drawable) {
    val options = RequestOptions()
            .error(errorDrawable)
    progressBar.visible()
    Glide.with(c)
            .load(url)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                        e: GlideException?,
                        model: Any,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                ): Boolean {
                    progressBar.gone()
                    return false
                }

                override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                ): Boolean {
                    progressBar.gone()
                    return false
                }
            })
            .apply(options)
            .into(this)
}