package com.afaneca.marvelchallenge.ui.details

import androidx.lifecycle.ViewModel
import com.afaneca.marvelchallenge.common.AppDispatchers
import com.afaneca.marvelchallenge.ui.model.CharacterContentUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val appDispatchers: AppDispatchers,
) : ViewModel() {

    private val _state = MutableStateFlow(DetailsState())
    val state = _state.asStateFlow()

    private val _comicsState = MutableStateFlow(ComicsState())
    val comicsState = _comicsState.asStateFlow()

    private val _eventsState = MutableStateFlow(EventsState())
    val eventsState = _eventsState.asStateFlow()

    private val _storiesState = MutableStateFlow(StoriesState())
    val storiesState = _storiesState.asStateFlow()

    private val _seriesState = MutableStateFlow(SeriesState())
    val seriesState = _seriesState.asStateFlow()

    fun init(id: Int, name: String, description: String, imgUrl: String) {
        _state.value = DetailsState(id, name, description, imgUrl)
        _comicsState.value = ComicsState(
            list = listOf(
                CharacterContentUiModel("Teste 1", "", "", "description description"),
                CharacterContentUiModel("Teste 2", "", "", "description description"),
                CharacterContentUiModel("Teste 3", "", "", "description description"),
                CharacterContentUiModel("Teste 4", "", "", "description description"),
                CharacterContentUiModel("Teste 5", "", "", "description description"),
            )
        )
    }
}