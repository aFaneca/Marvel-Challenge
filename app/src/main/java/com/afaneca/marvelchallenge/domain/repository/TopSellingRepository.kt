package com.afaneca.marvelchallenge.domain.repository

import com.afaneca.marvelchallenge.domain.model.TopSellingItem

interface TopSellingRepository {
    fun getTopSellingItems(): List<TopSellingItem>
}