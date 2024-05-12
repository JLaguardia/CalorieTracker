package com.prismsoft.onboarding_presentation.age

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
fun AgeScreen(
    viewModel: AgeViewModel = hiltViewModel(),
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

    AgeEntry(
        age = viewModel.age,
        onAgeEntered = viewModel::onAgeEnter,
        onNextTapped = viewModel::onNextTapped
    )
}

@Composable
fun AgeEntry(
    age: String,
    onAgeEntered: (String) -> Unit,
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
                text = stringResource(id = R.string.whats_your_age),
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            UnitTextField(
                value = age,
                unit = stringResource(id = R.string.years),
                onValueChange = onAgeEntered
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
