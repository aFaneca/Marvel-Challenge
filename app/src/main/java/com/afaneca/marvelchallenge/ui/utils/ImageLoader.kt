package com.afaneca.marvelchallenge.ui.utils

import android.content.Context
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.afaneca.marvelchallenge.R
import com.bumptech.glide.Glide
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
        Glide.with(context)
            .load(url)
            .thumbnail(0.1f)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(placeholder)
            .into(view)
    }
}