package com.afaneca.marvelchallenge.data.remote

import com.afaneca.marvelchallenge.common.Constants.DEFAULT_PAGE_SIZE
import com.afaneca.marvelchallenge.data.remote.entity.GetCharactersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApi {

    @GET("v1/public/characters")
    suspend fun getCharacters(
        @Query("offset") offset: Int,
        @Query("nameStartsWith") nameStartsWith: String? = null,
        @Query("limit") limit: Int,
    ): Response<GetCharactersResponse>
}