package com.iliaberlana.myrecipepuppy

import arrow.core.Either
import com.iliaberlana.myrecipepuppy.domain.exception.DomainError
import com.iliaberlana.myrecipepuppy.ui.search.SearchRecipeView
import com.iliaberlana.myrecipepuppy.ui.search.SearchRecipesPresenter
import com.iliaberlana.myrecipespuppy.usecases.SearchRecipes
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class SearchPresenterTest {
    private val searchRecipes = mockk<SearchRecipes>()
    private val presenter = SearchRecipesPresenter(searchRecipes)
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
    fun `should execute searchRecipe with page 1 and same text when call searchRecipeWithText`() {
        coEvery {
            searchRecipes(any(), any())
        } returns Either.right(emptyList())

        presenter.page = 5
        presenter.searchRecipesWithText("onion")

        coVerify(exactly = 1) {
            searchRecipes("onion", 1)
        }
    }

    @Test
    fun `should execute searchRecipe when call cleanRecipe in View`() {
        coEvery {
            searchRecipes(any(), any())
        } returns Either.right(emptyList())

        presenter.searchRecipesWithText("onion")

        verify(exactly = 1) {
            presenter.recipeView?.cleanRecipes()
        }
    }

    @Test
    fun `call showErrorCase with emptyListId when returns NoRecipesException and call searchRecipeWithText`() {
        coEvery {
            searchRecipes(any(), any())
        } returns Either.left(DomainError.NoRecipesException)

        presenter.searchRecipesWithText("oni")

        coVerify(exactly = 1) {
            presenter.recipeView?.showErrorCase(R.string.emptyList)
        }
    }

    @Test
    fun `call showErrorCase with noMoreRecipes Id when returns NoMoreRecipesException and call searchRecipeWithText`() {
        coEvery {
            searchRecipes(any(), any())
        } returns Either.left(DomainError.NoMoreRecipesException)

        presenter.searchRecipesWithText("onion")

        coVerify(exactly = 1) {
            presenter.recipeView?.showErrorCase(R.string.noMoreRecipes)
        }
    }

    @Test
    fun `call showErrorCase with noInternetConectionError Id when returns NoInternetConnectionException and call searchRecipeWithText`() {
        coEvery {
            searchRecipes(any(), any())
        } returns Either.left(DomainError.NoInternetConnectionException)

        presenter.searchRecipesWithText("onion")

        coVerify(exactly = 1) {
            presenter.recipeView?.showErrorCase(R.string.noInternetConectionError)
        }
    }

    @Test
    fun `call showErrorCase with unknownException Id when returns UnknownException and call searchRecipeWithText`() {
        coEvery {
            searchRecipes(any(), any())
        } returns Either.left(DomainError.UnknownException)

        presenter.searchRecipesWithText("onion")

        coVerify(exactly = 1) {
            presenter.recipeView?.showErrorCase(R.string.unknownException)
        }
    }

    @Test
    fun `call showToastMessage with emptyList Id when returns NoRecipesException and page isn't 1`() {
        coEvery {
            searchRecipes(any(), any())
        } returns Either.left(DomainError.NoRecipesException)

        presenter.page = 4
        presenter.searchText = "onion"
        presenter.renderMoreRecipes()

        coVerify(exactly = 1) {
            presenter.recipeView?.showToastMessage(R.string.emptyList)
        }
    }

    @Test
    fun `call showToastMessage with noMoreRecipes Id when returns NoMoreRecipesException and page isn't 1`() {
        coEvery {
            searchRecipes(any(), any())
        } returns Either.left(DomainError.NoMoreRecipesException)

        presenter.page = 4
        presenter.searchText = "onion"
        presenter.renderMoreRecipes()

        coVerify(exactly = 1) {
            presenter.recipeView?.showToastMessage(R.string.noMoreRecipes)
        }
    }

    @Test
    fun `call showToastMessage with noInternetConectionError Id when returns NoInternetConnectionException and page isn't 1`() {
        coEvery {
            searchRecipes(any(), any())
        } returns Either.left(DomainError.NoInternetConnectionException)

        presenter.page = 4
        presenter.searchText = "onion"
        presenter.renderMoreRecipes()

        coVerify(exactly = 1) {
            presenter.recipeView?.showToastMessage(R.string.noInternetConectionError)
        }
    }

    @Test
    fun `call showToastMessage with unknownException Id when returns UnknownException and page isn't 1`() {
        coEvery {
            searchRecipes(any(), any())
        } returns Either.left(DomainError.UnknownException)

        presenter.page = 4
        presenter.searchText = "onion"
        presenter.renderMoreRecipes()

        coVerify(exactly = 1) {
            presenter.recipeView?.showToastMessage(R.string.unknownException)
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
    fun `should call show loading when search recipes with text`() {
        coEvery {
            searchRecipes(any(), any())
        } returns Either.right(emptyList())

        presenter.searchRecipesWithText("onion")

        verify(exactly = 1) {
            presenter.recipeView?.showLoading()
        }
    }

    @Test
    fun `should call hide loading when search recipes was execute`() {
        coEvery {
            searchRecipes(any(), any())
        } returns Either.right(emptyList())

        presenter.searchRecipesWithText("onion")

        coVerify(exactly = 1) {
            presenter.recipeView?.hideLoading()
        }
    }
}