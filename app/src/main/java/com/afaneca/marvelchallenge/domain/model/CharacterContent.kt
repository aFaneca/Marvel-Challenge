package com.afaneca.marvelchallenge.domain.model

data class CharacterContent(
    val id: Int,
    val name: String? = null,
    val description: String? = null,
    val imgUrl: String? = null,
)