package com.afaneca.marvelchallenge.data.remote.entity

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CharacterThumbnailEntity(
    @SerializedName("path")
    val path: String,
    @SerializedName("extension")
    val extension: String,
)