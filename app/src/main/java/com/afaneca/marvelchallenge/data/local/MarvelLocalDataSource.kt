package com.afaneca.marvelchallenge.data.local

import com.afaneca.marvelchallenge.data.local.db.character.CharacterContentEntity
import com.afaneca.marvelchallenge.data.local.db.character.CharacterDbEntity

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
    //region comics
    suspend fun getAllCharacterComics(
        characterId: Int
    ): List<CharacterContentEntity>

    suspend fun insertAllCharacterComics(dataset: List<CharacterContentEntity>)
    //endregion
}