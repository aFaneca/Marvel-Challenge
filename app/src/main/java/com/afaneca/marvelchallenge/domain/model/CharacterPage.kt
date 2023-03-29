package com.afaneca.marvelchallenge.domain.model

data class CharacterPage(
    val list: List<Character>,
    val hasReachedPaginationEnd: Boolean
)