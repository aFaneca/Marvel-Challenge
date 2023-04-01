package com.afaneca.marvelchallenge.data.repository

import com.afaneca.marvelchallenge.data.local.MarvelLocalDataSource
import com.afaneca.marvelchallenge.data.remote.MarvelRemoteDataSource
import com.afaneca.marvelchallenge.domain.repository.CharacterRepository
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class CharacterRepositoryTest {

    @MockK(relaxed = true)
    private lateinit var localDataSource: MarvelLocalDataSource

    @MockK(relaxed = true)
    private lateinit var remoteDataSource: MarvelRemoteDataSource

    private lateinit var characterRepository: CharacterRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        characterRepository = LiveCharacterRepository(localDataSource, remoteDataSource)
    }

    //region characters
    @Test
    fun `getCharactersFromRemote should call remote data source to get data`() {
        runBlocking {
            characterRepository.getCharactersFromRemote(0, "")
            coVerify(exactly = 1) { remoteDataSource.getCharacters(any(), any()) }
        }
    }

    @Test
    fun `getCharactersFromLocalCache should call local data source to get data`() {
        runBlocking {
            characterRepository.getCharactersFromLocalCache(0, "")
            coVerify(exactly = 1) { localDataSource.getAllCharacterResultsInPage(any(), any()) }
        }
    }

    @Test
    fun `insertCharactersIntoLocalCache should call local data source to insert data`() {
        runBlocking {
            characterRepository.insertCharactersIntoLocalCache(emptyList(), "", 0)
            coVerify(exactly = 1) { localDataSource.insertAllCharacters(any()) }
        }
    }

    //endregion
    //region comics
    @Test
    fun `getCharacterComicsFromRemote should call remote data source to get data`() {
        runBlocking {
            characterRepository.getCharacterComicsFromRemote(0)
            coVerify(exactly = 1) { remoteDataSource.getCharacterComics(any()) }
        }
    }

    @Test
    fun `getCharacterComicsFromLocalCache should call local data source to get data`() {
        runBlocking {
            characterRepository.getCharacterComicsFromLocalCache(0)
            coVerify(exactly = 1) { localDataSource.getAllCharacterContent(any(), any()) }
        }
    }

    @Test
    fun `insertCharacterComicsIntoLocalCache should call local data source to insert data`() {
        runBlocking {
            characterRepository.insertCharacterComicsIntoLocalCache(0, emptyList())
            coVerify(exactly = 1) { localDataSource.insertAllCharacterContent(any()) }
        }
    }

    //endregion
    //region events
    @Test
    fun `getCharacterEventsFromRemote should call remote data source to get data`() {
        runBlocking {
            characterRepository.getCharacterEventsFromRemote(0)
            coVerify(exactly = 1) { remoteDataSource.getCharacterEvents(any()) }
        }
    }

    @Test
    fun `getCharacterEventsFromLocalCache should call local data source to get data`() {
        runBlocking {
            characterRepository.getCharacterEventsFromLocalCache(0)
            coVerify(exactly = 1) { localDataSource.getAllCharacterContent(any(), any()) }
        }
    }

    @Test
    fun `insertCharacterEventsIntoLocalCache should call local data source to insert data`() {
        runBlocking {
            characterRepository.insertCharacterEventsIntoLocalCache(0, emptyList())
            coVerify(exactly = 1) { localDataSource.insertAllCharacterContent(any()) }
        }
    }

    //endregion
    //region stories
    @Test
    fun `getCharacterStoriesFromRemote should call remote data source to get data`() {
        runBlocking {
            characterRepository.getCharacterStoriesFromRemote(0)
            coVerify(exactly = 1) { remoteDataSource.getCharacterStories(any()) }
        }
    }

    @Test
    fun `getCharacterStoriesFromLocalCache should call local data source to get data`() {
        runBlocking {
            characterRepository.getCharacterStoriesFromLocalCache(0)
            coVerify(exactly = 1) { localDataSource.getAllCharacterContent(any(), any()) }
        }
    }

    @Test
    fun `insertCharacterStoriesIntoLocalCache should call local data source to insert data`() {
        runBlocking {
            characterRepository.insertCharacterStoriesIntoLocalCache(0, emptyList())
            coVerify(exactly = 1) { localDataSource.insertAllCharacterContent(any()) }
        }
    }

    //endregion
    //region series
    @Test
    fun `getCharacterSeriesFromRemote should call remote data source to get data`() {
        runBlocking {
            characterRepository.getCharacterSeriesFromRemote(0)
            coVerify(exactly = 1) { remoteDataSource.getCharacterSeries(any()) }
        }
    }

    @Test
    fun `getCharacterSeriesFromLocalCache should call local data source to get data`() {
        runBlocking {
            characterRepository.getCharacterSeriesFromLocalCache(0)
            coVerify(exactly = 1) { localDataSource.getAllCharacterContent(any(), any()) }
        }
    }

    @Test
    fun `insertCharacterSeriesIntoLocalCache should call local data source to insert data`() {
        runBlocking {
            characterRepository.insertCharacterSeriesIntoLocalCache(0, emptyList())
            coVerify(exactly = 1) { localDataSource.insertAllCharacterContent(any()) }
        }
    }
    //endregion
}