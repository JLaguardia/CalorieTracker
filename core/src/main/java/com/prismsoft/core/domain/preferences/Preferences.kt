package com.prismsoft.core.domain.preferences

import com.prismsoft.core.domain.model.ActivityLevel
import com.prismsoft.core.domain.model.Gender
import com.prismsoft.core.domain.model.GoalType
import com.prismsoft.core.domain.model.UserInfo

interface Preferences {
    fun saveGender(gender: Gender)
    fun saveAge(age: Int)
    fun saveWeight(weight: Float)
    fun saveHeight(height: Int)
    fun saveActivityLevel(activityLevel: ActivityLevel)
    fun saveGoalType(goalType: GoalType)
    fun saveProteinRatio(ratio: Float)
    fun saveCarbRatio(ratio: Float)
    fun saveFatRatio(ratio: Float)
    fun loadUserInfo(): UserInfo

    companion object {
        const val KEY_GENDER = "Gender"
        const val KEY_AGE = "Age"
        const val KEY_WEIGHT = "Weight"
        const val KEY_HEIGHT = "Height"
        const val KEY_ACTIVITY_LEVEL = "ActivityLevel"
        const val KEY_GOAL_TYPE = "GoalType"
        const val KEY_PROTEIN_RATIO = "ProteinRatio"
        const val KEY_CARB_RATIO = "CarbRatio"
        const val KEY_FAT_RATIO = "FatRatio"
    }
}