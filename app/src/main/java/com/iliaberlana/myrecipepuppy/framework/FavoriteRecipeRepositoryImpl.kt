package com.iliaberlana.myrecipepuppy.framework

import arrow.core.Either
import com.iliaberlana.myrecipepuppy.domain.data.FavoriteRecipeRepository
import com.iliaberlana.myrecipepuppy.domain.entities.Recipe
import com.iliaberlana.myrecipepuppy.domain.exception.DomainError
import com.iliaberlana.myrecipepuppy.framework.local.RecipeDao
import com.iliaberlana.myrecipepuppy.framework.local.model.RecipeDbEntity

class FavoriteRecipeRepositoryImpl(
    private val recipeDao: RecipeDao
) : FavoriteRecipeRepository {

    override suspend fun getFavorites(): Either<DomainError, List<Recipe>> {
        try {
            val listRecipeLocal = recipeDao.getAll()

            if (listRecipeLocal.isEmpty()) return Either.left(DomainError.NoExistFavoriteException)

            return Either.right(listRecipeLocal.map { it.toDomain() })
        } catch (exception: Exception) {
            return Either.left(DomainError.CantGetRecipeFromDBException)
        }
    }

    override suspend fun saveFavorite(recipe: Recipe) {
        recipeDao.insert(RecipeDbEntity.fromDomain(recipe))
    }
}