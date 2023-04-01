package com.afaneca.marvelchallenge.data.remote

import com.afaneca.marvelchallenge.common.Constants
import com.afaneca.marvelchallenge.data.remote.entity.GetCharacterContentResponse
import com.afaneca.marvelchallenge.data.remote.entity.GetCharactersResponse
import retrofit2.Response

interface MarvelRemoteDataSource {

    suspend fun getCharacters(
        offset: Int,
        searchQuery: String? = null,
        limit: Int = Constants.DEFAULT_PAGE_SIZE,
    ): Response<GetCharactersResponse>

    suspend fun getCharacterComics(
        id: Int,
        limit: Int = Constants.DEFAULT_CHARACTER_CONTENT_ITEM_LIMIT,
    ): Response<GetCharacterContentResponse>

    suspend fun getCharacterEvents(
        id: Int,
        limit: Int = Constants.DEFAULT_CHARACTER_CONTENT_ITEM_LIMIT,
    ): Response<GetCharacterContentResponse>

    suspend fun getCharacterStories(
        id: Int,
        limit: Int = Constants.DEFAULT_CHARACTER_CONTENT_ITEM_LIMIT,
    ): Response<GetCharacterContentResponse>

    suspend fun getCharacterSeries(
        id: Int,
        limit: Int = Constants.DEFAULT_CHARACTER_CONTENT_ITEM_LIMIT,
    ): Response<GetCharacterContentResponse>
}