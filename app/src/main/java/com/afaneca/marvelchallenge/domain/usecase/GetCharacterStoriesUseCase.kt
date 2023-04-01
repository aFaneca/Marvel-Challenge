package com.afaneca.marvelchallenge.domain.usecase

import com.afaneca.marvelchallenge.common.Resource
import com.afaneca.marvelchallenge.domain.model.CharacterContent
import com.afaneca.marvelchallenge.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCharacterStoriesUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(
        characterId: Int,
    ): Flow<Resource<List<CharacterContent>>> = flow {
        emit(Resource.Loading())
        when (val response = characterRepository.getCharacterStoriesFromRemote(characterId)) {
            is Resource.Success -> {
                // Cache results to local data source
                response.data?.let {
                    characterRepository.insertCharacterStoriesIntoLocalCache(characterId, it)
                } ?: run { emit(Resource.Error("")) }

                // Fetch final list from local data source (single source of truth)
                val cachedStories =
                    characterRepository.getCharacterStoriesFromLocalCache(characterId)
                emit(Resource.Success(cachedStories))
            }

            is Resource.Error -> {
                // In case of failure (e.g. no internet), return the cached version, if it exists
                val cachedStories =
                    characterRepository.getCharacterStoriesFromLocalCache(characterId)
                if (cachedStories.isEmpty()) {
                    // If cached version doesn't exist, emit error
                    emit(Resource.Error(response.message ?: ""))
                } else {
                    emit(Resource.Success(cachedStories))
                }
            }

            else -> {}
        }
    }
}