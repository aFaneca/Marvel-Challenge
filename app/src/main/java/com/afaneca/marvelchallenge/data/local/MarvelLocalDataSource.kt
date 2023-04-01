package com.afaneca.marvelchallenge.data.local

import com.afaneca.marvelchallenge.data.local.db.character.CharacterDbEntity
import com.afaneca.marvelchallenge.data.local.db.content.CharacterContentEntity

interface MarvelLocalDataSource {
    sealed class ContentType(val tag: String) {
        object Comic : ContentType("comic")
        object Event : ContentType("event")
        object Story : ContentType("story")
        object Series : ContentType("series")
    }

    //region characters
    suspend fun getAllCharacterResultsInPage(
        page: Int,
        searchQuery: String? = ""
    ): List<CharacterDbEntity>

    suspend fun insertAllCharacters(dataset: List<CharacterDbEntity>)

    //endregion
    //region content
    suspend fun getAllCharacterContent(
        characterId: Int,
        contentType: ContentType
    ): List<CharacterContentEntity>

    suspend fun insertAllCharacterContent(dataset: List<CharacterContentEntity>)
    //endregion
}