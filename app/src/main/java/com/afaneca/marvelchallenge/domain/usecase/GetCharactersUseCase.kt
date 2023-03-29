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
        when (val response = characterRepository.getCharacters(page, searchQuery)) {
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