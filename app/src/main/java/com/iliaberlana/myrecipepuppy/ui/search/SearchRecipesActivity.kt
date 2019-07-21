package com.iliaberlana.myrecipepuppy.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iliaberlana.myrecipepuppy.R
import com.iliaberlana.myrecipepuppy.ui.commons.toast
import com.iliaberlana.myrecipepuppy.ui.detail.RecipeDetailActivity
import com.iliaberlana.myrecipepuppy.ui.favorites.FavoriteRecipesActivity
import com.iliaberlana.myrecipepuppy.ui.model.RecipeUI
import kotlinx.android.synthetic.main.recipe_list.*
import kotlinx.android.synthetic.main.recycler_withprogressbar_andtext.*
import org.koin.androidx.scope.currentScope


class SearchRecipesActivity : AppCompatActivity(), SearchRecipeView {
    private val presenter: SearchRecipesPresenter by currentScope.inject()

    private lateinit var adapter: SearchRecipeAdapter
    private lateinit var searchView: SearchView

    private lateinit var linearLayoutManager: LinearLayoutManager
    private val lastVisibleItemPosition: Int
        get() = linearLayoutManager.findLastVisibleItemPosition()

    private var twoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_withprogressbar_andtext)
        // TODO Comprobar si hay internet, si no hay, ir a la pantalla de Favoritos

        presenter.recipeView = this
        initializeRecyclerView()

        if (recipe_detail_container != null) {
            twoPane = true
        }
    }

    private fun initializeRecyclerView() {
        adapter = SearchRecipeAdapter(presenter)
        recipes_recyclerview.adapter = adapter

        linearLayoutManager = LinearLayoutManager(this)
        recipes_recyclerview.layoutManager = linearLayoutManager

        recipes_recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                val totalItemCount = recyclerView.layoutManager?.itemCount
                if (!presenter.isLoadingData && totalItemCount == lastVisibleItemPosition + 1) {
                    presenter.renderMoreRecipes()
                }
            }
        })
    }

    private fun searching() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                presenter.searchRecipesWithText(query)

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (!presenter.isLoadingData && newText.length > 3) {
                    presenter.searchRecipesWithText(newText)
                }

                return false
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu.findItem(R.id.search)
        searchView = searchItem.actionView as SearchView
        searchView.queryHint = getString(R.string.search_hint)
        searching()

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.favorites -> {
                val intent = Intent(this, FavoriteRecipesActivity::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun listRecipes(recipes: List<RecipeUI>) {
        adapter.addAll(recipes)
    }

    override fun cleanRecipes() {
        adapter.clean()
    }

    override fun showRecipe(recipeUI: RecipeUI) {
        RecipeDetailActivity.open(activity = this, recipeName = recipeUI.name, recipeLink = recipeUI.link)
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

    override fun onDestroy() {
        presenter.onDestroy()

        super.onDestroy()
    }
}