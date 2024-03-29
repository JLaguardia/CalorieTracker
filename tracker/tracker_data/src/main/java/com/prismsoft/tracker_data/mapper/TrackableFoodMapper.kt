package com.prismsoft.tracker_data.mapper

import com.prismsoft.tracker_data.remote.dto.Product
import com.prismsoft.tracker_domain.model.TrackableFood
import com.prismsoft.tracker_domain.model.TrackedFood

fun Product.toTrackableFood(): TrackableFood? {
    val calories = nutriments.energyKcal100g.toInt()
    val carbs = nutriments.carbohydrates100g.toInt()
    val protein = nutriments.proteins100g.toInt()
    val fat = nutriments.fat100g.toInt()
    return TrackableFood(
        name = productName ?: return null,
        imageUrl = imageFrontThumbUrl,
        caloriesPer100g = calories,
        carbsPer100g = carbs,
        proteinPer100g = protein,
        fatPer100g = fat
    )
}
