package com.afaneca.marvelchallenge.data.local

import com.afaneca.marvelchallenge.data.local.db.character.CharacterDbEntity

interface MarvelLocalDataSource {
    //region characters
    suspend fun getAllCharacterResultsInPage(
        page: Int,
        searchQuery: String? = ""
    ): List<CharacterDbEntity>

    suspend fun insertAllCharacters(dataset: List<CharacterDbEntity>)
    //endregion
}