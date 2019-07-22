package com.iliaberlana.myrecipepuppy.framework.remote.model

data class RecipeRemote(
    val title: String,
    val href: String,
    val ingredients: String,
    val thumbnail: String
)