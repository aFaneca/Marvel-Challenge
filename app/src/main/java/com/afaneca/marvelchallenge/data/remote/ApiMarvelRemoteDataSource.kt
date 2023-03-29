package com.afaneca.marvelchallenge.data.remote

import com.afaneca.marvelchallenge.common.Constants.DEFAULT_PAGE_SIZE
import com.afaneca.marvelchallenge.data.remote.entity.GetCharactersResponse
import retrofit2.Response
import javax.inject.Inject

class ApiMarvelRemoteDataSource @Inject constructor(
    private val marvelApi: MarvelApi
) : MarvelRemoteDataSource {
    override suspend fun getCharacters(
        offset: Int,
        searchQuery: String?,
        limit: Int
    ): Response<GetCharactersResponse> = marvelApi.getCharacters(offset, searchQuery, limit)
}