package com.prismsoft.onboarding_presentation.activity

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.prismsoft.core.util.UiEvent
import com.prismsoft.core_ui.LocalSpacing
import com.prismsoft.core_ui.components.CenteredColumn
import com.prismsoft.core.R
import com.prismsoft.core.domain.model.ActivityLevel
import com.prismsoft.onboarding_presentation.components.ActionButton
import com.prismsoft.onboarding_presentation.components.SelectableButton

// why do this? to use preview. ViewModel hoisted out
@Composable
fun ActivityLevelScreen(
    viewModel: ActivityViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit
) {
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }

    ActivityLevelSelect(
        selectedActivityLevel = viewModel.selectedActivityLevel,
        onActivityLevelSelected = viewModel::onActivityLevelTapped,
        onNextTapped = viewModel::onNextTapped
    )
}

@Composable
fun ActivityLevelSelect(
    selectedActivityLevel: ActivityLevel,
    onActivityLevelSelected: (ActivityLevel) -> Unit,
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
                text = stringResource(id = R.string.whats_your_activity_level),
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            Row {
                SelectableButton(
                    text = stringResource(id = R.string.low),
                    isSelected = selectedActivityLevel == ActivityLevel.Low,
                    color = MaterialTheme.colors.primaryVariant,
                    selectedTextColor = Color.White,
                    textStyle = MaterialTheme.typography.button.copy(fontWeight = FontWeight.Normal)
                ) {
                    onActivityLevelSelected(ActivityLevel.Low)
                }
                Spacer(modifier = Modifier.width(spacing.spaceMedium))
                SelectableButton(
                    text = stringResource(id = R.string.medium),
                    isSelected = selectedActivityLevel == ActivityLevel.Medium,
                    color = MaterialTheme.colors.primaryVariant,
                    selectedTextColor = Color.White,
                    textStyle = MaterialTheme.typography.button.copy(fontWeight = FontWeight.Normal)
                ) {
                    onActivityLevelSelected(ActivityLevel.Medium)
                }
                Spacer(modifier = Modifier.width(spacing.spaceMedium))
                SelectableButton(
                    text = stringResource(id = R.string.high),
                    isSelected = selectedActivityLevel == ActivityLevel.High,
                    color = MaterialTheme.colors.primaryVariant,
                    selectedTextColor = Color.White,
                    textStyle = MaterialTheme.typography.button.copy(fontWeight = FontWeight.Normal)
                ) {
                    onActivityLevelSelected(ActivityLevel.High)
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
fun ActivityLevelPreview() {
    ActivityLevelSelect(
        selectedActivityLevel = ActivityLevel.Low,
        onActivityLevelSelected = {}
    ) {

    }
}
