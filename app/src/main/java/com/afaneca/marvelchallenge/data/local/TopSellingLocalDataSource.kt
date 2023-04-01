package com.afaneca.marvelchallenge.data.local

import com.afaneca.marvelchallenge.data.local.model.TopSellingItemEntity

interface TopSellingLocalDataSource {
    fun getTopSellingItems(): List<TopSellingItemEntity>
}