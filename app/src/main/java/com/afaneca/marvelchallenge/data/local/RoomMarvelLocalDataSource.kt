package com.afaneca.marvelchallenge.data.local

import com.afaneca.marvelchallenge.data.local.db.character.CharacterDao
import com.afaneca.marvelchallenge.data.local.db.character.CharacterDbEntity
import com.afaneca.marvelchallenge.data.local.db.content.CharacterContentDao
import com.afaneca.marvelchallenge.data.local.db.content.CharacterContentEntity
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

    override suspend fun getAllCharacterContent(
        characterId: Int,
        contentType: MarvelLocalDataSource.ContentType
    ): List<CharacterContentEntity> =
        characterContentDao.getCharacterContent(
            characterId, contentType.tag
        )

    override suspend fun insertAllCharacterContent(dataset: List<CharacterContentEntity>) {
        characterContentDao.insertAll(dataset)
    }

}