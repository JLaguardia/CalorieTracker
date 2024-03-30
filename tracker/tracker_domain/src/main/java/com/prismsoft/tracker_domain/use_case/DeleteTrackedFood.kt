package com.prismsoft.tracker_domain.use_case

import com.prismsoft.tracker_domain.model.TrackableFood
import com.prismsoft.tracker_domain.model.TrackedFood
import com.prismsoft.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class DeleteTrackedFood(private val repository: TrackerRepository) {

    suspend operator fun invoke(food: TrackedFood) {
         repository.deleteTrackedFood(food)
    }
}