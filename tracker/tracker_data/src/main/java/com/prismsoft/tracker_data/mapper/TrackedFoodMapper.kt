package com.prismsoft.tracker_data.mapper

import com.prismsoft.tracker_data.local.entity.TrackedFoodEntity
import com.prismsoft.tracker_domain.model.MealType
import com.prismsoft.tracker_domain.model.TrackedFood
import java.time.LocalDate

fun TrackedFoodEntity.toTrackedFood() = TrackedFood(
    id = id,
    name = name,
    carbs = carbs,
    protein = protein,
    fat = fat,
    imageUrl = imageUrl,
    mealType = MealType.fromString(type),
    amount = amount,
    date = LocalDate.of(year, month, dayOfMonth),
    calories = calories
)

fun TrackedFood.toTrackedFoodEntity() = TrackedFoodEntity(
    id = id,
    name = name,
    carbs = carbs,
    protein = protein,
    fat = fat,
    imageUrl = imageUrl,
    type = mealType.name,
    amount = amount,
    year = date.year,
    month = date.monthValue,
    dayOfMonth = date.dayOfMonth,
    calories = calories

)