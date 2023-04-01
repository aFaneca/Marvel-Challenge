package com.afaneca.marvelchallenge.ui.utils

import android.content.Context
import android.util.Log
import com.afaneca.marvelchallenge.BuildConfig
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class GlideAppModule : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
        if (BuildConfig.DEBUG)
            builder.setLogLevel(Log.VERBOSE)
    }
}