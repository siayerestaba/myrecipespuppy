package com.iliaberlana.myrecipepuppy.ui.model

data class RecipeUI (
    val name: String,
    val ingredients : String,
    val imageUrl : String,
    val link : String,
    val isFavorite : Boolean
){
}