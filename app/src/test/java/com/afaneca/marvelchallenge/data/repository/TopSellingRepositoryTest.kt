package com.afaneca.marvelchallenge.data.repository

import com.afaneca.marvelchallenge.data.local.TopSellingLocalDataSource
import com.afaneca.marvelchallenge.domain.repository.TopSellingRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class TopSellingRepositoryTest {

    @MockK(relaxed = true)
    private lateinit var topSellingLocalDataSource: TopSellingLocalDataSource

    private lateinit var topSellingRepository: TopSellingRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        topSellingRepository = LiveTopSellingRepository(topSellingLocalDataSource)
    }

    @Test
    fun `getTopSellingItems should invoke local data source to get data`() {
        runBlocking {
            topSellingRepository.getTopSellingItems()
            verify(exactly = 1) { topSellingLocalDataSource.getTopSellingItems() }
        }
    }
}