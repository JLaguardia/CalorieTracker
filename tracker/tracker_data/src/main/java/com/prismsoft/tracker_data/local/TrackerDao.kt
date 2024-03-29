package com.prismsoft.tracker_data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.prismsoft.tracker_data.local.entity.TrackedFoodEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrackedFood(food: TrackedFoodEntity)

    @Delete
    suspend fun deleteTrackedFood(food: TrackedFoodEntity)

    @Query("""
        SELECT * 
        FROM TrackedFoodEntity 
        WHERE dayOfMonth = :day 
            AND month = :month
            AND year = :year
    """)
    fun getFoodsForDate(day: Int, month: Int, year: Int): Flow<List<TrackedFoodEntity>>
}