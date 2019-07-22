package com.iliaberlana.myrecipepuppy.ui.search

import com.iliaberlana.myrecipepuppy.R
import com.iliaberlana.myrecipepuppy.domain.exception.DomainError
import com.iliaberlana.myrecipepuppy.ui.model.RecipeUI
import com.iliaberlana.myrecipepuppy.usecases.SaveFavorite
import com.iliaberlana.myrecipespuppy.usecases.SearchRecipes
import kotlinx.coroutines.*

class SearchRecipesPresenter(
    private val searchRecipes: SearchRecipes,
    private val saveFavorite: SaveFavorite
) : CoroutineScope by MainScope() {
    var recipeView: SearchRecipeView? = null
    var page: Int = 1
    var searchText: String = ""
    var isLoadingData = false

    fun searchRecipesWithText(text: String) {
        page = 1
        searchText = text

        recipeView?.showLoading()

        recipeView?.cleanRecipes()
        searchRecipes()
    }

    fun renderMoreRecipes() {
        page = page.plus(1)

        searchRecipes()
    }

    fun addFavorite(recipeUI: RecipeUI) {
        val recipe = recipeUI.toDomain()

        launch {
            saveFavorite(recipe)
            recipeView?.showToastMessage(R.string.favorite_saved)
        }
    }

    fun onRecipeClicked(recipeUI: RecipeUI) = recipeView?.showRecipe(recipeUI)

    private fun searchRecipes() = launch {
        isLoadingData = true
        val resultRecipes = withContext(Dispatchers.IO) { searchRecipes(searchText, page) }
        resultRecipes.fold({
            when (it) {
                is DomainError.NoRecipesException -> showErrorMessage(R.string.emptyList)
                is DomainError.NoMoreRecipesException -> showErrorMessage(R.string.noMoreRecipes)
                is DomainError.NoInternetConnectionException -> showErrorMessage(R.string.noInternetConectionError)
                is DomainError.UnknownException -> showErrorMessage(R.string.unknownException)
            }
        }, { listRecipes ->
            recipeView?.hideErrorCase()
            recipeView?.listRecipes(listRecipes.map { RecipeUI.fromDomain(it) })
        })

        isLoadingData = false
        recipeView?.hideLoading()
    }

    private fun showErrorMessage(stringIdError: Int) {
        if (page == 1) {
            recipeView?.showErrorCase(stringIdError)
        } else {
            recipeView?.showToastMessage(stringIdError)
        }
    }

    fun onDestroy() {
        recipeView = null
    }
}