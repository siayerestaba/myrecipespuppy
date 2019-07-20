package com.iliaberlana.myrecipepuppy.framework

import arrow.core.Either
import com.iliaberlana.myrecipepuppy.domain.data.FavoriteRecipeRepository
import com.iliaberlana.myrecipepuppy.domain.entities.Recipe
import com.iliaberlana.myrecipepuppy.domain.exception.RoomError
import com.iliaberlana.myrecipepuppy.framework.local.RecipeDao
import com.iliaberlana.myrecipepuppy.framework.local.model.RecipeDB
import com.iliaberlana.myrecipepuppy.framework.local.model.toRecipe

class FavoriteRecipeRepositoryImpl(
    private val recipeDao: RecipeDao
) : FavoriteRecipeRepository {

    override suspend fun getFavorites(): Either<RoomError, List<Recipe>> {
        try {
            val listRecipeLocal = recipeDao.getAll()

            if (listRecipeLocal.isEmpty()) return Either.left(RoomError.NoExistFavoriteException)

            return Either.right(listRecipeLocal.map { it.toRecipe() })
        } catch (exception: Exception) {
            return Either.left(RoomError.CantGetRecipeFromDBException)
        }
    }

    override suspend fun saveFavorite(recipe: Recipe) {
        recipeDao.insert(fromRecipeToRecipeDB(recipe))
    }

    override suspend fun deleteFavorite(recipe: Recipe) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun fromRecipeToRecipeDB(recipe: Recipe): RecipeDB {
        return RecipeDB(recipe.name, recipe.ingredients, recipe.imageUrl, recipe.link)
    }
}