package com.iliaberlana.myrecipepuppy.usecases

import com.iliaberlana.myrecipepuppy.domain.data.FavoriteRecipeRepository
import com.iliaberlana.myrecipepuppy.domain.entities.Recipe
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SaveFavoriteTest {

    @Test
    fun `call FavoriteRecipeRepository when execute saveRecipe with same parameters`() = runBlocking {
        val recipe = Recipe(
            "Recipe title",
            "onion, garlic",
            "https://via.placeholder.com/150",
            "http://www.recipepuppy.com/150")

        val favRecipeRepository = mockk<FavoriteRecipeRepository>(relaxed = true)
        val saveFavorite = SaveFavorite(favRecipeRepository)

        saveFavorite(recipe)

        coVerify { favRecipeRepository.saveFavorite(recipe) }
    }
}