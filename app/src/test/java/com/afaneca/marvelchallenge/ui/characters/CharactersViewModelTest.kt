package com.afaneca.marvelchallenge.ui.characters

import com.afaneca.marvelchallenge.common.AppDispatchers
import com.afaneca.marvelchallenge.common.Resource
import com.afaneca.marvelchallenge.domain.model.CharacterPage
import com.afaneca.marvelchallenge.domain.model.MarvelCharacter
import com.afaneca.marvelchallenge.domain.repository.CharacterRepository
import com.afaneca.marvelchallenge.domain.usecase.GetCharactersUseCase
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
class CharactersViewModelTest {

    @MockK(relaxed = true)
    private lateinit var charactersRepository: CharacterRepository

    private lateinit var viewModel: CharactersViewModel

    private val testDispatcher = AppDispatchers(
        IO = UnconfinedTestDispatcher()
    )

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        MockKAnnotations.init(this)

        val useCase = GetCharactersUseCase(charactersRepository)
        viewModel = CharactersViewModel(testDispatcher, useCase)
    }

    @After
    fun destroy() {
        Dispatchers.resetMain()
    }

    @Test
    fun `invoking requestNextPage increments page number`() = runTest {
        setupRepositoryMocks(isNetworkAvailable = false, hasLocalCache = true)
        advanceUntilIdle()
        viewModel.requestNextPage()
        Assert.assertEquals(1, viewModel.state.value.page)
    }

    @Test
    fun `when getCharactersUseCase is invoked, first state change is loading to true`() = runTest {
        runCurrent()
        Assert.assertTrue(viewModel.state.value.isLoading)
    }

    @Test
    fun `when getCharactersUseCase is invoked without internet and no cached items, error is triggered`() =
        runTest {
            setupRepositoryMocks(isNetworkAvailable = false, hasLocalCache = false)
            advanceUntilIdle()
            Assert.assertTrue(viewModel.state.value.error?.isNotEmpty() == true)
        }

    @Test
    fun `when getCharactersUseCase is invoked without internet and has cached items, those are returned`() =
        runTest {
            setupRepositoryMocks(isNetworkAvailable = false, hasLocalCache = true)

            advanceUntilIdle()
            Assert.assertTrue(viewModel.state.value.characterList?.isNotEmpty() == true)
        }

    @Test
    fun `when getCharactersUseCase is invoked with internet and has cached items, the list is returned`() =
        runTest {
            setupRepositoryMocks(isNetworkAvailable = true, hasLocalCache = true)

            advanceUntilIdle()
            Assert.assertTrue(viewModel.state.value.characterList?.isNotEmpty() == true)
        }

    @Test
    fun `when search input is submitted, state list is reset`() = runTest {
        setupRepositoryMocks(isNetworkAvailable = true, hasLocalCache = true)

        // After fetching unfiltered results, the list is no longer empty
        advanceUntilIdle()
        Assert.assertTrue(viewModel.state.value.characterList?.isNotEmpty() == true)

        val searchInput = "some search query"
        viewModel.onSearchInputSubmitted(searchInput)
        runCurrent()

        // After search query is inputted, the list state is reset
        with(viewModel.state.value) {
            Assert.assertEquals(searchQuery, searchInput)
            Assert.assertTrue(viewModel.state.value.characterList == null)
        }
    }

    private fun setupRepositoryMocks(isNetworkAvailable: Boolean, hasLocalCache: Boolean) {
        val mockCharactersList = getMockCharactersList()
        coEvery { charactersRepository.getCharactersFromRemote(any()) } returns
                if (isNetworkAvailable) Resource.Success(
                    data = CharacterPage(
                        mockCharactersList,
                        false
                    )
                )
                else Resource.Error("Some network error")

        coEvery {
            charactersRepository.getCharactersFromLocalCache(any(), any())
        } returns CharacterPage(
            if (hasLocalCache) mockCharactersList else emptyList(), false
        )
    }

    private fun getMockCharactersList(): List<MarvelCharacter> = listOf(
        mockk(relaxed = true),
        mockk(relaxed = true),
        mockk(relaxed = true),
        mockk(relaxed = true),
    )
}