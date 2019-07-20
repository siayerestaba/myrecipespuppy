package com.iliaberlana.myrecipepuppy.ui.search

import android.os.Bundle
import android.view.View
import com.iliaberlana.myrecipepuppy.ui.commons.BaseListActivity
import com.iliaberlana.myrecipepuppy.ui.commons.toast
import com.iliaberlana.myrecipepuppy.ui.listrecipe.ListRecipeView
import com.iliaberlana.myrecipepuppy.ui.model.RecipeUI
import kotlinx.android.synthetic.main.recycler_withprogressbar_andtext.*
import org.koin.android.scope.currentScope


class FavoriteRecipesActivity : BaseListActivity(), ListRecipeView {
    private val presenter: FavoritesRecipesPresenter by currentScope.inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        presenter.recipeView = this
    }

    override fun onDestroy() {
        presenter.onDestroy()

        super.onDestroy()
    }

    override fun listRecipes(recipes: List<RecipeUI>) {
        adapter.addAll(recipes)
    }

    override fun cleanRecipes() {
        adapter.clean()
    }

    override fun hideLoading() {
        recipes_progressbar.visibility = View.GONE
    }

    override fun showLoading() {
        recipes_progressbar.visibility = View.VISIBLE
    }

    override fun showToastMessage(stringId: Int) {
        this.toast(this, resources.getString(stringId))
    }

    override fun showErrorCase(stringId: Int) {
        recipes_texterror.text = resources.getString(stringId)
        recipes_texterror.visibility = View.VISIBLE
    }

    override fun hideErrorCase() {
        recipes_texterror.visibility = View.GONE
    }
}