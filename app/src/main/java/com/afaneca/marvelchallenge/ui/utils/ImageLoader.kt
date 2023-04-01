package com.afaneca.marvelchallenge.ui.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.afaneca.marvelchallenge.R
import com.afaneca.marvelchallenge.ui.normalizeUrlToHttps
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy

object ImageLoader {
    /**
     * Loads image from [url] using external library and then inflates it into the a [view]
     */
    fun loadImageIntoView(
        context: Context,
        url: String,
        view: ImageView,
        @DrawableRes placeholder: Int = R.drawable.ic_icon
    ) {
        GlideApp.with(context)
            .load(url.normalizeUrlToHttps())
            .thumbnail(getThumbnailRequestBuilder(context))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(placeholder)
            .into(view)
    }

    fun preLoadImageIntoView(
        requestBuilder: RequestBuilder<Drawable>,
        url: String?,
    ): RequestBuilder<Drawable> {
        return requestBuilder.load(url?.normalizeUrlToHttps())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
    }

    fun getRequestBuilder(context: Context): RequestBuilder<Drawable> {
        return GlideApp.with(context).asDrawable()
    }

    private fun getThumbnailRequestBuilder(context: Context): RequestBuilder<Drawable> {
        return GlideApp.with(context).asDrawable().sizeMultiplier(0.1f)
    }
}