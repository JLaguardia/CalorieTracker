package com.prismsoft.tracker_domain.repository

import com.prismsoft.tracker_domain.model.TrackableFood
import com.prismsoft.tracker_domain.model.TrackedFood
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TrackerRepository {
    suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int
    ): Result<List<TrackableFood>>

    suspend fun insertTrackedFood(trackableFood: TrackedFood)
    suspend fun deleteTrackedFood(trackableFood: TrackedFood)
    fun getFoodsForDate(date: LocalDate): Flow<List<TrackedFood>>
}