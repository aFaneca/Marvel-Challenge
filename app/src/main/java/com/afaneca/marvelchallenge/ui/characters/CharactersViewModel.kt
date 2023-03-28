package com.afaneca.marvelchallenge.ui.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afaneca.marvelchallenge.common.AppDispatchers
import com.afaneca.marvelchallenge.ui.model.CharacterUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val appDispatchers: AppDispatchers,
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
        // TODO: replace mocked with real data
        _state.value = _state.value.copy(
            characterList = listOf(
                CharacterUiModel(
                    "1",
                    "Name 1",
                    "https://i.annihil.us/u/prod/marvel/i/mg/9/50/4ce18691cbf04.jpg"
                ),
                CharacterUiModel(
                    "2",
                    "Name 2",
                    "https://i.annihil.us/u/prod/marvel/i/mg/9/50/4ce18691cbf04.jpg"
                ),
                CharacterUiModel(
                    "3",
                    "Name 3",
                    "https://i.annihil.us/u/prod/marvel/i/mg/9/50/4ce18691cbf04.jpg"
                ),
                CharacterUiModel(
                    "4",
                    "Name 4",
                    "https://i.annihil.us/u/prod/marvel/i/mg/9/50/4ce18691cbf04.jpg"
                ),
                CharacterUiModel(
                    "5",
                    "Name 5",
                    "https://i.annihil.us/u/prod/marvel/i/mg/9/50/4ce18691cbf04.jpg"
                ),
                CharacterUiModel(
                    "6",
                    "Name 6",
                    "https://i.annihil.us/u/prod/marvel/i/mg/9/50/4ce18691cbf04.jpg"
                ),
                CharacterUiModel(
                    "7",
                    "Name 7",
                    "https://i.annihil.us/u/prod/marvel/i/mg/9/50/4ce18691cbf04.jpg"
                ),
                CharacterUiModel(
                    "8",
                    "Name 8",
                    "https://i.annihil.us/u/prod/marvel/i/mg/9/50/4ce18691cbf04.jpg"
                ),
            ),
            isLoading = false,
            isLoadingFromPagination = false,
            error = null,
        )
    }
}