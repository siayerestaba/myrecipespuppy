package com.iliaberlana.myrecipepuppy.ui

import arrow.core.Either
import com.iliaberlana.myrecipepuppy.R
import com.iliaberlana.myrecipepuppy.domain.entities.Recipe
import com.iliaberlana.myrecipepuppy.domain.exception.DomainError
import com.iliaberlana.myrecipepuppy.ui.search.SearchRecipeView
import com.iliaberlana.myrecipepuppy.ui.model.RecipeUI
import com.iliaberlana.myrecipepuppy.ui.search.SearchRecipesPresenter
import com.iliaberlana.myrecipepuppy.usecases.SaveFavorite
import com.iliaberlana.myrecipespuppy.usecases.SearchRecipes
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class SearchPresenterTest {
    private val searchRecipes = mockk<SearchRecipes>()
    private val saveFavorite = mockk<SaveFavorite>()
    private val presenter = SearchRecipesPresenter(searchRecipes, saveFavorite)
    private val view = mockk<SearchRecipeView>(relaxed = true)

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        presenter.recipeView = view

        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `should call hideErrorCase and listRecipes when returns a list of recipes`() {
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
            searchRecipes(any(), any())
        } returns Either.right(listRecipes)

        presenter.searchRecipesWithText("onion")

        coVerifyOrder {
            searchRecipes("onion", 1)
            presenter.recipeView?.hideErrorCase()
            presenter.recipeView?.listRecipes(expected)
        }
    }

    @Test
    fun `should call showLoading, cleanRecipes, execute searchRecipe with page 1 and same text and hideLoading in order when call searchRecipeWithText`() {
        coEvery {
            searchRecipes(any(), any())
        } returns Either.right(emptyList())

        presenter.page = 5
        presenter.searchRecipesWithText("onion")

        coVerifyOrder {
            presenter.recipeView?.showLoading()
            presenter.recipeView?.cleanRecipes()
            searchRecipes("onion", 1)
            presenter.recipeView?.hideLoading()
        }
    }

    @Test
    fun `should execute searchRecipe with one page more when call renderMoreRecipes`() {
        coEvery {
            searchRecipes(any(), any())
        } returns Either.right(emptyList())

        presenter.page = 5
        presenter.searchText = "onion"

        presenter.renderMoreRecipes()

        coVerify(exactly = 1) {
            searchRecipes("onion", 6)
        }
    }

    @Test
    fun `should execute saveFavorite with recipe when call addFavorite`() = runBlocking {
        val expected =
            Recipe(
                "Recipe title",
                "onion, garlic",
                "https://via.placeholder.com/150",
                "http://www.recipepuppy.com/150"
            )

        val actual =
            RecipeUI(
                "Recipe title",
                "onion, garlic",
                "https://via.placeholder.com/150",
                "http://www.recipepuppy.com/150",
                isFavorite = false,
                hasLactose = false
            )

        coEvery {
            saveFavorite(expected)
        }

        presenter.addFavorite(actual)

        coVerify(exactly = 1) {
            saveFavorite(expected)
        }
    }

    @Test
    fun `call showErrorCase with emptyListId when call searchRecipeWithText and returns NoRecipesException`() {
        coEvery {
            searchRecipes(any(), any())
        } returns Either.left(DomainError.NoRecipesException)

        presenter.searchRecipesWithText("oni")

        coVerify(exactly = 1) {
            presenter.recipeView?.showErrorCase(R.string.emptyList)
        }
    }

    @Test
    fun `call showErrorCase with noInternetConectionErrorId when call searchRecipeWithText and returns NoInternetConnectionException`() {
        coEvery {
            searchRecipes(any(), any())
        } returns Either.left(DomainError.NoInternetConnectionException)

        presenter.searchRecipesWithText("onion")

        coVerify(exactly = 1) {
            presenter.recipeView?.showErrorCase(R.string.noInternetConectionError)
        }
    }

    @Test
    fun `call showErrorCase with unknownExceptionId when call searchRecipeWithText and returns UnknownException`() {
        coEvery {
            searchRecipes(any(), any())
        } returns Either.left(DomainError.UnknownException)

        presenter.searchRecipesWithText("onion")

        coVerify(exactly = 1) {
            presenter.recipeView?.showErrorCase(R.string.unknownException)
        }
    }

    @Test
    fun `call showToastMessage with noMoreRecipesId when call renderMoreRecipes and returns NoMoreRecipesException`() {
        coEvery {
            searchRecipes(any(), any())
        } returns Either.left(DomainError.NoMoreRecipesException)

        presenter.page = 4
        presenter.searchText = "onion"
        presenter.renderMoreRecipes()

        coVerifyOrder {
            searchRecipes("onion", 5)
            presenter.recipeView?.showToastMessage(R.string.noMoreRecipes)
        }
    }

    @Test
    fun `call showToastMessage with noInternetConectionErrorId when call renderMoreRecipes and returns NoInternetConnectionException`() {
        coEvery {
            searchRecipes(any(), any())
        } returns Either.left(DomainError.NoInternetConnectionException)

        presenter.page = 4
        presenter.searchText = "onion"
        presenter.renderMoreRecipes()

        coVerifyOrder {
            searchRecipes("onion", 5)
            presenter.recipeView?.showToastMessage(R.string.noInternetConectionError)
        }
    }

    @Test
    fun `call showToastMessage with unknownExceptionId when call renderMoreRecipes and returns UnknownException`() {
        coEvery {
            searchRecipes(any(), any())
        } returns Either.left(DomainError.UnknownException)

        presenter.page = 4
        presenter.searchText = "onion"
        presenter.renderMoreRecipes()

        coVerifyOrder {
            searchRecipes("onion", 5)
            presenter.recipeView?.showToastMessage(R.string.unknownException)
        }
    }
}