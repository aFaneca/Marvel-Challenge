package com.afaneca.marvelchallenge.data.local.model

import com.afaneca.marvelchallenge.domain.model.TopSellingItem

class TopSellingItemEntity(
    val id: Int,
    val name: String,
    val imgUrl: String,
    val ctaUrl: String,
)

fun TopSellingItemEntity.mapToDomain() = TopSellingItem(id, name, imgUrl, ctaUrl)