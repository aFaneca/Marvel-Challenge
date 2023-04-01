package com.afaneca.marvelchallenge.ui.characters

import com.afaneca.marvelchallenge.ui.model.CharacterUiModel

data class CharacterListState(
    val isLoading: Boolean = false,
    val isLoadingFromPagination: Boolean = false,
    val characterList: List<CharacterUiModel>? = null,
    val page: Int = 0,
    val error: String? = null,
    val searchQuery: String? = null,
    val hasReachedPaginationEnd: Boolean = false,
)