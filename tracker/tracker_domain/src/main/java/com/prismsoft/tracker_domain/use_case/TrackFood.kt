package com.prismsoft.tracker_domain.use_case

import com.prismsoft.tracker_domain.model.MealType
import com.prismsoft.tracker_domain.model.TrackableFood
import com.prismsoft.tracker_domain.model.TrackedFood
import com.prismsoft.tracker_domain.repository.TrackerRepository
import java.time.LocalDate
import javax.inject.Inject
import kotlin.math.roundToInt

class TrackFood(private val repository: TrackerRepository) {

    suspend operator fun invoke(
        food: TrackableFood,
        amount: Int,
        mealType: MealType,
        date: LocalDate
    ){
        repository.insertTrackedFood(
            TrackedFood(
                name = food.name,
                calories = ((food.caloriesPer100g / 100f) * amount).roundToInt(),
                carbs = ((food.carbsPer100g / 100f) * amount).roundToInt(),
                protein = ((food.proteinPer100g / 100f) * amount).roundToInt(),
                fat = ((food.fatPer100g / 100f) * amount).roundToInt(),
                imageUrl = food.imageUrl,
                mealType = mealType,
                amount = amount,
                date = date
            )
        )
    }
}