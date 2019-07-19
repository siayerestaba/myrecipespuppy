package com.iliaberlana.myrecipepuppy

import com.iliaberlana.myrecipepuppy.domain.entities.Recipe
import com.iliaberlana.myrecipepuppy.domain.exception.DomainError
import com.iliaberlana.myrecipepuppy.framework.RecipeRepositoryImpl
import com.iliaberlana.myrecipepuppy.framework.remote.RecipeDataSource
import com.iliaberlana.myrecipepuppy.framework.remote.model.RecipeRemote
import io.kotlintest.assertions.arrow.either.shouldBeLeft
import io.kotlintest.assertions.arrow.either.shouldBeRight
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class RecipeRepositoryTest {
    private val recipeDataSource = mockk<RecipeDataSource>()
    private val recipeRepositoryImpl = RecipeRepositoryImpl(recipeDataSource)

    @Test
    fun `call MovieDBClientService when call MoviesRepository with same parameters`() = runBlocking {
        coEvery { recipeDataSource.searchRecipes("onion,garlic", 1) } returns emptyList()

        recipeRepositoryImpl.searchRecipes("onion,garlic", 1)

        coVerify { recipeDataSource.searchRecipes("onion,garlic", 1) }
    }

    @Test
    fun `catch the NoInternetConnectionException and return Either with this error`() = runBlocking {
        coEvery {
            recipeDataSource.searchRecipes(
                "onion,garlic",
                1
            )
        } throws DomainError.NoInternetConnectionException

        val actual = recipeRepositoryImpl.searchRecipes("onion,garlic", 1)

        actual.shouldBeLeft(DomainError.NoInternetConnectionException)
    }

    @Test
    fun `return Either with NoMoreMoviesException error when receive a emptyList`() = runBlocking {
        coEvery { recipeDataSource.searchRecipes("onion,garlic", 1) } returns emptyList()

        val actual = recipeRepositoryImpl.searchRecipes("onion,garlic", 1)

        actual.shouldBeLeft(DomainError.NoRecipesException)
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

        coEvery { recipeDataSource.searchRecipes("onion,garlic", 1) } returns listOf(
            RecipeRemote(
                "Recipe title",
                "http://www.recipepuppy.com/150",
                "onion, garlic",
                "https://via.placeholder.com/150"
            )
        )

        val actual = recipeRepositoryImpl.searchRecipes("onion,garlic", 1)

        actual.shouldBeRight(expected)

        Unit
    }
}