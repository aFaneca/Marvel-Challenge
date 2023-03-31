package com.afaneca.marvelchallenge.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afaneca.marvelchallenge.common.AppDispatchers
import com.afaneca.marvelchallenge.common.Resource
import com.afaneca.marvelchallenge.domain.model.CharacterContent
import com.afaneca.marvelchallenge.domain.usecase.GetCharacterComicsUseCase
import com.afaneca.marvelchallenge.domain.usecase.GetCharacterEventsUseCase
import com.afaneca.marvelchallenge.domain.usecase.GetCharacterSeriesUseCase
import com.afaneca.marvelchallenge.domain.usecase.GetCharacterStoriesUseCase
import com.afaneca.marvelchallenge.ui.model.CharacterContentUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val appDispatchers: AppDispatchers,
    private val getCharacterComicsUseCase: GetCharacterComicsUseCase,
    private val getCharacterEventsUseCase: GetCharacterEventsUseCase,
    private val getCharacterStoriesUseCase: GetCharacterStoriesUseCase,
    private val getCharacterSeriesUseCase: GetCharacterSeriesUseCase
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
        getComics(id)
        getEvents(id)
        getStories(id)
        getSeries(id)
    }

    //region comics
    private fun getComics(id: Int) {
        viewModelScope.launch(appDispatchers.IO) {
            getCharacterComicsUseCase(id).onEach { result ->
                when (result) {
                    is Resource.Success -> handleGetCharacterComicsSuccess(result.data)
                    is Resource.Error -> handleGetCharacterComicsError(result.message)
                    is Resource.Loading -> handleGetCharacterComicsLoading()
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun handleGetCharacterComicsLoading() {
        _comicsState.value = _comicsState.value.copy(isLoading = true, error = null)
    }

    private fun handleGetCharacterComicsError(message: String?) {
        _comicsState.value = _comicsState.value.copy(isLoading = false, error = message)
    }

    private fun handleGetCharacterComicsSuccess(data: List<CharacterContent>?) {
        val list = data?.map { item ->
            CharacterContentUiModel(
                name = item.name,
                imgUrl = item.imgUrl,
                description = item.description,
            )
        }
        if (!list.isNullOrEmpty()) {
            _comicsState.value = _comicsState.value.copy(
                isLoading = false,
                error = null,
                list = list
            )
        }
    }

    //endregion
    //region events
    private fun getEvents(id: Int) {
        viewModelScope.launch(appDispatchers.IO) {
            getCharacterEventsUseCase(id).onEach { result ->
                when (result) {
                    is Resource.Success -> handleGetCharacterEventsSuccess(result.data)
                    is Resource.Error -> handleGetCharacterEventsError(result.message)
                    is Resource.Loading -> handleGetCharacterEventsLoading()
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun handleGetCharacterEventsLoading() {
        _eventsState.value = _eventsState.value.copy(isLoading = true, error = null)
    }

    private fun handleGetCharacterEventsError(message: String?) {
        _eventsState.value = _eventsState.value.copy(isLoading = false, error = message)
    }

    private fun handleGetCharacterEventsSuccess(data: List<CharacterContent>?) {
        val list = data?.map { item ->
            CharacterContentUiModel(
                name = item.name,
                imgUrl = item.imgUrl,
                description = item.description
            )
        }
        if (!list.isNullOrEmpty()) {
            _eventsState.value = _eventsState.value.copy(
                isLoading = false,
                error = null,
                list = list
            )
        }
    }

    //endregion
    //region stories
    private fun getStories(id: Int) {
        viewModelScope.launch(appDispatchers.IO) {
            getCharacterStoriesUseCase(id).onEach { result ->
                when (result) {
                    is Resource.Success -> handleGetCharacterStoriesSuccess(result.data)
                    is Resource.Error -> handleGetCharacterStoriesError(result.message)
                    is Resource.Loading -> handleGetCharacterStoriesLoading()
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun handleGetCharacterStoriesLoading() {
        _storiesState.value = _storiesState.value.copy(isLoading = true, error = null)
    }

    private fun handleGetCharacterStoriesError(message: String?) {
        _storiesState.value = _storiesState.value.copy(isLoading = false, error = message)
    }

    private fun handleGetCharacterStoriesSuccess(data: List<CharacterContent>?) {
        val list = data?.map { item ->
            CharacterContentUiModel(
                name = item.name,
                imgUrl = item.imgUrl,
                description = item.description,
            )
        }
        if (!list.isNullOrEmpty()) {
            _storiesState.value = _storiesState.value.copy(
                isLoading = false,
                error = null,
                list = list
            )
        }
    }

    //endregion
    //region stories
    private fun getSeries(id: Int) {
        viewModelScope.launch(appDispatchers.IO) {
            getCharacterSeriesUseCase(id).onEach { result ->
                when (result) {
                    is Resource.Success -> handleGetCharacterSeriesSuccess(result.data)
                    is Resource.Error -> handleGetCharacterSeriesError(result.message)
                    is Resource.Loading -> handleGetCharacterSeriesLoading()
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun handleGetCharacterSeriesLoading() {
        _seriesState.value = _seriesState.value.copy(isLoading = true, error = null)
    }

    private fun handleGetCharacterSeriesError(message: String?) {
        _seriesState.value = _seriesState.value.copy(isLoading = false, error = message)
    }

    private fun handleGetCharacterSeriesSuccess(data: List<CharacterContent>?) {
        val list = data?.map { item ->
            CharacterContentUiModel(
                name = item.name,
                imgUrl = item.imgUrl,
                description = item.description,
            )
        }
        if (!list.isNullOrEmpty()) {
            _seriesState.value = _seriesState.value.copy(
                isLoading = false,
                error = null,
                list = list
            )
        }
    }
    //endregion
}