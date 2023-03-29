package com.afaneca.marvelchallenge.data.remote.entity

import androidx.annotation.Keep
import com.afaneca.marvelchallenge.domain.model.CharacterContent
import com.google.gson.annotations.SerializedName

@Keep
data class CharacterContentEntity(
    @SerializedName("available")
    val available: Int,
    @SerializedName("items")
    val items: List<CharacterContentItemEntity>
)

@Keep
data class CharacterContentItemEntity(
    @SerializedName("resourceURI")
    val resourceUri: String?,
    @SerializedName("name")
    val name: String?,
)

fun CharacterContentItemEntity.mapToDomain() = CharacterContent(resourceUri, name)