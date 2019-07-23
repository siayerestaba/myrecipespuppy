package com.iliaberlana.myrecipepuppy.usecases

import com.iliaberlana.myrecipepuppy.domain.data.FavoriteRecipeRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class ShowFavoriteRecipesTest {

    @Test
    fun `call FavoriteRecipeRepository when execute showFavorites`() = runBlocking {
        val favRecipeRepository = mockk<FavoriteRecipeRepository>(relaxed = true)
        val showFavoriteRecipes = ShowFavoriteRecipes(favRecipeRepository)

        showFavoriteRecipes()

        coVerify { favRecipeRepository.getFavorites() }
    }
}