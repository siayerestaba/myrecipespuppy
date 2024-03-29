package com.iliaberlana.myrecipepuppy.usecases

import arrow.core.Either
import com.iliaberlana.myrecipepuppy.domain.data.FavoriteRecipeRepository
import com.iliaberlana.myrecipepuppy.domain.entities.Recipe
import com.iliaberlana.myrecipepuppy.domain.exception.DomainError

class ShowFavoriteRecipes(
    private val favoriteRecipeRepository: FavoriteRecipeRepository
) {
    suspend operator fun invoke(): Either<DomainError, List<Recipe>> = favoriteRecipeRepository.getFavorites()
}