package com.prismsoft.tracker_presentation.tracker_overview.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.prismsoft.core_ui.CarbColor
import com.prismsoft.core_ui.FatColor
import com.prismsoft.core_ui.ProteinColor

@Composable
fun NutrientsBar(
    carbs: Int,
    protein: Int,
    fat: Int,
    calories: Int,
    calorieGoal: Int,
    modifier: Modifier = Modifier
) {
    val background = MaterialTheme.colors.background
    val caloriesExceededColor = MaterialTheme.colors.error
    val carbWidthRatio = remember {
        Animatable(0f)
    }
    val proteinWidthRatio = remember {
        Animatable(0f)
    }
    val fatWidthRatio = remember {
        Animatable(0f)
    }
    
    LaunchedEffect(key1 = carbs) {
        carbWidthRatio.animateTo(((carbs * 4f) / calorieGoal))
    }
    LaunchedEffect(key1 = protein) {
        proteinWidthRatio.animateTo(((protein * 4f) / calorieGoal))
    }
    LaunchedEffect(key1 = fat) {
        fatWidthRatio.animateTo(((fat * 9f) / calorieGoal))
    }

    Canvas(modifier = modifier) {
        if(calories <= calorieGoal){
            val carbsWidth = carbWidthRatio.value * size.width
            val proteinWidth = proteinWidthRatio.value * size.width
            val fatWidth = fatWidthRatio.value * size.width

            drawRoundRect(
                color = background,
                size = size,
                cornerRadius = CornerRadius(100f)
            )
            drawRoundRect(
                color = FatColor,
                size = size.copy(width = carbsWidth + proteinWidth + fatWidth),
                cornerRadius = CornerRadius(100f)
            )
            drawRoundRect(
                color = ProteinColor,
                size = size.copy(width = carbsWidth + proteinWidth),
                cornerRadius = CornerRadius(100f)
            )
            drawRoundRect(
                color = CarbColor,
                size = size.copy(width = carbsWidth),
                cornerRadius = CornerRadius(100f)
            )
        } else {
            drawRoundRect(
                color = caloriesExceededColor,
                size = size,
                cornerRadius = CornerRadius(100f)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun NutrientsBarPreview() {
    NutrientsBar(
        carbs = 200,
        protein = 50,
        fat = 20,
        calories = 2500,
        calorieGoal = 3000,
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .height(50.dp)
    )
}