package com.afaneca.marvelchallenge.domain.model

data class MarvelCharacter(
    val id: Int,
    val name: String,
    val description: String?,
    val thumbnailUrl: String?,
)