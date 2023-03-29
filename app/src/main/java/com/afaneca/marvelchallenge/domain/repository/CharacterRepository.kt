package com.afaneca.marvelchallenge.domain.repository

import com.afaneca.marvelchallenge.common.Resource
import com.afaneca.marvelchallenge.domain.model.CharacterPage

interface CharacterRepository {

    suspend fun getCharacters(
        page: Int,
        searchQuery: String? = null,
    ): Resource<CharacterPage>
}