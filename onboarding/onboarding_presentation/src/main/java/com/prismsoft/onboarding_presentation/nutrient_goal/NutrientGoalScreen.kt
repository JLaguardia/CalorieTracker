package com.prismsoft.onboarding_presentation.nutrient_goal

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.prismsoft.core.R
import com.prismsoft.core.util.UiEvent
import com.prismsoft.core_ui.LocalSpacing
import com.prismsoft.core_ui.components.CenteredColumn
import com.prismsoft.onboarding_presentation.components.ActionButton
import com.prismsoft.onboarding_presentation.components.UnitTextField

@Composable
fun NutrientGoalScreen(
    viewModel: NutrientGoalViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState,
    onNextTap: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                UiEvent.Success -> onNextTap()
                is UiEvent.ShowSnackBar ->
                    scaffoldState.snackbarHostState.showSnackbar(
                        event.message.asString(
                            context = context
                        )
                    )

                else -> Unit
            }
        }
    }

    NutrientGoalEntry(
        state = viewModel.state,
        onUiEvent = viewModel::onEvent,
    )
}

@Composable
fun NutrientGoalEntry(
    state: NutrientGoalState,
    onUiEvent: (NutrientGoalEvent) -> Unit,
) {
    val spacing = LocalSpacing.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceLarge)
    ) {
        CenteredColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = stringResource(id = R.string.what_are_your_nutrient_goals),
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            UnitTextField(
                value = state.carbsRatio,
                unit = stringResource(id = R.string.percent_carbs),
                onValueChange = {
                    onUiEvent(NutrientGoalEvent.OnCarbRatioEnter(it))
                }
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            UnitTextField(
                value = state.proteinRatio,
                unit = stringResource(id = R.string.percent_proteins),
                onValueChange = {
                    onUiEvent(NutrientGoalEvent.OnProteinRatioEnter(it))
                }
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            UnitTextField(
                value = state.fatRatio,
                unit = stringResource(id = R.string.percent_fats),
                onValueChange = {
                    onUiEvent(NutrientGoalEvent.OnFatRatioEnter(it))
                }
            )
        }

        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = { onUiEvent(NutrientGoalEvent.OnNextTap) },
            modifier = Modifier.align(
                Alignment.BottomEnd
            )
        )
    }
}

@Preview
@Composable
private fun NutrientGoalPreview() {
    NutrientGoalEntry(state = NutrientGoalState()) {

    }
}
