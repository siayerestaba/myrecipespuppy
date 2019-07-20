package com.iliaberlana.myrecipepuppy.ui.search

import android.os.Bundle
import android.support.v7.widget.SearchView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.Menu
import android.view.View
import com.iliaberlana.myrecipepuppy.R
import com.iliaberlana.myrecipepuppy.ui.BaseListActivity
import com.iliaberlana.myrecipepuppy.ui.commons.logDebug
import com.iliaberlana.myrecipepuppy.ui.commons.toast
import com.iliaberlana.myrecipepuppy.ui.model.RecipeUI
import kotlinx.android.synthetic.main.recycler_withprogressbar_andtext.*
import org.koin.android.scope.currentScope


class SearchRecipesActivity : BaseListActivity(), SearchRecipeView {
    private val presenter: SearchRecipesPresenter by currentScope.inject()

    private lateinit var searchView: SearchView
    private lateinit var adapter: RecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.recycler_withprogressbar_andtext)

        initializeRecyclerView()
    }

    private fun initializeRecyclerView() {
        adapter = RecipeAdapter({ presenter.renderMoreRecipes() })

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS

        recipes_recyclerview.adapter = adapter
        recipes_recyclerview.layoutManager = layoutManager
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu.findItem(R.id.search)
        searchView = searchItem.actionView as SearchView
        searchView.queryHint = getString(R.string.search_hint)
        searching()

        return true
    }

    private fun searching() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                presenter.searchRecipesWithText(query)

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                /*if(newText.length > 3) { // TODO No tiene sentido, no va a encontrar nada!!
                    presenter.searchRecipesWithText(newText)
                }*/

                return false
            }
        })
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

    override fun cleanSearchBar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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