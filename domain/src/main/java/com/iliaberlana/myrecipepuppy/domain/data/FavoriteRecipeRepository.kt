package com.iliaberlana.myrecipepuppy.domain.data

import arrow.core.Either
import com.iliaberlana.myrecipepuppy.domain.entities.Recipe
import com.iliaberlana.myrecipepuppy.domain.exception.RoomError

interface FavoriteRecipeRepository {
    suspend fun getFavorites(): Either<RoomError, List<Recipe>>

    suspend fun saveFavorite(recipe: Recipe)

    suspend fun deleteFavorite(recipe: Recipe)
}