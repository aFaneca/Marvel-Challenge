package com.afaneca.marvelchallenge.data.remote.entity

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GetCharactersResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("status")
    val status: String?,
    @SerializedName("copyright")
    val copyright: String?,
    @SerializedName("attributionText")
    val attributionText: String?,
    @SerializedName("data")
    val data: GetCharacterResponseData,
)

@Keep
data class GetCharacterResponseData(
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("total")
    val total: Int,
    @SerializedName("count")
    val count: Int,
    @SerializedName("results")
    val results: List<CharacterEntity>
)