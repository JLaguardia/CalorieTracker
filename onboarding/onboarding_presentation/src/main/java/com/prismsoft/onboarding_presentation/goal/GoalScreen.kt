package com.prismsoft.onboarding_presentation.goal

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.prismsoft.core.util.UiEvent
import com.prismsoft.core_ui.LocalSpacing
import com.prismsoft.core_ui.components.CenteredColumn
import com.prismsoft.core.R
import com.prismsoft.core.domain.model.GoalType
import com.prismsoft.onboarding_presentation.components.ActionButton
import com.prismsoft.onboarding_presentation.components.SelectableButton

// why do this? to use preview. ViewModel hoisted out
@Composable
fun GoalScreen(
    viewModel: GoalViewModel = hiltViewModel(),
    onNextTap: () -> Unit
) {
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                UiEvent.Success -> onNextTap()
                else -> Unit
            }
        }
    }

    GoalSelect(
        selectedGoalType = viewModel.selectedGoalType,
        onGoalSelected = viewModel::onGoalSelected,
        onNextTapped = viewModel::onNextTapped
    )
}

@Composable
fun GoalSelect(
    selectedGoalType: GoalType,
    onGoalSelected: (GoalType) -> Unit,
    onNextTapped: () -> Unit
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
                text = stringResource(id = R.string.lose_keep_or_gain_weight),
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            Row {
                SelectableButton(
                    text = stringResource(id = R.string.lose),
                    isSelected = selectedGoalType == GoalType.LoseWeight,
                    color = MaterialTheme.colors.primaryVariant,
                    selectedTextColor = Color.White,
                    textStyle = MaterialTheme.typography.button.copy(fontWeight = FontWeight.Normal)
                ) {
                    onGoalSelected(GoalType.LoseWeight)
                }
                Spacer(modifier = Modifier.width(spacing.spaceMedium))
                SelectableButton(
                    text = stringResource(id = R.string.keep),
                    isSelected = selectedGoalType == GoalType.KeepWeight,
                    color = MaterialTheme.colors.primaryVariant,
                    selectedTextColor = Color.White,
                    textStyle = MaterialTheme.typography.button.copy(fontWeight = FontWeight.Normal)
                ) {
                    onGoalSelected(GoalType.KeepWeight)
                }
                Spacer(modifier = Modifier.width(spacing.spaceMedium))
                SelectableButton(
                    text = stringResource(id = R.string.gain),
                    isSelected = selectedGoalType == GoalType.GainWeight,
                    color = MaterialTheme.colors.primaryVariant,
                    selectedTextColor = Color.White,
                    textStyle = MaterialTheme.typography.button.copy(fontWeight = FontWeight.Normal)
                ) {
                    onGoalSelected(GoalType.GainWeight)
                }
            }
        }

        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = onNextTapped,
            modifier = Modifier.align(
                Alignment.BottomEnd
            )
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun GoalPreview() {
    GoalSelect(
        selectedGoalType = GoalType.KeepWeight,
        onGoalSelected = {}
    ) {

    }
}
