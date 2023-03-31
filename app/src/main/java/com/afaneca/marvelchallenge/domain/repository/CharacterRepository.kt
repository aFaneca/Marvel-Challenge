package com.afaneca.marvelchallenge.domain.repository

import com.afaneca.marvelchallenge.common.Resource
import com.afaneca.marvelchallenge.domain.model.CharacterContent
import com.afaneca.marvelchallenge.domain.model.CharacterPage

interface CharacterRepository {

    suspend fun getCharacters(
        page: Int,
        searchQuery: String? = null,
    ): Resource<CharacterPage>

    suspend fun getCharacterComics(
        characterId: Int,
    ): Resource<List<CharacterContent>>

    suspend fun getCharacterEvents(
        characterId: Int,
    ): Resource<List<CharacterContent>>

    suspend fun getCharacterStories(
        characterId: Int,
    ): Resource<List<CharacterContent>>

    suspend fun getCharacterSeries(
        characterId: Int,
    ): Resource<List<CharacterContent>>
}