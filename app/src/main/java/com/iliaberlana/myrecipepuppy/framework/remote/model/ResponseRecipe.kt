package com.iliaberlana.myrecipepuppy.framework.remote.model

data class ResponseRecipe(
    val title: String,
    val version: String,
    val href: String,
    val results: List<RecipeRemote>
)