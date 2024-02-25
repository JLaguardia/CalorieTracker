package com.prismsoft.onboarding_presentation.gender

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
import com.prismsoft.core.domain.model.Gender
import com.prismsoft.onboarding_presentation.components.ActionButton
import com.prismsoft.onboarding_presentation.components.SelectableButton

// why do this? to use preview. ViewModel hoisted out
@Composable
fun GenderScreen(
    viewModel: GenderViewModel = hiltViewModel(),
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

    GenderSelect(
        selectedGender = viewModel.selectedGender,
        onGenderSelected = { viewModel.onGenderTapped(it) },
        onNextTapped = viewModel::onNextTapped
    )
}

@Composable
fun GenderSelect(
    selectedGender: Gender,
    onGenderSelected: (Gender) -> Unit,
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
                text = stringResource(id = R.string.whats_your_gender),
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            Row {
                SelectableButton(
                    text = stringResource(id = R.string.male),
                    isSelected = selectedGender == Gender.Male,
                    color = MaterialTheme.colors.primaryVariant,
                    selectedTextColor = Color.White,
                    textStyle = MaterialTheme.typography.button.copy(fontWeight = FontWeight.Normal)
                ) {
                    onGenderSelected(Gender.Male)
                }
                Spacer(modifier = Modifier.width(spacing.spaceMedium))
                SelectableButton(
                    text = stringResource(id = R.string.female),
                    isSelected = selectedGender == Gender.Female,
                    color = MaterialTheme.colors.primaryVariant,
                    selectedTextColor = Color.White,
                    textStyle = MaterialTheme.typography.button.copy(fontWeight = FontWeight.Normal)
                ) {
                    onGenderSelected(Gender.Female)
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
fun GenderPreview() {
    GenderSelect(
        selectedGender = Gender.Female,
        onGenderSelected = {}
    ) {

    }
}
