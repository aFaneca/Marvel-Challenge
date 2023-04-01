package com.afaneca.marvelchallenge.data.remote.entity

import androidx.annotation.Keep
import com.afaneca.marvelchallenge.domain.model.CharacterContent
import com.google.gson.annotations.SerializedName

@Keep
data class CharacterContentEntity(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("thumbnail")
    val thumbnail: CharacterThumbnailEntity?,
)

fun CharacterContentEntity.mapToDomain() = CharacterContent(
    id = id,
    name = title,
    description = description,
    imgUrl = "${thumbnail?.path}.${thumbnail?.extension}"
)