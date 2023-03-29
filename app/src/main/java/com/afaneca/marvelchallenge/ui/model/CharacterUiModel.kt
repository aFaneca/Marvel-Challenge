package com.afaneca.marvelchallenge.ui.model

import com.afaneca.marvelchallenge.domain.model.MarvelCharacter

data class CharacterUiModel(
    val id: Int,
    val name: String,
    val imgUrl: String?
)

