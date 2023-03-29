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
    @SerializedName("comics")
    val comics: CharacterContentEntity,
    @SerializedName("series")
    val series: CharacterContentEntity,
    @SerializedName("stories")
    val stories: CharacterContentEntity,
    @SerializedName("events")
    val events: CharacterContentEntity
)

@Keep
data class CharacterThumbnailEntity(
    @SerializedName("path")
    val path: String,
    @SerializedName("extension")
    val extension: String,
)

fun CharacterEntity.mapToDomain() = MarvelCharacter(
    id = id,
    name = name,
    description = description,
    thumbnailUrl = "${thumbnail?.path}.${thumbnail?.extension}",
    comics = comics.items.map { comic -> comic.mapToDomain() },
    series = series.items.map { series -> series.mapToDomain() },
    stories = stories.items.map { stories -> stories.mapToDomain() },
    events = events.items.map { events -> events.mapToDomain() },
)
