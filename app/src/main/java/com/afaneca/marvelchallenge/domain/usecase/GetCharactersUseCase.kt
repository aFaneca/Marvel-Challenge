package com.afaneca.marvelchallenge.domain.usecase

import com.afaneca.marvelchallenge.common.Resource
import com.afaneca.marvelchallenge.domain.model.CharacterPage
import com.afaneca.marvelchallenge.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(
        page: Int,
        searchQuery: String? = null
    ): Flow<Resource<CharacterPage>> = flow {
        emit(Resource.Loading())
        when (val response = characterRepository.getCharactersFromRemote(page, searchQuery)) {
            is Resource.Success -> {
                // Cache results to local data source
                response.data?.list?.let {
                    characterRepository.insertCharactersIntoLocalCache(it, searchQuery, page)
                } ?: run { emit(Resource.Error("")) }

                // Fetch final list from local data source (single source of truth)
                val cachedPage = characterRepository.getCharactersFromLocalCache(page, searchQuery)
                emit(Resource.Success(cachedPage))
            }
            is Resource.Error -> {
                // In case of failure (e.g. no internet), return the cached version, if it exists
                val cachedPage = characterRepository.getCharactersFromLocalCache(page, searchQuery)
                if (cachedPage.list.isEmpty()) {
                    // If cached version doesn't exist, emit error
                    emit(Resource.Error(response.message ?: ""))
                } else {
                    emit(Resource.Success(cachedPage))
                }
            }
            else -> {}
        }
    }
}