package com.prismsoft.onboarding_presentation.gender

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prismsoft.core.domain.model.Gender
import com.prismsoft.core.domain.preferences.Preferences
import com.prismsoft.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenderViewModel @Inject constructor (
    private val preferences: Preferences
): ViewModel() {

    var selectedGender: Gender by mutableStateOf(Gender.Male)
    private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onGenderTapped(gender: Gender) {
        selectedGender = gender
    }

    fun onNextTapped() {
        viewModelScope.launch {
            preferences.saveGender(selectedGender)
            _uiEvent.send(UiEvent.Success)
        }
    }

}