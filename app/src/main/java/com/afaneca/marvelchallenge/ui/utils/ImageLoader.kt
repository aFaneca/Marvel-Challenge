package com.afaneca.marvelchallenge.ui.utils

import android.content.Context
import android.widget.ImageView
import com.afaneca.marvelchallenge.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

object ImageLoader {
    /**
     * Loads image from [url] using external library and then inflates it into the a [view]
     */
    fun loadImageIntoView(context: Context, url: String, view: ImageView) {
        Glide.with(context)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.ic_dashboard_black_24dp) // TODO: set placeholder
            .into(view)
    }
}