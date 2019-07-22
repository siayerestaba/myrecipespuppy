package com.iliaberlana.myrecipepuppy.framework.local

import com.iliaberlana.myrecipepuppy.domain.entities.Recipe
import com.iliaberlana.myrecipepuppy.domain.exception.DomainError
import com.iliaberlana.myrecipepuppy.framework.FavoriteRecipeRepositoryImpl
import com.iliaberlana.myrecipepuppy.framework.local.model.RecipeDbEntity
import io.kotlintest.assertions.arrow.either.shouldBeLeft
import io.kotlintest.assertions.arrow.either.shouldBeRight
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Test

class FavoriteRecipeRepositoryTest {
    private val recipeDao = mockk<RecipeDao>()
    private val favoriteRecipeRepositoryImpl = FavoriteRecipeRepositoryImpl(recipeDao)

    @Test
    fun `call RecipeDao_insert when call saveFavorite`() = runBlocking {
        val expected = RecipeDbEntity(
            null,
            "Recipe title",
            "onion, garlic",
            "https://via.placeholder.com/150",
            "http://www.recipepuppy.com/150"
        )

        coEvery { recipeDao.insert(expected) } just Runs

        favoriteRecipeRepositoryImpl.saveFavorite(Recipe(
            "Recipe title",
            "onion, garlic",
            "https://via.placeholder.com/150",
            "http://www.recipepuppy.com/150"
        ))

        coVerify { recipeDao.insert(expected) }
    }

    @Test
    fun `call RecipeDao_getAll when call getFavorites`() = runBlocking {
        coEvery { recipeDao.getAll() } returns emptyList()

        favoriteRecipeRepositoryImpl.getFavorites()

        coVerify { recipeDao.getAll() }
    }

    @Test
    fun `catch a exception and return Either with CantGetRecipeFromDBException error`() = runBlocking {
        coEvery { recipeDao.getAll() } throws DomainError.NoInternetConnectionException

        val actual = favoriteRecipeRepositoryImpl.getFavorites()

        actual.shouldBeLeft(DomainError.CantGetRecipeFromDBException)
    }

    @Test
    fun `return Either with NoExistFavoriteException error when receive a emptyList`() = runBlocking {
        coEvery { recipeDao.getAll() } returns emptyList()

        val actual = favoriteRecipeRepositoryImpl.getFavorites()

        actual.shouldBeLeft(DomainError.NoExistFavoriteException)
    }

    @Test
    fun `return Either rigth with items when the call is ok`() = runBlocking {
        val expected = listOf(
            Recipe(
                "Recipe title",
                "onion, garlic",
                "https://via.placeholder.com/150",
                "http://www.recipepuppy.com/150"
            )
        )

        coEvery { recipeDao.getAll() } returns listOf(
            RecipeDbEntity(
                1,
                "Recipe title",
                "onion, garlic",
                "https://via.placeholder.com/150",
                "http://www.recipepuppy.com/150"
            )
        )

        val actual = favoriteRecipeRepositoryImpl.getFavorites()

        actual.shouldBeRight(expected)

        Unit
    }
}