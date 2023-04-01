package com.afaneca.marvelchallenge.ui.details

import com.afaneca.marvelchallenge.common.AppDispatchers
import com.afaneca.marvelchallenge.common.Resource
import com.afaneca.marvelchallenge.domain.model.CharacterContent
import com.afaneca.marvelchallenge.domain.repository.CharacterRepository
import com.afaneca.marvelchallenge.domain.usecase.*
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailsViewModelTest {

    @MockK(relaxed = true)
    private lateinit var charactersRepository: CharacterRepository

    private lateinit var viewModel: DetailsViewModel

    private val testDispatcher = AppDispatchers(
        IO = UnconfinedTestDispatcher()
    )

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        MockKAnnotations.init(this)

        val comicsUseCase = GetCharacterComicsUseCase(charactersRepository)
        val eventsUseCase = GetCharacterEventsUseCase(charactersRepository)
        val storiesUseCase = GetCharacterStoriesUseCase(charactersRepository)
        val seriesUseCase = GetCharacterSeriesUseCase(charactersRepository)
        viewModel = DetailsViewModel(
            testDispatcher,
            comicsUseCase,
            eventsUseCase,
            storiesUseCase,
            seriesUseCase
        )
    }


    @After
    fun destroy() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when is initiated, state propagates that data`() = runTest {
        viewModel.init(1, "a", "b", "c")
        runCurrent()
        with(viewModel.state.value) {
            Assert.assertEquals(id, 1)
            Assert.assertEquals(name, "a")
            Assert.assertEquals(description, "b")
            Assert.assertEquals(imgUrl, "c")
        }
    }

    //region comics
    @Test
    fun `when getComics is invoked, state changes to loading`() = runTest {
        viewModel.init(1, "", "", "")
        advanceUntilIdle()
        Assert.assertTrue(viewModel.comicsState.value.isLoading)
    }

    @Test
    fun `when getCharacterComicsUseCase is invoked without internet and no cached items, error is triggered`() =
        runTest {
            viewModel.init(1, "", "", "")
            setupRepositoryMocksForComics(isNetworkAvailable = false, hasLocalCache = false)
            advanceUntilIdle()
            Assert.assertTrue(viewModel.comicsState.value.error?.isNotEmpty() == true)
        }

    @Test
    fun `when getCharacterComicsUseCase is invoked without internet and has cached items, those are returned`() =
        runTest {
            viewModel.init(1, "", "", "")
            setupRepositoryMocksForComics(isNetworkAvailable = false, hasLocalCache = true)
            advanceUntilIdle()
            Assert.assertTrue(viewModel.comicsState.value.list?.isNotEmpty() == true)
        }

    @Test
    fun `when getCharacterComicsUseCase is invoked with internet and has cached items, the list is returned`() =
        runTest {
            viewModel.init(1, "", "", "")
            setupRepositoryMocksForComics(isNetworkAvailable = true, hasLocalCache = true)
            advanceUntilIdle()
            Assert.assertTrue(viewModel.comicsState.value.list?.isNotEmpty() == true)
        }

    //endregion
    //region events
    @Test
    fun `when getEvents is invoked, state changes to loading`() = runTest {
        viewModel.init(1, "", "", "")
        advanceUntilIdle()
        Assert.assertTrue(viewModel.eventsState.value.isLoading)
    }

    @Test
    fun `when getCharacterEventsUseCase is invoked without internet and no cached items, error is triggered`() =
        runTest {
            viewModel.init(1, "", "", "")
            setupRepositoryMocksForEvents(isNetworkAvailable = false, hasLocalCache = false)
            advanceUntilIdle()
            Assert.assertTrue(viewModel.eventsState.value.error?.isNotEmpty() == true)
        }

    @Test
    fun `when getCharacterEventsUseCase is invoked without internet and has cached items, those are returned`() =
        runTest {
            viewModel.init(1, "", "", "")
            setupRepositoryMocksForEvents(isNetworkAvailable = false, hasLocalCache = true)
            advanceUntilIdle()
            Assert.assertTrue(viewModel.eventsState.value.list?.isNotEmpty() == true)
        }

    @Test
    fun `when getCharacterEventsUseCase is invoked with internet and has cached items, the list is returned`() =
        runTest {
            viewModel.init(1, "", "", "")
            setupRepositoryMocksForEvents(isNetworkAvailable = true, hasLocalCache = true)
            advanceUntilIdle()
            Assert.assertTrue(viewModel.eventsState.value.list?.isNotEmpty() == true)
        }

    //endregion
    //region stories
    @Test
    fun `when getStories is invoked, state changes to loading`() = runTest {
        viewModel.init(1, "", "", "")
        advanceUntilIdle()
        Assert.assertTrue(viewModel.storiesState.value.isLoading)
    }

    @Test
    fun `when getCharacterStoriesUseCase is invoked without internet and no cached items, error is triggered`() =
        runTest {
            viewModel.init(1, "", "", "")
            setupRepositoryMocksForStories(isNetworkAvailable = false, hasLocalCache = false)
            advanceUntilIdle()
            Assert.assertTrue(viewModel.storiesState.value.error?.isNotEmpty() == true)
        }

    @Test
    fun `when getCharacterStoriesUseCase is invoked without internet and has cached items, those are returned`() =
        runTest {
            viewModel.init(1, "", "", "")
            setupRepositoryMocksForStories(isNetworkAvailable = false, hasLocalCache = true)
            advanceUntilIdle()
            Assert.assertTrue(viewModel.storiesState.value.list?.isNotEmpty() == true)
        }

    @Test
    fun `when getCharacterStoriesUseCase is invoked with internet and has cached items, the list is returned`() =
        runTest {
            viewModel.init(1, "", "", "")
            setupRepositoryMocksForStories(isNetworkAvailable = true, hasLocalCache = true)
            advanceUntilIdle()
            Assert.assertTrue(viewModel.storiesState.value.list?.isNotEmpty() == true)
        }

    //endregion
    //region series
    @Test
    fun `when getSeries is invoked, state changes to loading`() = runTest {
        viewModel.init(1, "", "", "")
        advanceUntilIdle()
        Assert.assertTrue(viewModel.seriesState.value.isLoading)
    }

    @Test
    fun `when getCharacterSeriesUseCase is invoked without internet and no cached items, error is triggered`() =
        runTest {
            viewModel.init(1, "", "", "")
            setupRepositoryMocksForSeries(isNetworkAvailable = false, hasLocalCache = false)
            advanceUntilIdle()
            Assert.assertTrue(viewModel.seriesState.value.error?.isNotEmpty() == true)
        }

    @Test
    fun `when getCharacterSeriesUseCase is invoked without internet and has cached items, those are returned`() =
        runTest {
            viewModel.init(1, "", "", "")
            setupRepositoryMocksForSeries(isNetworkAvailable = false, hasLocalCache = true)
            advanceUntilIdle()
            Assert.assertTrue(viewModel.seriesState.value.list?.isNotEmpty() == true)
        }

    @Test
    fun `when getCharacterSeriesUseCase is invoked with internet and has cached items, the list is returned`() =
        runTest {
            viewModel.init(1, "", "", "")
            setupRepositoryMocksForSeries(isNetworkAvailable = true, hasLocalCache = true)
            advanceUntilIdle()
            Assert.assertTrue(viewModel.seriesState.value.list?.isNotEmpty() == true)
        }
    //endregion

    private fun setupRepositoryMocksForComics(isNetworkAvailable: Boolean, hasLocalCache: Boolean) {
        val mockContent = getMockCharacterContentList()
        coEvery { charactersRepository.getCharacterComicsFromRemote(any()) } returns
                if (isNetworkAvailable) Resource.Success(data = mockContent)
                else Resource.Error("Some network error")

        coEvery { charactersRepository.getCharacterComicsFromLocalCache(any()) } returns
                if (hasLocalCache) mockContent else emptyList()
    }

    private fun setupRepositoryMocksForEvents(isNetworkAvailable: Boolean, hasLocalCache: Boolean) {
        val mockContent = getMockCharacterContentList()
        coEvery { charactersRepository.getCharacterEventsFromRemote(any()) } returns
                if (isNetworkAvailable) Resource.Success(data = mockContent)
                else Resource.Error("Some network error")

        coEvery { charactersRepository.getCharacterEventsFromLocalCache(any()) } returns
                if (hasLocalCache) mockContent else emptyList()
    }

    private fun setupRepositoryMocksForStories(
        isNetworkAvailable: Boolean,
        hasLocalCache: Boolean
    ) {
        val mockContent = getMockCharacterContentList()
        coEvery { charactersRepository.getCharacterStoriesFromRemote(any()) } returns
                if (isNetworkAvailable) Resource.Success(data = mockContent)
                else Resource.Error("Some network error")

        coEvery { charactersRepository.getCharacterStoriesFromLocalCache(any()) } returns
                if (hasLocalCache) mockContent else emptyList()
    }

    private fun setupRepositoryMocksForSeries(isNetworkAvailable: Boolean, hasLocalCache: Boolean) {
        val mockContent = getMockCharacterContentList()
        coEvery { charactersRepository.getCharacterSeriesFromRemote(any()) } returns
                if (isNetworkAvailable) Resource.Success(data = mockContent)
                else Resource.Error("Some network error")

        coEvery { charactersRepository.getCharacterSeriesFromLocalCache(any()) } returns
                if (hasLocalCache) mockContent else emptyList()
    }

    private fun getMockCharacterContentList(): List<CharacterContent> = listOf(
        mockk(relaxed = true),
        mockk(relaxed = true),
        mockk(relaxed = true),
        mockk(relaxed = true),
    )
}