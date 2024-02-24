package com.prismsoft.core.domain.model

data class UserInfo(
    val gender: Gender,
    val age: Int,
    val weight: Int,
    val height: Int,
    val goalType: GoalType,
    val activityLevel: ActivityLevel,
    val proteinRatio: Float,
    val carbRatio: Float,
    val fatRatio: Float,
)
