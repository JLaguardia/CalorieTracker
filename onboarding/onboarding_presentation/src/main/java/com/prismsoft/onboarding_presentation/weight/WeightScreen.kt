package com.prismsoft.onboarding_presentation.weight

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
import androidx.hilt.navigation.compose.hiltViewModel
import com.prismsoft.core.R
import com.prismsoft.core.util.UiEvent
import com.prismsoft.core_ui.LocalSpacing
import com.prismsoft.core_ui.components.CenteredColumn
import com.prismsoft.onboarding_presentation.components.ActionButton
import com.prismsoft.onboarding_presentation.components.UnitTextField

@Composable
fun WeightScreen(
    viewModel: WeightViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState,
    onNavigate: (UiEvent.Navigate) -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
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

    WeightEntry(
        weight = viewModel.weight,
        onWeightEntered = viewModel::onWeightEnter,
        onNextTapped = viewModel::onNextTapped
    )
}

@Composable
fun WeightEntry(
    weight: String,
    onWeightEntered: (String) -> Unit,
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
                text = stringResource(id = R.string.whats_your_weight),
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            UnitTextField(
                value = weight,
                unit = stringResource(id = R.string.kg),
                onValueChange = onWeightEntered
            )
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
