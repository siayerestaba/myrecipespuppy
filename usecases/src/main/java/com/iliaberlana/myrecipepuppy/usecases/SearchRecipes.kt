package com.iliaberlana.myrecipespuppy.usecases

import arrow.core.Either
import com.iliaberlana.myrecipepuppy.domain.data.RecipeRepository
import com.iliaberlana.myrecipepuppy.domain.entities.Recipe
import com.iliaberlana.myrecipepuppy.domain.exception.DomainError

class SearchRecipes(
    private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(ingredients: String, page: Int): Either<DomainError, List<Recipe>> = recipeRepository.searchRecipes(ingredients, page)
}
