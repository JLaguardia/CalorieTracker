package com.prismsoft.tracker_presentation.search

import com.prismsoft.tracker_domain.model.MealType
import com.prismsoft.tracker_domain.model.TrackableFood
import java.time.LocalDate

sealed class SearchEvent {
    object OnSearch : SearchEvent()
    data class OnQueryChanged(val query: String) : SearchEvent()
    data class OnToggleTrackableFood(val food: TrackableFood) : SearchEvent()
    data class OnAmountForfoodChange(
        val food: TrackableFood,
        val amount: Int
    ) : SearchEvent()

    data class OnTrackFoodTap(
        val food: TrackableFood,
        val mealType: MealType,
        val date: LocalDate
    ) : SearchEvent()

    data class OnSearchFocusChanged(val isFocused: Boolean) : SearchEvent()
}