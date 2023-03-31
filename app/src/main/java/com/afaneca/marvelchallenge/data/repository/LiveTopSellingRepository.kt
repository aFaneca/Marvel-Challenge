package com.afaneca.marvelchallenge.data.repository

import com.afaneca.marvelchallenge.data.local.TopSellingLocalDataSource
import com.afaneca.marvelchallenge.data.local.model.mapToDomain
import com.afaneca.marvelchallenge.domain.model.TopSellingItem
import com.afaneca.marvelchallenge.domain.repository.TopSellingRepository

class LiveTopSellingRepository(
    private val localDataSource: TopSellingLocalDataSource,
) : TopSellingRepository {
    override fun getTopSellingItems(): List<TopSellingItem> =
        localDataSource.getTopSellingItems().map { it.mapToDomain() }
}