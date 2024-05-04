package com.prismsoft.tracker_presentation.tracker_overview.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.prismsoft.core_ui.components.CenteredColumn
import com.prismsoft.core.R
import com.prismsoft.tracker_presentation.components.UnitDisplay

@Composable
fun NutrientBarInfo(
    value: Int,
    goal: Int,
    name: String,
    color: Color,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 8.dp
) {
    val backgroundColor = MaterialTheme.colors.background
    val goalExceededColor = MaterialTheme.colors.error
    val angleRatio = remember {
        Animatable(0f)
    }
    
    LaunchedEffect(key1 = value) {
        angleRatio.animateTo(
            targetValue = if(goal > 0){
                value / goal.toFloat()
            } else { 0f },
            animationSpec = tween(durationMillis = 300)
        )
    }


    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            drawArc(
                color = if(value <= goal) {
                    backgroundColor
                } else {
                    goalExceededColor
                },
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                size = size,
                style = Stroke(
                    width = strokeWidth.toPx(),
                    cap = StrokeCap.Round
                )
            )
            if(value <= goal){
                drawArc(
                    color = color,
                    startAngle = 90f,
                    sweepAngle = 360f * angleRatio.value,
                    useCenter = false,
                    size = size,
                    style = Stroke(
                        width = strokeWidth.toPx(),
                        cap = StrokeCap.Round
                    )
                )
            }
        }

        CenteredColumn(Modifier.fillMaxWidth()) {
            UnitDisplay(
                amount = value,
                unit = stringResource(id = R.string.grams),
                amountColor = if(value <= goal) {
                    MaterialTheme.colors.onPrimary
                } else {
                    goalExceededColor
                },
                unitTextColor = if(value <= goal) {
                    MaterialTheme.colors.onPrimary
                } else {
                    goalExceededColor
                }
            )
            
            Text(
                text = name,
                color = if(value <= goal) {
                    MaterialTheme.colors.onPrimary
                } else {
                    goalExceededColor
                },
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun NutrientBarInfoPreview() {
    NutrientBarInfo(
        value = 100,
        goal = 150,
        name = "test",
        color = Color.Green
    )
}