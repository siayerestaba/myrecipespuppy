package com.iliaberlana.myrecipepuppy.framework.remote

import com.iliaberlana.myrecipepuppy.framework.remote.model.ResponseRecipe
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeClient {
    @GET("/api/")
    suspend fun searchRecipes(
        @Query("i") ingredients: String,
        @Query("p") page: Int
    ): ResponseRecipe
}