package com.afaneca.marvelchallenge.ui.about

import com.afaneca.marvelchallenge.common.AppDispatchers
import com.afaneca.marvelchallenge.domain.model.TopSellingItem
import com.afaneca.marvelchallenge.domain.repository.TopSellingRepository
import com.afaneca.marvelchallenge.domain.usecase.GetTopSellingItemsUseCase
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
class AboutViewModelTest {

    @MockK(relaxed = true)
    private lateinit var topSellingRepository: TopSellingRepository

    private lateinit var viewModel: AboutViewModel

    private val testDispatcher = AppDispatchers(
        IO = UnconfinedTestDispatcher()
    )

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        MockKAnnotations.init(this)
    }

    @After
    fun destroy() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when is initiated, state propagates the retrieved data`() = runTest {
        val mockItems = getMockItemList()
        coEvery { topSellingRepository.getTopSellingItems() } returns mockItems
        val topSellingItemsUseCase = GetTopSellingItemsUseCase(topSellingRepository)
        viewModel = AboutViewModel(testDispatcher, topSellingItemsUseCase)

        advanceUntilIdle()
        with(viewModel.state.value) {
            Assert.assertTrue(items?.isNotEmpty() == true)
        }
    }

    private fun getMockItemList(): List<TopSellingItem> = listOf(
        mockk(relaxed = true),
        mockk(relaxed = true),
        mockk(relaxed = true),
        mockk(relaxed = true),
    )
}