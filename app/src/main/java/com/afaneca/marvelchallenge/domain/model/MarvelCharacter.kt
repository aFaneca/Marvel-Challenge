package com.afaneca.marvelchallenge.domain.model

data class MarvelCharacter(
    val id: Int,
    val name: String,
    val description: String?,
    val thumbnailUrl: String?,
    val comics: List<CharacterContent>?,
    val series: List<CharacterContent>?,
    val stories: List<CharacterContent>?,
    val events: List<CharacterContent>?,
)