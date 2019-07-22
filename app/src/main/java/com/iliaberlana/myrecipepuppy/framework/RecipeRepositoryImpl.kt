package com.iliaberlana.myrecipepuppy.framework

import arrow.core.Either
import com.iliaberlana.myrecipepuppy.domain.data.RecipeRepository
import com.iliaberlana.myrecipepuppy.domain.entities.Recipe
import com.iliaberlana.myrecipepuppy.domain.exception.DomainError
import com.iliaberlana.myrecipepuppy.framework.remote.RecipeRemoteDataSource

class RecipeRepositoryImpl(
    private val recipeRemoteDataSource: RecipeRemoteDataSource
) : RecipeRepository
{
    override suspend fun searchRecipes(ingredients: String, page: Int): Either<DomainError, List<Recipe>> {
        try {
            val listRecipeRemote = recipeRemoteDataSource.searchRecipes(ingredients, page)

            if (listRecipeRemote.isEmpty()) return Either.left(DomainError.NoRecipesException)

            return Either.right(listRecipeRemote.map { it.toDomain() })
        } catch (noInternetConnectionException: DomainError) {
            return Either.left(DomainError.NoInternetConnectionException)
        } catch (exception: Exception) {
            return Either.left(DomainError.UnknownException)
        }
    }

    override fun showRecipe(recipe: Recipe) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}