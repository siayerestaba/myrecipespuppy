package com.iliaberlana.myrecipepuppy.framework.remote

import com.iliaberlana.myrecipepuppy.domain.exception.DomainError
import com.iliaberlana.myrecipepuppy.framework.remote.model.RecipeRemote
import org.koin.core.KoinComponent
import org.koin.core.inject

class RecipeDataSource : KoinComponent
{
    private val URL_BASE = "http://www.recipepuppy.com"
    private val network : NetworkFactory by inject()

    suspend fun searchRecipes(ingredients: String, page: Int): List<RecipeRemote> {
        try {
            val recipeApi = network.createApi(RecipeClient::class.java, URL_BASE) // TODO Desacoplar

            val reponseMovieDB = recipeApi.searchRecipes(ingredients, page)
            return reponseMovieDB.results
        } catch (error: Error) {
            throw DomainError.NoInternetConnectionException
        }
    }
}