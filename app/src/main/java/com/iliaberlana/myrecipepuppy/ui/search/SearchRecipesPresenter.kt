package com.iliaberlana.myrecipepuppy.ui.search

import com.iliaberlana.myrecipespuppy.usecases.SearchRecipes
import kotlinx.coroutines.*

class SearchRecipesPresenter(
    private val searchRecipes: SearchRecipes
) {
    var recipeView: SearchRecipeView? = null
    var page: Int = 1
    var searchText: String = ""

    fun searchRecipesWithText(text: String) {
        searchText = text

        searchRecipes()
    }

    fun renderMoreRecipes() {
        page = page.plus(1)

        searchRecipes()
    }

    private fun searchRecipes() {
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) { searchRecipes(searchText, page) }
        }
    }

    fun onDestroy() {
        recipeView = null
    }
}