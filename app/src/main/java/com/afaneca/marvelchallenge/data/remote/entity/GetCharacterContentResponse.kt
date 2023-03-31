package com.afaneca.marvelchallenge.data.remote.entity

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GetCharacterContentResponse(
    @SerializedName("data")
    val data: GetCharacterContentResponseData,
)

@Keep
data class GetCharacterContentResponseData(
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("total")
    val total: Int,
    @SerializedName("count")
    val count: Int,
    @SerializedName("results")
    val results: List<CharacterContentEntity>
)