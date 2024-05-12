package com.prismsoft.onboarding_presentation.activity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prismsoft.core.domain.model.ActivityLevel
import com.prismsoft.core.domain.preferences.Preferences
import com.prismsoft.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor (
    private val preferences: Preferences
): ViewModel() {

    var selectedActivityLevel: ActivityLevel by mutableStateOf(ActivityLevel.Medium)
    private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onActivityLevelTapped(activityLevel: ActivityLevel) {
        selectedActivityLevel = activityLevel
    }

    fun onNextTapped() {
        viewModelScope.launch {
            preferences.saveActivityLevel(selectedActivityLevel)
            _uiEvent.send(UiEvent.Success)
        }
    }
}