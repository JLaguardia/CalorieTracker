package com.plcoding.calorytracker.navigation

import androidx.navigation.NavController
import com.prismsoft.core.util.UiEvent

fun NavController.navigate(event: UiEvent.Navigate){
    navigate(event.route)
}