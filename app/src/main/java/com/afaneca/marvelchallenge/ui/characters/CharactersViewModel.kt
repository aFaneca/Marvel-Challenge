package com.afaneca.marvelchallenge.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afaneca.marvelchallenge.common.AppDispatchers
import com.afaneca.marvelchallenge.common.Resource
import com.afaneca.marvelchallenge.domain.model.CharacterPage
import com.afaneca.marvelchallenge.domain.usecase.GetCharactersUseCase
import com.afaneca.marvelchallenge.ui.model.CharacterUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val appDispatchers: AppDispatchers,
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CharacterListState())
    val state = _state.asStateFlow()

    init {
        getCharacters()
    }

    /**
     * Fetches characters data.
     * If [searchQuery] is null, will assume filters are not being applied.
     */
    private fun getCharacters(searchQuery: String? = null) {
        viewModelScope.launch(appDispatchers.IO) {
            getCharactersUseCase(_state.value.page, searchQuery).onEach { result ->
                when (result) {
                    is Resource.Success -> handleGetCharactersSuccess(result.data)
                    is Resource.Error -> handleGetCharactersError(result.message)
                    is Resource.Loading -> handleGetCharactersLoading()
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun handleGetCharactersSuccess(data: CharacterPage?) {
        val pageData = data?.list?.map { item ->
            CharacterUiModel(
                item.id, item.name, item.thumbnailUrl
            )
        }

        // If new page is not empty, append items to list and notify UI
        if (!pageData.isNullOrEmpty()) {
            _state.value = _state.value.copy(
                characterList = _state.value.characterList?.plus(pageData) ?: pageData,
                isLoading = false,
                isLoadingFromPagination = false,
                error = null,
                hasReachedPaginationEnd = data.hasReachedPaginationEnd
            )
        } else {
            _state.value = _state.value.copy(
                isLoading = false,
                isLoadingFromPagination = false
            )
        }
    }

    private fun handleGetCharactersLoading() {
        with(state.value) {
            if (characterList.isNullOrEmpty()) {
                // First load
                _state.value = copy(isLoading = true, error = null)
            } else {
                // Pagination loading
                _state.value = copy(isLoadingFromPagination = true)
            }
        }
    }

    private fun handleGetCharactersError(errorMessage: String?) {
        _state.value = _state.value.copy(
            isLoading = false, isLoadingFromPagination = false, error = errorMessage
        )
    }

    private fun resetListState(newSearchQuery: String?) {
        _state.value = _state.value.copy(
            characterList = null,
            page = 0,
            hasReachedPaginationEnd = false,
            searchQuery = newSearchQuery
        )
    }

    private fun incrementPageNumber() {
        with(_state.value) {
            _state.value = this.copy(page = this.page + 1)
        }
    }

    private fun isLoadingResults(): Boolean =
        with(_state.value) { isLoading || isLoadingFromPagination }

    fun requestNextPage() {
        with(state.value) {
            if (hasReachedPaginationEnd
                || isLoadingResults()
                || _state.value.characterList.isNullOrEmpty()
            ) return
            // increment page number
            incrementPageNumber()

            // get more items
            getCharacters(searchQuery)
        }
    }

    fun onSearchInputSubmitted(query: String?) {
        resetListState(newSearchQuery = query)
        getCharacters(query)
    }
}