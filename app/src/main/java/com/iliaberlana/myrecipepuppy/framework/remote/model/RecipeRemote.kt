package com.iliaberlana.myrecipepuppy.framework.remote.model

import com.iliaberlana.myrecipepuppy.domain.entities.Recipe

data class RecipeRemote(
    val title: String,
    val href: String,
    val ingredients: String,
    val thumbnail: String
){
    fun toDomain(): Recipe = Recipe(title, ingredients, thumbnail, href)
}