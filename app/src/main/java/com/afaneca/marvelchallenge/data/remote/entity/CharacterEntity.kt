package com.afaneca.marvelchallenge.data.remote.entity

import androidx.annotation.Keep
import com.afaneca.marvelchallenge.domain.model.Character
import com.afaneca.marvelchallenge.domain.model.CharacterPage
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
    val comics: List<CharacterContentEntity>,
    @SerializedName("series")
    val series: List<CharacterContentEntity>,
    @SerializedName("stories")
    val stories: List<CharacterContentEntity>,
    @SerializedName("events")
    val events: List<CharacterContentEntity>
)

@Keep
data class CharacterThumbnailEntity(
    @SerializedName("path")
    val path: String,
    @SerializedName("extension")
    val extension: String,
)

fun CharacterEntity.mapToDomain() = Character(
    id = id,
    name = name,
    description = description,
    thumbnailUrl = "${thumbnail?.path}.${thumbnail?.extension}",
    comics = comics.map { comic -> comic.mapToDomain() },
    series = series.map { series -> series.mapToDomain() },
    stories = stories.map { stories -> stories.mapToDomain() },
    events = events.map { events -> events.mapToDomain() },
)
