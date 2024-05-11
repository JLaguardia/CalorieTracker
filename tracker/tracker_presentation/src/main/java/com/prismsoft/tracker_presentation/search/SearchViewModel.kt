package com.prismsoft.tracker_presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prismsoft.core.domain.use_case.FilterOutDigits
import com.prismsoft.core.util.UiEvent
import com.prismsoft.core.util.UiText
import com.prismsoft.tracker_domain.use_case.TrackerUseCases
import com.prismsoft.core.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val trackerUseCases: TrackerUseCases,
    private val filterOutDigits: FilterOutDigits
) : ViewModel() {

    var state by mutableStateOf(SearchState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: SearchEvent) {
        when (event) {
            SearchEvent.OnSearch -> {
                executeSearch()
            }

            is SearchEvent.OnQueryChanged -> {
                state = state.copy(query = event.query)
            }

            is SearchEvent.OnAmountForfoodChange -> {
                state = state.copy(
                    trackableFoods = state.trackableFoods.map {
                        if (it.food == event.food) {
                            it.copy(amount = filterOutDigits(event.amount.toString()))
                        } else {
                            it
                        }
                    }
                )
            }

            is SearchEvent.OnToggleTrackableFood -> {
                state = state.copy(
                    trackableFoods = state.trackableFoods.map {
                        if (it.food == event.food) {
                            it.copy(isExpanded = !it.isExpanded)
                        } else {
                            it
                        }
                    }
                )
            }

            is SearchEvent.OnSearchFocusChanged -> {
                state = state.copy(
                    isHintVisible = !event.isFocused && state.query.isBlank()
                )
            }

            is SearchEvent.OnTrackFoodTap -> {
                trackFood(event)
            }
        }
    }

    private fun executeSearch() {
        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(
                isSearching = true,
                trackableFoods = emptyList()
            )
            trackerUseCases
                .searchFood(state.query)
                .onSuccess { foods ->
                    state = state.copy(
                        isSearching = false,
                        trackableFoods = foods.map { food -> TrackableFoodUiState(food) },
                        query = ""
                    )
                }
                .onFailure {
                    state = state.copy(isSearching = false)
                    _uiEvent.send(
                        UiEvent.ShowSnackBar(
                            UiText.StringResource(R.string.error_something_went_wrong)
                        )
                    )
                }
        }
    }

    private fun trackFood(event: SearchEvent.OnTrackFoodTap) {
        viewModelScope.launch {
            val uiState = state.trackableFoods.find { it.food == event.food }
            trackerUseCases.trackFood(
                food = uiState?.food ?: return@launch,
                amount = uiState.amount.toIntOrNull() ?: return@launch,
                mealType = event.mealType,
                date = event.date
            )

            _uiEvent.send(UiEvent.NavigateUp)
        }
    }
}