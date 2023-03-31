package com.afaneca.marvelchallenge.domain.usecase

import com.afaneca.marvelchallenge.domain.model.TopSellingItem
import com.afaneca.marvelchallenge.domain.repository.TopSellingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTopSellingItemsUseCase @Inject constructor(
    private val topSellingRepository: TopSellingRepository
) {
    suspend operator fun invoke(): Flow<List<TopSellingItem>> = flow {
        val items = topSellingRepository.getTopSellingItems()
        emit(items)
    }
}