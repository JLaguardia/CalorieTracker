package com.prismsoft.tracker_domain.use_case

import com.prismsoft.tracker_domain.model.TrackableFood
import com.prismsoft.tracker_domain.repository.TrackerRepository
import javax.inject.Inject

class SearchFood(private val repository: TrackerRepository) {

    suspend operator fun invoke(
        query: String,
        page: Int = 1,
        pageSize: Int = 40
    ): Result<List<TrackableFood>>{
        if(query.isBlank()){
            return Result.success(emptyList())
        }

        return repository.searchFood(
            query = query.trim(),
            page = page,
            pageSize = pageSize
        )
    }
}