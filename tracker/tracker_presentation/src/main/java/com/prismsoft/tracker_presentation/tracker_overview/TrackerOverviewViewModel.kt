package com.prismsoft.tracker_presentation.tracker_overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prismsoft.core.domain.preferences.Preferences
import com.prismsoft.core.navigation.Route
import com.prismsoft.core.util.UiEvent
import com.prismsoft.tracker_domain.use_case.TrackerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackerOverviewViewModel @Inject constructor(
    preferences: Preferences,
    private val trackerUseCases: TrackerUseCases
) : ViewModel() {

    private var foodStateJob: Job? = null
    var state by mutableStateOf(TrackerOverviewState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        preferences.saveShouldShowOnboarding(false)
    }

    fun onEvent(event: TrackerOverviewEvent) {
        when (event) {
            is TrackerOverviewEvent.onAddNewFoodTap -> {
                viewModelScope.launch {
                    _uiEvent.send(
                        UiEvent.Navigate(
                            route = Route.SEARCH +
                                    "/${event.meal.mealType.name}" +
                                    "/${state.date.dayOfMonth}" +
                                    "/${state.date.monthValue}" +
                                    "/${state.date.year}"
                        )
                    )
                }
            }

            is TrackerOverviewEvent.onDeleteTrackedFoodTap -> {
                viewModelScope.launch {
                    trackerUseCases.deleteTrackedFood(event.trackedFood)
                    refreshFoods()
                }
            }

            is TrackerOverviewEvent.onToggleMealTap -> {
                state = state.copy(meals = state.meals.map {
                    if (it.name == event.meal.name) {
                        it.copy(isExpanded = !it.isExpanded)
                    } else {
                        it
                    }
                })
                refreshFoods()
            }

            TrackerOverviewEvent.onNextDayTap -> {
                state = state.copy(date = state.date.plusDays(1))
            }

            TrackerOverviewEvent.onPreviousDayTap -> {
                state = state.copy(date = state.date.minusDays(1))
            }
        }
    }

    private fun refreshFoods() {
        foodStateJob?.cancel()
        foodStateJob = trackerUseCases
            .getFoodsForDate(state.date)
            .onEach { foods ->
                val nutrientResults = trackerUseCases.calculateMealNutrients(foods)
                state = state.copy(
                    totalCarbs = nutrientResults.totalCarbs,
                    totalProtein = nutrientResults.totalProtein,
                    totalFat = nutrientResults.totalFat,
                    totalCalories = nutrientResults.totalCalories,
                    carbsGoal = nutrientResults.carbsGoal,
                    proteinGoal = nutrientResults.proteinGoal,
                    fatGoal = nutrientResults.fatGoal,
                    caloriesGoal = nutrientResults.caloriesGoal,
                    trackedFoods = foods,
                    meals = state.meals.map {
                        val nutrientsForMeal =
                            nutrientResults.totalNutrients[it.mealType]
                                ?: return@map it.copy(
                                    carbs = 0,
                                    protein = 0,
                                    fat = 0,
                                    calories = 0
                                )
                        it.copy(
                            carbs = nutrientsForMeal.carbs,
                            protein = nutrientsForMeal.protein,
                            fat = nutrientsForMeal.fat,
                            calories = nutrientsForMeal.calories
                        )
                    }
                )
            }
            .launchIn(viewModelScope)
    }
}