package com.afaneca.marvelchallenge.data.repository

import com.afaneca.marvelchallenge.common.Constants.DEFAULT_PAGE_SIZE
import com.afaneca.marvelchallenge.common.Resource
import com.afaneca.marvelchallenge.data.local.MarvelLocalDataSource
import com.afaneca.marvelchallenge.data.local.db.character.CharacterDbEntity
import com.afaneca.marvelchallenge.data.local.db.content.CharacterContentEntity
import com.afaneca.marvelchallenge.data.remote.MarvelRemoteDataSource
import com.afaneca.marvelchallenge.data.remote.entity.mapToDomain
import com.afaneca.marvelchallenge.domain.model.CharacterContent
import com.afaneca.marvelchallenge.domain.model.CharacterPage
import com.afaneca.marvelchallenge.domain.model.MarvelCharacter
import com.afaneca.marvelchallenge.domain.repository.CharacterRepository
import javax.inject.Inject

class LiveCharacterRepository @Inject constructor(
    private val localDataSource: MarvelLocalDataSource,
    private val remoteDataSource: MarvelRemoteDataSource,
) : CharacterRepository {

    //region characters
    override suspend fun getCharactersFromRemote(
        page: Int,
        searchQuery: String?
    ): Resource<CharacterPage> {
        return try {
            val itemsOffset = page * DEFAULT_PAGE_SIZE
            val response =
                remoteDataSource.getCharacters(offset = itemsOffset, searchQuery = searchQuery)

            response.body()?.data?.let { data ->
                // nr of total items
                val totalItems = data.total
                // nr of items in page
                val pageCount = data.count
                // Check if has reached last page
                val hasReachedPaginationEnd = totalItems <= itemsOffset + pageCount

                val pageList = data.results.map { item -> item.mapToDomain() }
                Resource.Success(
                    CharacterPage(pageList, hasReachedPaginationEnd)
                )
            } ?: run {
                Resource.Error("")
            }


        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "")
        }
    }

    override suspend fun getCharactersFromLocalCache(
        page: Int,
        searchQuery: String?
    ): CharacterPage {
        val results = localDataSource.getAllCharacterResultsInPage(page, searchQuery)
        return CharacterPage(
            list = results.map { MarvelCharacter(it.id, it.name, it.description, it.thumbnailUrl) },
            hasReachedPaginationEnd = false
        )
    }

    override suspend fun insertCharactersIntoLocalCache(
        list: List<MarvelCharacter>,
        query: String?,
        page: Int
    ) {
        localDataSource.insertAllCharacters(list.map {
            CharacterDbEntity(
                it.id,
                it.name,
                it.description,
                it.thumbnailUrl,
                page,
                query ?: ""
            )
        })
    }
    //endregion

    //region comics
    override suspend fun getCharacterComicsFromRemote(characterId: Int): Resource<List<CharacterContent>> {
        return try {
            val response = remoteDataSource.getCharacterComics(characterId)
            response.body()?.data?.let {
                Resource.Success(it.results.map { item -> item.mapToDomain() })
            } ?: run {
                Resource.Error("")
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "")
        }
    }

    override suspend fun getCharacterComicsFromLocalCache(characterId: Int): List<CharacterContent> {
        val results = localDataSource.getAllCharacterContent(
            characterId,
            MarvelLocalDataSource.ContentType.Comic
        )
        return results.map {
            CharacterContent(
                it.characterId,
                it.name,
                it.description,
                it.thumbnailUrl
            )
        }
    }

    override suspend fun insertCharacterComicsIntoLocalCache(
        characterId: Int,
        list: List<CharacterContent>
    ) {
        localDataSource.insertAllCharacterContent(list.map {
            CharacterContentEntity(
                it.name ?: "",
                it.imgUrl,
                it.description,
                MarvelLocalDataSource.ContentType.Comic.tag,
                characterId
            )
        })
    }
    //endregion

    //region events
    override suspend fun getCharacterEventsFromRemote(characterId: Int): Resource<List<CharacterContent>> {
        return try {
            val response = remoteDataSource.getCharacterEvents(characterId)
            response.body()?.data?.let {
                Resource.Success(it.results.map { item -> item.mapToDomain() })
            } ?: run {
                Resource.Error("")
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "")
        }
    }

    override suspend fun getCharacterEventsFromLocalCache(characterId: Int): List<CharacterContent> {
        val results = localDataSource.getAllCharacterContent(
            characterId,
            MarvelLocalDataSource.ContentType.Event
        )
        return results.map {
            CharacterContent(
                it.characterId,
                it.name,
                it.description,
                it.thumbnailUrl
            )
        }
    }

    override suspend fun insertCharacterEventsIntoLocalCache(
        characterId: Int,
        list: List<CharacterContent>
    ) {
        localDataSource.insertAllCharacterContent(list.map {
            CharacterContentEntity(
                it.name ?: "",
                it.imgUrl,
                it.description,
                MarvelLocalDataSource.ContentType.Event.tag,
                characterId
            )
        })
    }
    //endregion

    //region stories
    override suspend fun getCharacterStoriesFromRemote(characterId: Int): Resource<List<CharacterContent>> {
        return try {
            val response = remoteDataSource.getCharacterStories(characterId)
            response.body()?.data?.let {
                Resource.Success(it.results.map { item -> item.mapToDomain() })
            } ?: run {
                Resource.Error("")
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "")
        }
    }

    override suspend fun getCharacterStoriesFromLocalCache(characterId: Int): List<CharacterContent> {
        val results = localDataSource.getAllCharacterContent(
            characterId,
            MarvelLocalDataSource.ContentType.Story
        )
        return results.map {
            CharacterContent(
                it.characterId,
                it.name,
                it.description,
                it.thumbnailUrl
            )
        }
    }

    override suspend fun insertCharacterStoriesIntoLocalCache(
        characterId: Int,
        list: List<CharacterContent>
    ) {
        localDataSource.insertAllCharacterContent(list.map {
            CharacterContentEntity(
                it.name ?: "",
                it.imgUrl,
                it.description,
                MarvelLocalDataSource.ContentType.Story.tag,
                characterId
            )
        })
    }
    //endregion

    //region series
    override suspend fun getCharacterSeriesFromRemote(characterId: Int): Resource<List<CharacterContent>> {
        return try {
            val response = remoteDataSource.getCharacterSeries(characterId)
            response.body()?.data?.let {
                Resource.Success(it.results.map { item -> item.mapToDomain() })
            } ?: run {
                Resource.Error("")
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "")
        }
    }

    override suspend fun getCharacterSeriesFromLocalCache(characterId: Int): List<CharacterContent> {
        val results = localDataSource.getAllCharacterContent(
            characterId,
            MarvelLocalDataSource.ContentType.Series
        )
        return results.map {
            CharacterContent(
                it.characterId,
                it.name,
                it.description,
                it.thumbnailUrl
            )
        }
    }

    override suspend fun insertCharacterSeriesIntoLocalCache(
        characterId: Int,
        list: List<CharacterContent>
    ) {
        localDataSource.insertAllCharacterContent(list.map {
            CharacterContentEntity(
                it.name ?: "",
                it.imgUrl,
                it.description,
                MarvelLocalDataSource.ContentType.Series.tag,
                characterId
            )
        })
    }
    //endregion
}