package com.iliaberlana.myrecipepuppy

import arrow.core.Either
import com.iliaberlana.myrecipepuppy.ui.search.SearchRecipesPresenter
import com.iliaberlana.myrecipespuppy.usecases.SearchRecipes
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
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

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
//        presenter.view = viewMain
//
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `should execute searchRecipe with same parameters when call searchRecipeWithText`() {
        coEvery { searchRecipes("onion",1) } returns Either.right(emptyList())

        presenter.searchRecipesWithText("onion")

        coVerify { searchRecipes("onion", 1) }
    }

    @Test
    fun `should execute searchRecipe with one page more when call renderMoreRecipes`() {
        coEvery { searchRecipes("onion",6) } returns Either.right(emptyList())

        presenter.page = 5
        presenter.searchText = "onion"

        presenter.renderMoreRecipes()

        coVerify { searchRecipes("onion", 6) }
    }
}