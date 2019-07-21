package com.iliaberlana.myrecipepuppy.ui.favorites

import com.iliaberlana.myrecipepuppy.R
import com.iliaberlana.myrecipepuppy.domain.exception.RoomError
import com.iliaberlana.myrecipepuppy.ui.model.RecipeUI
import com.iliaberlana.myrecipespuppy.usecases.ShowFavoriteRecipes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoritesRecipesPresenter(
    private val showFavoriteRecipes: ShowFavoriteRecipes
) {
    var recipeView: FavoriteRecipesView? = null

    fun renderFavoriteRecipes() {
        getFavoriteRecipes()
    }

    private fun getFavoriteRecipes() {
        GlobalScope.launch(Dispatchers.Main) {
            val resultRecipes = withContext(Dispatchers.IO) { showFavoriteRecipes() }
            resultRecipes.fold({
                when (it) {
                    RoomError.NoExistFavoriteException -> showErrorMessage(R.string.emptyFavorites)
                    RoomError.CantGetRecipeFromDBException -> showErrorMessage(R.string.unknownException)
                }
            }, { listRecipes ->
                recipeView?.hideErrorCase()
                recipeView?.listFavorites(listRecipes.map { RecipeUI.fromDomain(it) })
            })

            recipeView?.hideLoading()
        }
    }

    private fun showErrorMessage(stringIdError: Int) {
        recipeView?.showErrorCase(stringIdError)
    }

    fun onDestroy() {
        recipeView = null
    }
}