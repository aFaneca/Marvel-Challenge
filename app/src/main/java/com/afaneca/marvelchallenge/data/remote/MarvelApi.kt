package com.afaneca.marvelchallenge.data.remote

import com.afaneca.marvelchallenge.data.remote.entity.GetCharacterContentResponse
import com.afaneca.marvelchallenge.data.remote.entity.GetCharactersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApi {

    @GET("v1/public/characters")
    suspend fun getCharacters(
        @Query("offset") offset: Int,
        @Query("nameStartsWith") nameStartsWith: String? = null,
        @Query("limit") limit: Int,
    ): Response<GetCharactersResponse>

    @GET("v1/public/characters/{characterId}/comics")
    suspend fun getCharacterComics(
        @Path("characterId") id: Int,
        @Query("limit") limit: Int
    ): Response<GetCharacterContentResponse>

    @GET("v1/public/characters/{characterId}/events")
    suspend fun getCharacterEvents(
        @Path("characterId") id: Int,
        @Query("limit") limit: Int
    ): Response<GetCharacterContentResponse>

    @GET("v1/public/characters/{characterId}/stories")
    suspend fun getCharacterStories(
        @Path("characterId") id: Int,
        @Query("limit") limit: Int
    ): Response<GetCharacterContentResponse>

    @GET("v1/public/characters/{characterId}/series")
    suspend fun getCharacterSeries(
        @Path("characterId") id: Int,
        @Query("limit") limit: Int
    ): Response<GetCharacterContentResponse>
}