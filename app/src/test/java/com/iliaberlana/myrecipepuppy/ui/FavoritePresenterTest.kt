package com.iliaberlana.myrecipepuppy.ui

import arrow.core.Either
import com.iliaberlana.myrecipepuppy.R
import com.iliaberlana.myrecipepuppy.domain.entities.Recipe
import com.iliaberlana.myrecipepuppy.domain.exception.DomainError
import com.iliaberlana.myrecipepuppy.ui.favorites.FavoriteRecipesView
import com.iliaberlana.myrecipepuppy.ui.favorites.FavoritesRecipesPresenter
import com.iliaberlana.myrecipepuppy.ui.model.RecipeUI
import com.iliaberlana.myrecipepuppy.usecases.ShowFavoriteRecipes
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class FavoritePresenterTest {
    private val showFavoriteRecipes = mockk<ShowFavoriteRecipes>()
    private val view = mockk<FavoriteRecipesView>(relaxed = true)
    private lateinit var presenter : FavoritesRecipesPresenter

    @Before
    fun setUp() {
        presenter = FavoritesRecipesPresenter(showFavoriteRecipes)
        presenter.recipeView = view

        Dispatchers.setMain(TestCoroutineDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should call showLoading, execute showFavoriteRecipes and hideLoading in order when call renderFavoriteRecipes`() = runBlocking {
        coEvery {
            showFavoriteRecipes()
        } returns Either.right(emptyList())

        presenter.renderFavoriteRecipes()

        coVerifyOrder {
            presenter.recipeView?.showLoading()
            showFavoriteRecipes()
            presenter.recipeView?.hideLoading()
        }
    }

    @Test
    fun `should call hideErrorCase and listRecipes when returns a list of recipes`() = runBlocking {
        val listRecipes = listOf(
            Recipe(
                "Recipe title",
                "onion, garlic",
                "https://via.placeholder.com/150",
                "http://www.recipepuppy.com/150"
            )
        )
        val expected = listOf(
            RecipeUI(
                "Recipe title",
                "onion, garlic",
                "https://via.placeholder.com/150",
                "http://www.recipepuppy.com/150",
                isFavorite = false,
                hasLactose = false
            )
        )

        coEvery {
            showFavoriteRecipes()
        } returns Either.right(listRecipes)

        presenter.renderFavoriteRecipes()

        verifyOrder {
            presenter.recipeView?.showLoading()
            presenter.recipeView?.hideErrorCase()
            presenter.recipeView?.listFavorites(expected)
        }
    }

    @Test
    fun `should call showFavorite when call onRecipeClicked with same recipe`() = runBlocking {
        val recipeUI =
            RecipeUI(
                "Recipe title",
                "onion, garlic",
                "https://via.placeholder.com/150",
                "http://www.recipepuppy.com/150",
                isFavorite = false,
                hasLactose = false
            )

        presenter.onRecipeClicked(recipeUI)

        verify {
            presenter.recipeView?.showRecipe(recipeUI)
        }
    }

    @Test
    fun `call showErrorCase with emptyFavoritestId when call renderFavoriteRecipes and returns NoExistFavoriteException`() = runBlocking {
        coEvery {
            showFavoriteRecipes()
        } returns Either.left(DomainError.NoExistFavoriteException)

        presenter.renderFavoriteRecipes()

        verify(exactly = 1) {
            presenter.recipeView?.showErrorCase(R.string.emptyFavorites)
        }
    }

    @Test
    fun `call showErrorCase with unknownExceptionId when call renderFavoriteRecipes and returns CantGetRecipeFromDBException`() = runBlocking {
        coEvery {
            showFavoriteRecipes()
        } returns Either.left(DomainError.CantGetRecipeFromDBException)

        presenter.renderFavoriteRecipes()

        verify(exactly = 1) {
            presenter.recipeView?.showErrorCase(R.string.unknownException)
        }
    }
}