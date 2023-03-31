package com.afaneca.marvelchallenge.domain.usecase

import com.afaneca.marvelchallenge.common.Resource
import com.afaneca.marvelchallenge.domain.model.CharacterContent
import com.afaneca.marvelchallenge.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCharacterSeriesUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(
        characterId: Int,
    ): Flow<Resource<List<CharacterContent>>> = flow {
        emit(Resource.Loading())
        when (val response = characterRepository.getCharacterSeries(characterId)) {
            is Resource.Success -> {
                response.data?.let {
                    emit(Resource.Success(it))
                } ?: run { emit(Resource.Error("")) }
            }

            is Resource.Error -> {
                emit(Resource.Error(response.message ?: ""))
            }

            else -> {}
        }
    }
}