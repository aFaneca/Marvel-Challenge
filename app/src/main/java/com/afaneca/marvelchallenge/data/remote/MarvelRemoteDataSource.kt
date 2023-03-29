package com.afaneca.marvelchallenge.data.remote

import com.afaneca.marvelchallenge.common.Constants
import com.afaneca.marvelchallenge.data.remote.entity.GetCharactersResponse
import retrofit2.Response

interface MarvelRemoteDataSource {

    suspend fun getCharacters(
        offset: Int,
        searchQuery: String? = null,
        limit: Int = Constants.DEFAULT_PAGE_SIZE,
    ): Response<GetCharactersResponse>
}