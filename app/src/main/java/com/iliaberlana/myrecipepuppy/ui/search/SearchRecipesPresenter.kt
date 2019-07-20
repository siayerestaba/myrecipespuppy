package com.iliaberlana.myrecipepuppy.ui.search

import com.iliaberlana.myrecipepuppy.R
import com.iliaberlana.myrecipepuppy.domain.exception.DomainError
import com.iliaberlana.myrecipepuppy.ui.listrecipe.ListRecipeView
import com.iliaberlana.myrecipepuppy.ui.model.RecipeUI
import com.iliaberlana.myrecipepuppy.ui.model.toRecipeUI
import com.iliaberlana.myrecipespuppy.usecases.SearchRecipes
import kotlinx.coroutines.*

class SearchRecipesPresenter(
    private val searchRecipes: SearchRecipes
) {
    var recipeView: ListRecipeView? = null
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

    private fun searchRecipes() {
        isLoadingData = true
        GlobalScope.launch(Dispatchers.Main) {
            val resultRecipes = withContext(Dispatchers.IO) { searchRecipes(searchText, page) }
            resultRecipes.fold({
                when(it) {
                    is DomainError.NoRecipesException -> showErrorMessage(R.string.emptyList)
                    is DomainError.NoMoreRecipesException -> showErrorMessage(R.string.noMoreRecipes)
                    is DomainError.NoInternetConnectionException -> showErrorMessage(R.string.noInternetConectionError)
                    is DomainError.UnknownException -> showErrorMessage(R.string.unknownException)
                }
            }, {listRecipes ->
                recipeView?.hideErrorCase()
                recipeView?.listRecipes(listRecipes.map {  it.toRecipeUI() })
            })

            isLoadingData = false
            recipeView?.hideLoading()
        }
    }

    fun addFavorite(recipeUI: RecipeUI) {

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