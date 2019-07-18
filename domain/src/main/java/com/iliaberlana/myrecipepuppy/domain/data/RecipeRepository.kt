package com.iliaberlana.myrecipepuppy.domain.data

import arrow.core.Either
import com.iliaberlana.myrecipepuppy.domain.entities.Recipe
import com.iliaberlana.myrecipepuppy.domain.exception.DomainError

interface RecipeRepository {
    suspend fun searchRecipes(ingredients: String): Either<DomainError, List<Recipe>>

    fun showRecipe(recipe: Recipe)
}