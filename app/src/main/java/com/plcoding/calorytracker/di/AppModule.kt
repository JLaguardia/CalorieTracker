package com.plcoding.calorytracker.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.prismsoft.core.data.preferences.DefaultPreferences
import com.prismsoft.core.domain.preferences.Preferences
import com.prismsoft.core.domain.use_case.FilterOutDigits
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesSharedPreferences(
        app: Application
    ): SharedPreferences {
        return app.getSharedPreferences("shared_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providesPreferences(preferences: SharedPreferences): Preferences {
        return DefaultPreferences(preferences)
    }

    @Provides
    @Singleton
    fun providesFilterOutDigits(): FilterOutDigits = FilterOutDigits()
}