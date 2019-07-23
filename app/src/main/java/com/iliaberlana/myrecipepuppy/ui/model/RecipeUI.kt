package com.iliaberlana.myrecipepuppy.ui.model

import com.iliaberlana.myrecipepuppy.domain.entities.Recipe
import com.iliaberlana.myrecipepuppy.ui.commons.cleanHexCaracters

data class RecipeUI (
    val name: String,
    val ingredients : String,
    val imageUrl : String,
    val link : String,
    val isFavorite : Boolean,
    val hasLactose : Boolean
) {
    fun toDomain(): Recipe = Recipe(name, ingredients, imageUrl, link)

    companion object {
        fun fromDomain(recipe: Recipe): RecipeUI {
            val hasLactose = recipe.ingredients.contains("milk") || recipe.ingredients.contains("cheese")

            return RecipeUI(recipe.name.cleanHexCaracters(), recipe.ingredients, recipe.imageUrl, recipe.link, false, hasLactose)
        }
    }
}