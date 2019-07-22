package com.iliaberlana.myrecipepuppy.usecases

import com.iliaberlana.myrecipepuppy.domain.data.RecipeRepository
import com.iliaberlana.myrecipespuppy.usecases.SearchRecipes
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SearchRecipesTest {

    @Test
    fun `call RecipeRepository when execute searchRecipe with same parameters`() = runBlocking {
        val recipeRepository = mockk<RecipeRepository>(relaxed = true)
        val searchRecipes = SearchRecipes(recipeRepository)

        searchRecipes("onion,garlic", 10)

        coVerify { recipeRepository.searchRecipes("onion,garlic", 10) }
    }
}