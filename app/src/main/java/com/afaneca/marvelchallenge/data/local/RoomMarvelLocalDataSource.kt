package com.afaneca.marvelchallenge.data.local

import com.afaneca.marvelchallenge.data.local.db.character.CharacterContentDao
import com.afaneca.marvelchallenge.data.local.db.character.CharacterContentEntity
import com.afaneca.marvelchallenge.data.local.db.character.CharacterDao
import com.afaneca.marvelchallenge.data.local.db.character.CharacterDbEntity
import javax.inject.Inject

class RoomMarvelLocalDataSource @Inject constructor(
    private val characterDao: CharacterDao,
    private val characterContentDao: CharacterContentDao
) : MarvelLocalDataSource {
    override suspend fun getAllCharacterResultsInPage(
        page: Int,
        searchQuery: String?
    ): List<CharacterDbEntity> =
        characterDao.getCharacters(page = page, searchQuery = searchQuery ?: "")

    override suspend fun insertAllCharacters(dataset: List<CharacterDbEntity>) {
        characterDao.insertAll(dataset)
    }

    override suspend fun getAllCharacterComics(characterId: Int): List<CharacterContentEntity> =
        characterContentDao.getCharacterContent(
            characterId,
            MarvelLocalDataSource.ContentType.Comic.tag
        )

    override suspend fun insertAllCharacterComics(dataset: List<CharacterContentEntity>) {
        characterContentDao.insertAll(dataset)
    }
}