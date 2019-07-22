package com.iliaberlana.myrecipepuppy.domain.data

import arrow.core.Either
import com.iliaberlana.myrecipepuppy.domain.entities.Recipe
import com.iliaberlana.myrecipepuppy.domain.exception.DomainError

interface FavoriteRecipeRepository {
    suspend fun getFavorites(): Either<DomainError, List<Recipe>>

    suspend fun saveFavorite(recipe: Recipe)

    suspend fun deleteFavorite(recipe: Recipe)
}