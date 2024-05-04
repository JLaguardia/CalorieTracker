package com.prismsoft.core.data.preferences

import android.content.SharedPreferences
import androidx.core.content.edit
import com.prismsoft.core.domain.model.ActivityLevel
import com.prismsoft.core.domain.model.Gender
import com.prismsoft.core.domain.model.GoalType
import com.prismsoft.core.domain.model.UserInfo
import com.prismsoft.core.domain.preferences.Preferences

class DefaultPreferences(
    private val sharedPrefs: SharedPreferences
) : Preferences {
    override fun saveGender(gender: Gender) {
        sharedPrefs.edit {
            putString(Preferences.KEY_GENDER, gender.name)
        }
    }

    override fun saveAge(age: Int) {
        sharedPrefs.edit {
            putInt(Preferences.KEY_AGE, age)
        }
    }

    override fun saveWeight(weight: Float) {
        sharedPrefs.edit {
            putFloat(Preferences.KEY_WEIGHT, weight)
        }
    }

    override fun saveHeight(height: Int) {
        sharedPrefs.edit {
            putInt(Preferences.KEY_HEIGHT, height)
        }
    }

    override fun saveActivityLevel(activityLevel: ActivityLevel) {
        sharedPrefs.edit {
            putString(Preferences.KEY_ACTIVITY_LEVEL, activityLevel.name)
        }
    }

    override fun saveGoalType(goalType: GoalType) {
        sharedPrefs.edit {
            putString(Preferences.KEY_GOAL_TYPE, goalType.name)
        }
    }

    override fun saveProteinRatio(ratio: Float) {
        sharedPrefs.edit {
            putFloat(Preferences.KEY_PROTEIN_RATIO, ratio)
        }
    }

    override fun saveCarbRatio(ratio: Float) {
        sharedPrefs.edit {
            putFloat(Preferences.KEY_CARB_RATIO, ratio)
        }
    }

    override fun saveFatRatio(ratio: Float) {
        sharedPrefs.edit {
            putFloat(Preferences.KEY_FAT_RATIO, ratio)
        }
    }

    override fun loadUserInfo(): UserInfo = UserInfo(
        gender = Gender.fromString(sharedPrefs.getString(Preferences.KEY_GENDER, "")),
        age = sharedPrefs.getInt(Preferences.KEY_AGE, 0),
        weight = sharedPrefs.getInt(Preferences.KEY_WEIGHT, 0),
        height = sharedPrefs.getInt(Preferences.KEY_HEIGHT, 0),
        activityLevel = ActivityLevel.fromString(
            sharedPrefs.getString(
                Preferences.KEY_ACTIVITY_LEVEL,
                ""
            )
        ),
        goalType = GoalType.fromString(sharedPrefs.getString(Preferences.KEY_GOAL_TYPE, "")),
        proteinRatio = sharedPrefs.getFloat(Preferences.KEY_PROTEIN_RATIO, 0f),
        carbRatio = sharedPrefs.getFloat(Preferences.KEY_CARB_RATIO, 0f),
        fatRatio = sharedPrefs.getFloat(Preferences.KEY_FAT_RATIO, 0f),
    )

    override fun saveShouldShowOnboarding(shouldShow: Boolean) {
        sharedPrefs.edit {
            putBoolean(Preferences.KEY_SHOULD_SHOW_ONBOARDING, shouldShow)
        }
    }

    override fun loadShouldShowOnboarding(): Boolean =
        sharedPrefs.getBoolean(Preferences.KEY_SHOULD_SHOW_ONBOARDING, true)

}