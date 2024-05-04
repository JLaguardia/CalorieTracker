package com.prismsoft.tracker_presentation.tracker_overview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.prismsoft.core.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DaySelector(
    date: LocalDate,
    onPreviousDayClick: () -> Unit,
    onNextDayClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onPreviousDayClick) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = stringResource(id = R.string.previous_day),
            )
        }

        Text(
            text = date.toRelativeString(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h2
        )

        IconButton(onClick = onNextDayClick) {
            Icon(
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = stringResource(id = R.string.next_day),
            )
        }

    }
}

@Composable
private fun LocalDate.toRelativeString(): String {
    val now = LocalDate.now()
    return when(this) {
        now -> stringResource(id = R.string.today)
        now.plusDays(1) -> stringResource(id = R.string.tomorrow)
        now.minusDays(1) -> stringResource(id = R.string.yesterday)
        else -> DateTimeFormatter.ofPattern("MMMM dd").format(this)
    }
}

@Preview(showSystemUi = true)
@Composable
private fun DaySelectorPreview() {
    val day = remember {
        mutableStateOf(LocalDate.now())
    }
    DaySelector(
        date = day.value,
        onPreviousDayClick = {
            day.value = day.value.minusDays(1)
        },
        onNextDayClick = {
            day.value = day.value.plusDays(1)
        },
        modifier = Modifier.fillMaxWidth()
    )
}