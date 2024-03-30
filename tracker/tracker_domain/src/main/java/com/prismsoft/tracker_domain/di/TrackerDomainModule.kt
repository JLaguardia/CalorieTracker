package com.prismsoft.tracker_domain.di

import com.prismsoft.core.domain.preferences.Preferences
import com.prismsoft.tracker_domain.repository.TrackerRepository
import com.prismsoft.tracker_domain.use_case.CalculateMealNutrients
import com.prismsoft.tracker_domain.use_case.DeleteTrackedFood
import com.prismsoft.tracker_domain.use_case.GetFoodsForDate
import com.prismsoft.tracker_domain.use_case.SearchFood
import com.prismsoft.tracker_domain.use_case.TrackFood
import com.prismsoft.tracker_domain.use_case.TrackerUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object TrackerDomainModule {

    @Provides
    @ViewModelScoped
    fun provideTrackerUseCases(
        repository: TrackerRepository,
        preferences: Preferences
    ): TrackerUseCases {
        return TrackerUseCases(
            trackFood = TrackFood(repository),
            searchFood = SearchFood(repository),
            getFoodsForDate = GetFoodsForDate(repository),
            deleteTrackedFood = DeleteTrackedFood(repository),
            calculateMealNutrients = CalculateMealNutrients(preferences),
        )
    }
}