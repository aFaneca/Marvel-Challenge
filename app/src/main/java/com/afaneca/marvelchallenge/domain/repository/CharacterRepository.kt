package com.afaneca.marvelchallenge.domain.repository

import com.afaneca.marvelchallenge.common.Resource
import com.afaneca.marvelchallenge.domain.model.CharacterContent
import com.afaneca.marvelchallenge.domain.model.CharacterPage
import com.afaneca.marvelchallenge.domain.model.MarvelCharacter

interface CharacterRepository {

    //region characters
    suspend fun getCharactersFromRemote(
        page: Int,
        searchQuery: String? = null,
    ): Resource<CharacterPage>

    suspend fun getCharactersFromLocalCache(
        page: Int,
        searchQuery: String? = "",
    ): CharacterPage

    suspend fun insertCharactersIntoLocalCache(
        list: List<MarvelCharacter>,
        query: String? = "",
        page: Int
    )
    //endregion

    //region comics
    suspend fun getCharacterComicsFromRemote(
        characterId: Int,
    ): Resource<List<CharacterContent>>

    suspend fun getCharacterComicsFromLocalCache(
        characterId: Int,
    ): List<CharacterContent>

    suspend fun insertCharacterComicsIntoLocalCache(
        characterId: Int,
        list: List<CharacterContent>
    )
    //endregion

    //region events
    suspend fun getCharacterEventsFromRemote(
        characterId: Int,
    ): Resource<List<CharacterContent>>

    suspend fun getCharacterEventsFromLocalCache(
        characterId: Int,
    ): List<CharacterContent>

    suspend fun insertCharacterEventsIntoLocalCache(
        characterId: Int,
        list: List<CharacterContent>
    )
    //endregion

    //region stories
    suspend fun getCharacterStories(
        characterId: Int,
    ): Resource<List<CharacterContent>>
    //endregion

    //region series
    suspend fun getCharacterSeries(
        characterId: Int,
    ): Resource<List<CharacterContent>>
    //endregion
}