package com.iliaberlana.myrecipepuppy.framework.remote

import com.iliaberlana.myrecipepuppy.domain.exception.DomainError
import com.iliaberlana.myrecipepuppy.framework.remote.model.RecipeRemote
import org.koin.core.KoinComponent
import org.koin.core.inject

class RecipeRemoteDataSource(
    private val network : NetworkFactory
)
{
    var urlBase = "http://www.recipepuppy.com"

    suspend fun searchRecipes(ingredients: String, page: Int): List<RecipeRemote> {
        try {
            val recipeApi = network.createApi(RecipeClient::class.java, urlBase)

            val reponseMovieDB = recipeApi.searchRecipes(ingredients, page)
            return reponseMovieDB.results
        } catch (error: Error) {
            throw DomainError.NoInternetConnectionException
        } catch (error: Exception) {
            throw DomainError.NoInternetConnectionException
        }
    }
}