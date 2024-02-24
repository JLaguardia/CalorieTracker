package com.prismsoft.core.domain.model

sealed class Gender(val name: String) {
    object Male: Gender("male")
    object Female: Gender("female")

    companion object {
        fun fromString(name: String?) = when(name){
            "male" -> Male
            else -> Female
        }
    }
}