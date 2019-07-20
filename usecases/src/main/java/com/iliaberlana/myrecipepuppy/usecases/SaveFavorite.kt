package com.iliaberlana.myrecipepuppy.usecases

import com.iliaberlana.myrecipepuppy.domain.data.FavoriteRecipeRepository
import com.iliaberlana.myrecipepuppy.domain.entities.Recipe

class SaveFavorite (
    private val favoriteRecipeRepository: FavoriteRecipeRepository
) {
    suspend operator fun invoke(recipe: Recipe) = favoriteRecipeRepository.saveFavorite(recipe)
}