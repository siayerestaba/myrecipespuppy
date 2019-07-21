package com.iliaberlana.myrecipepuppy.ui.favorites

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.iliaberlana.myrecipepuppy.R
import com.iliaberlana.myrecipepuppy.ui.commons.toast
import com.iliaberlana.myrecipepuppy.ui.model.RecipeUI
import kotlinx.android.synthetic.main.recipe_list.*
import kotlinx.android.synthetic.main.recycler_withprogressbar_andtext.*
import org.koin.androidx.scope.currentScope


class FavoriteRecipesActivity : AppCompatActivity(), FavoriteRecipesView
{
    private val presenter: FavoritesRecipesPresenter by currentScope.inject()

    private lateinit var adapter: FavoriteRecipesAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    private var actionbar: ActionBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_withprogressbar_andtext)

        initActionBar()
        initializeRecyclerView()

        presenter.recipeView = this
        presenter.renderFavoriteRecipes()
    }

    private fun initActionBar() {
        actionbar = supportActionBar
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        actionbar!!.title = resources.getString(R.string.favorite_title)
    }

    private fun initializeRecyclerView() {
        adapter = FavoriteRecipesAdapter(presenter)
        recipes_recyclerview.adapter = adapter

        linearLayoutManager = LinearLayoutManager(this)
        recipes_recyclerview.layoutManager = linearLayoutManager
    }

    override fun listFavorites(favorites: List<RecipeUI>) {
        adapter.addAll(favorites)
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onDestroy() {
        presenter.onDestroy()

        super.onDestroy()
    }
}