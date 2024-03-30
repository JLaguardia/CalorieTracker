package com.prismsoft.tracker_domain.use_case

import com.prismsoft.tracker_domain.model.TrackableFood
import com.prismsoft.tracker_domain.model.TrackedFood
import com.prismsoft.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class GetFoodsForDate(private val repository: TrackerRepository) {

    operator fun invoke(
        date: LocalDate
    ): Flow<List<TrackedFood>>{
        return repository.getFoodsForDate(date)
    }
}