package com.afaneca.marvelchallenge.domain.usecase

import com.afaneca.marvelchallenge.common.Resource
import com.afaneca.marvelchallenge.domain.model.CharacterContent
import com.afaneca.marvelchallenge.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCharacterEventsUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(
        characterId: Int,
    ): Flow<Resource<List<CharacterContent>>> = flow {
        emit(Resource.Loading())
        when (val response = characterRepository.getCharacterEventsFromRemote(characterId)) {
            is Resource.Success -> {
                // Cache results to local data source
                response.data?.let {
                    characterRepository.insertCharacterEventsIntoLocalCache(characterId, it)
                } ?: run { emit(Resource.Error("")) }

                // Fetch final list from local data source (single source of truth)
                val cachedEvents = characterRepository.getCharacterEventsFromLocalCache(characterId)
                emit(Resource.Success(cachedEvents))
            }

            is Resource.Error -> {
                // In case of failure (e.g. no internet), return the cached version, if it exists
                val cachedEvents = characterRepository.getCharacterEventsFromLocalCache(characterId)
                if (cachedEvents.isEmpty()) {
                    // If cached version doesn't exist, emit error
                    emit(Resource.Error(response.message ?: ""))
                } else {
                    emit(Resource.Success(cachedEvents))
                }
            }

            else -> {}
        }
    }
}