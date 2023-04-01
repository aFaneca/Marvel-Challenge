package com.afaneca.marvelchallenge.ui.details

import com.afaneca.marvelchallenge.ui.model.CharacterContentUiModel

data class DetailsState(
    val id: Int? = null,
    val name: String? = null,
    val description: String? = null,
    val imgUrl: String? = null,
)

data class ComicsState(
    val isLoading: Boolean = false,
    val list: List<CharacterContentUiModel>? = null,
    val error: String? = null,
)

data class EventsState(
    val isLoading: Boolean = false,
    val list: List<CharacterContentUiModel>? = null,
    val error: String? = null,
)

data class StoriesState(
    val isLoading: Boolean = false,
    val list: List<CharacterContentUiModel>? = null,
    val error: String? = null,
)

data class SeriesState(
    val isLoading: Boolean = false,
    val list: List<CharacterContentUiModel>? = null,
    val error: String? = null,
)