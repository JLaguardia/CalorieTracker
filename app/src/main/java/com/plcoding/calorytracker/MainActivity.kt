package com.prismsoft.calorytracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.prismsoft.calorytracker.ui.theme.CaloryTrackerTheme
import com.prismsoft.core.domain.preferences.Preferences
import com.plcoding.calorytracker.navigation.Route
import com.prismsoft.onboarding_presentation.activity.ActivityLevelScreen
import com.prismsoft.onboarding_presentation.age.AgeScreen
import com.prismsoft.onboarding_presentation.gender.GenderScreen
import com.prismsoft.onboarding_presentation.goal.GoalScreen
import com.prismsoft.onboarding_presentation.height.HeightScreen
import com.prismsoft.onboarding_presentation.nutrient_goal.NutrientGoalScreen
import com.prismsoft.onboarding_presentation.weight.WeightScreen
import com.prismsoft.onboarding_presentation.welcome.WelcomeScreen
import com.prismsoft.tracker_presentation.search.SearchScreen
import com.prismsoft.tracker_presentation.tracker_overview.TrackerOverviewScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val shouldShowOnboarding = preferences.loadShouldShowOnboarding()
        setContent {
            CaloryTrackerTheme {
                val navCtrl = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState
                ) {
                    NavHost(
                        modifier = Modifier.padding(it),
                        navController = navCtrl,
                        startDestination = if (shouldShowOnboarding) {
                            Route.WELCOME
                        } else {
                            Route.TRACKER_OVERVIEW
                        }
                    ) {
                        composable(Route.WELCOME) {
                            WelcomeScreen {
                                navCtrl.navigate(Route.GENDER)
                            }
                        }

                        composable(Route.GENDER) {
                            GenderScreen { navCtrl.navigate(Route.HEIGHT) }
                        }

                        composable(Route.HEIGHT) {
                            HeightScreen(
                                onNextTap = { navCtrl.navigate(Route.WEIGHT) },
                                scaffoldState = scaffoldState
                            )
                        }

                        composable(Route.WEIGHT) {
                            WeightScreen(
                                onNextTap = { navCtrl.navigate(Route.ACTIVITY) },
                                scaffoldState = scaffoldState
                            )
                        }

                        composable(Route.AGE) {
                            AgeScreen(
                                onNextTap = { navCtrl.navigate(Route.HEIGHT) },
                                scaffoldState = scaffoldState
                            )
                        }

                        composable(Route.ACTIVITY) {
                            ActivityLevelScreen { navCtrl.navigate(Route.GOAL) }
                        }

                        composable(Route.GOAL) {
                            GoalScreen { navCtrl.navigate(Route.NUTRIENT_GOALS) }
                        }

                        composable(Route.NUTRIENT_GOALS) {
                            NutrientGoalScreen(
                                onNextTap = { navCtrl.navigate(Route.TRACKER_OVERVIEW) },
                                scaffoldState = scaffoldState
                            )
                        }

                        composable(Route.TRACKER_OVERVIEW) {
                            TrackerOverviewScreen(onNavigateToSearch = { mealName, dayOfMonth, month, year ->
                                navCtrl.navigate("${Route.SEARCH}/$mealName/$dayOfMonth/$month/$year")
                            })
                        }

                        composable(
                            route = Route.SEARCH + "/{mealName}/{dayOfMonth}/{month}/{year}",
                            arguments = listOf(
                                navArgument("mealName") {
                                    type = NavType.StringType
                                },
                                navArgument("dayOfMonth") {
                                    type = NavType.IntType
                                },
                                navArgument("month") {
                                    type = NavType.IntType
                                },
                                navArgument("year") {
                                    type = NavType.IntType
                                }
                            )
                        ) { entry ->
                            val mealName = entry.arguments?.getString("mealName")!!
                            val dayOfMonth = entry.arguments?.getInt("dayOfMonth")!!
                            val month = entry.arguments?.getInt("month")!!
                            val year = entry.arguments?.getInt("year")!!
                            SearchScreen(
                                scaffoldState = scaffoldState,
                                mealName = mealName,
                                dayOfMonth = dayOfMonth,
                                month = month,
                                year = year,
                                onNavigateUp = { navCtrl.navigateUp() })
                        }
                    }
                }

            }
        }
    }
}