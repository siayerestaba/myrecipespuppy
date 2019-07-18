package com.iliaberlana.myrecipepuppy.domain.data

import com.iliaberlana.myrecipepuppy.domain.entities.Recipe

interface RecipeRepository {
    fun searchRecipes(ingredients: String): List<Recipe>

    fun showRecipe(recipe: Recipe)
}