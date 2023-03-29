package com.afaneca.marvelchallenge.data.remote.entity

import androidx.annotation.Keep
import com.afaneca.marvelchallenge.domain.model.CharacterContent
import com.google.gson.annotations.SerializedName

@Keep
data class CharacterContentEntity(
    @SerializedName("resourceURI")
    val resourceUri: String?,
    @SerializedName("name")
    val name: String?,
)

fun CharacterContentEntity.mapToDomain() = CharacterContent(resourceUri, name)