package com.afaneca.marvelchallenge.ui.about

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afaneca.marvelchallenge.common.AppDispatchers
import com.afaneca.marvelchallenge.domain.usecase.GetTopSellingItemsUseCase
import com.afaneca.marvelchallenge.ui.model.TopSellingItemUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject constructor(
    private val appDispatchers: AppDispatchers,
    private val getTopSellingItemsUseCase: GetTopSellingItemsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AboutState())
    val state = _state.asStateFlow()

    init {
        getTopSellingItems()
    }

    private fun getTopSellingItems() {
        viewModelScope.launch(appDispatchers.IO) {
            val items = getTopSellingItemsUseCase().first()
            _state.value = _state.value.copy(items = items.map { item ->
                TopSellingItemUiModel(
                    item.id,
                    item.name,
                    item.imgUrl,
                    item.ctaUrl
                )
            })
        }
    }
}