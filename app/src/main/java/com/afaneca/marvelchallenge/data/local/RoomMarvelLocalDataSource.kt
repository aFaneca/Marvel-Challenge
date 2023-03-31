package com.afaneca.marvelchallenge.data.local

import com.afaneca.marvelchallenge.data.local.db.character.CharacterDao
import com.afaneca.marvelchallenge.data.local.db.character.CharacterDbEntity
import javax.inject.Inject

class RoomMarvelLocalDataSource @Inject constructor(
    private val characterDao: CharacterDao,
) : MarvelLocalDataSource {
    override suspend fun getAllCharacterResultsInPage(
        page: Int,
        searchQuery: String?
    ): List<CharacterDbEntity> =
        characterDao.getCharacters(page = page, searchQuery = searchQuery ?: "")

    override suspend fun insertAllCharacters(dataset: List<CharacterDbEntity>) {
        characterDao.insertAll(dataset)
    }
}