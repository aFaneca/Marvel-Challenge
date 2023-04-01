package com.afaneca.marvelchallenge.data.remote

import com.afaneca.marvelchallenge.data.remote.entity.GetCharacterContentResponse
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

    override suspend fun getCharacterComics(
        id: Int,
        limit: Int
    ): Response<GetCharacterContentResponse> = marvelApi.getCharacterComics(id, limit)

    override suspend fun getCharacterEvents(
        id: Int,
        limit: Int
    ): Response<GetCharacterContentResponse> = marvelApi.getCharacterEvents(id, limit)

    override suspend fun getCharacterStories(
        id: Int,
        limit: Int
    ): Response<GetCharacterContentResponse> = marvelApi.getCharacterStories(id, limit)

    override suspend fun getCharacterSeries(
        id: Int,
        limit: Int
    ): Response<GetCharacterContentResponse> = marvelApi.getCharacterSeries(id, limit)

}