package com.prismsoft.tracker_data.repository

import com.prismsoft.tracker_data.local.TrackerDao
import com.prismsoft.tracker_data.mapper.toTrackableFood
import com.prismsoft.tracker_data.mapper.toTrackedFood
import com.prismsoft.tracker_data.mapper.toTrackedFoodEntity
import com.prismsoft.tracker_data.remote.OpenFoodApi
import com.prismsoft.tracker_domain.model.TrackableFood
import com.prismsoft.tracker_domain.model.TrackedFood
import com.prismsoft.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class TrackerRepositoryImpl @Inject constructor(
    private val trackerDao: TrackerDao,
    private val openFoodApi: OpenFoodApi
) : TrackerRepository {
    override suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int
    ): Result<List<TrackableFood>> = try {
        val dto = openFoodApi.searchFood(
            query = query,
            page = page,
            pageSize = pageSize
        )
        Result.success(dto.products.mapNotNull { it.toTrackableFood() })
    } catch (e: Exception){
        e.printStackTrace()
        Result.failure(e)
    }

    override suspend fun insertTrackedFood(trackableFood: TrackedFood) {
        trackerDao.insertTrackedFood(trackableFood.toTrackedFoodEntity())
    }

    override suspend fun deleteTrackedFood(trackableFood: TrackedFood) {
        trackerDao.deleteTrackedFood(trackableFood.toTrackedFoodEntity())
    }

    override fun getFoodsForDate(date: LocalDate): Flow<List<TrackedFood>> {
        return trackerDao.getFoodsForDate(date.monthValue, date.dayOfMonth, date.year)
            .map { entities -> entities.map { it.toTrackedFood() } }
    }

}