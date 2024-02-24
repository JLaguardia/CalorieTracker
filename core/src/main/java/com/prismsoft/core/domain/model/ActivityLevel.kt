package com.prismsoft.core.domain.model

sealed class ActivityLevel(val name: String) {
    object Low: ActivityLevel("Low")
    object Medium: ActivityLevel("Medium")
    object High: ActivityLevel("High")

    companion object {
        fun fromString(name: String?) = when(name){
            "Low" -> Low
            "Medium" -> Medium
            else -> High
        }
    }
}