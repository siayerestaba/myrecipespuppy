package com.iliaberlana.myrecipepuppy.domain.data

import com.iliaberlana.myrecipepuppy.domain.entities.Recipe

interface FavoriteRecipeRepository {
    fun showFavorites()

    fun saveFavorite(recipe: Recipe)

    fun deleteFavorite(recipe: Recipe)
}