package com.prismsoft.onboarding_presentation.goal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prismsoft.core.domain.model.ActivityLevel
import com.prismsoft.core.domain.model.GoalType
import com.prismsoft.core.domain.preferences.Preferences
import com.prismsoft.core.navigation.Route
import com.prismsoft.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalViewModel @Inject constructor (
    private val preferences: Preferences
): ViewModel() {

    var selectedGoalType: GoalType by mutableStateOf(GoalType.KeepWeight)
    private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onGoalSelected(goal: GoalType) {
        selectedGoalType = goal
    }

    fun onNextTapped() {
        viewModelScope.launch {
            preferences.saveGoalType(selectedGoalType)
            _uiEvent.send(UiEvent.Navigate(Route.NUTRIENT_GOALS))
        }
    }
}