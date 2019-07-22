package com.iliaberlana.myrecipepuppy.domain.entities

data class Recipe(
    val name : String,
    val ingredients : String,
    val imageUrl : String,
    val link : String
)