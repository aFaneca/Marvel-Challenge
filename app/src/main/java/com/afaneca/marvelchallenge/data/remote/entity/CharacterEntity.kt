package com.afaneca.marvelchallenge.data.remote.entity

import androidx.annotation.Keep
import com.afaneca.marvelchallenge.domain.model.MarvelCharacter
import com.google.gson.annotations.SerializedName

@Keep
data class CharacterEntity(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("thumbnail")
    val thumbnail: CharacterThumbnailEntity?,
)

fun CharacterEntity.mapToDomain() = MarvelCharacter(
    id = id,
    name = name,
    description = description,
    thumbnailUrl = "${thumbnail?.path}.${thumbnail?.extension}"
)
