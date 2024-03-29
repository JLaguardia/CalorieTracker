package com.prismsoft.tracker_domain.model

import java.time.LocalDate

data class TrackedFood(
    val name: String,
    val carbs: Int,
    val protein: Int,
    val fat: Int,
    val imageUrl: String?,
    val mealType: MealType,
    val date: LocalDate,
    val calories: Int,
    val amount: Int,
    val id: Int? = null
)
