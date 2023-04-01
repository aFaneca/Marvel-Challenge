package com.afaneca.marvelchallenge.domain.model

data class CharacterPage(
    val list: List<MarvelCharacter>,
    val hasReachedPaginationEnd: Boolean
)