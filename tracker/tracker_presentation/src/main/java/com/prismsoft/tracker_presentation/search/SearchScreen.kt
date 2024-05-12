package com.prismsoft.tracker_presentation.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.prismsoft.core.util.UiEvent
import com.prismsoft.core_ui.LocalSpacing
import com.prismsoft.core.R
import com.prismsoft.tracker_domain.model.MealType
import com.prismsoft.tracker_presentation.search.components.SearchTextField
import com.prismsoft.tracker_presentation.search.components.TrackableFoodItem
import java.time.LocalDate

@Composable
fun SearchScreen(
    scaffoldState: ScaffoldState,
    mealName: String,
    dayOfMonth: Int,
    month: Int,
    year: Int,
    onNavigateUp: () -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val state = viewModel.state
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = keyboardController) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                UiEvent.NavigateUp -> onNavigateUp()
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        event.message.asString(context)
                    )
                    keyboardController?.hide()
                }

                else -> Unit
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceMedium)
    ) {
        Text(
            text = stringResource(
                id = R.string.add_meal,
                mealName
            ),
            style = MaterialTheme.typography.h2
        )
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        SearchTextField(
            text = state.query,
            onValueChanged = { viewModel.onEvent(SearchEvent.OnQueryChanged(it)) },
            onSearch = {
                keyboardController?.hide()
                viewModel.onEvent(SearchEvent.OnSearch)
            },
            onFocusChanged = { viewModel.onEvent(SearchEvent.OnSearchFocusChanged(it.isFocused)) },
            shouldShowHint = state.isHintVisible
        )
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        LazyColumn(Modifier.fillMaxSize()) {
            items(state.trackableFoods) { item ->
                TrackableFoodItem(
                    trackableFoodUiState = item,
                    onTap = { viewModel.onEvent(SearchEvent.OnToggleTrackableFood(item.food)) },
                    onAmountChanged = {
                        viewModel.onEvent(
                            SearchEvent.OnAmountForFoodChange(
                                item.food,
                                it
                            )
                        )
                    },
                    onTrack = {
                        keyboardController?.hide()
                        viewModel.onEvent(
                            SearchEvent.OnTrackFoodTap(
                                food = item.food,
                                mealType = MealType.fromString(mealName),
                                date = LocalDate.of(
                                    year,
                                    month,
                                    dayOfMonth
                                )
                            )
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when {
            state.isSearching -> CircularProgressIndicator()
            state.trackableFoods.isEmpty() -> {
                Text(
                    text = stringResource(id = R.string.no_results),
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

}