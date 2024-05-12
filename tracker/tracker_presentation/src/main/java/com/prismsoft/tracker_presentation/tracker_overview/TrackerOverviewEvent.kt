package com.prismsoft.tracker_presentation.tracker_overview

import com.prismsoft.tracker_domain.model.TrackedFood

sealed class TrackerOverviewEvent {
    object onNextDayTap: TrackerOverviewEvent()
    object onPreviousDayTap: TrackerOverviewEvent()
    data class onToggleMealTap(val meal: Meal): TrackerOverviewEvent()
    data class onDeleteTrackedFoodTap(val trackedFood: TrackedFood): TrackerOverviewEvent()
}