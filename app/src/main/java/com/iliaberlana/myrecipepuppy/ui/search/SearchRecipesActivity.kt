package com.iliaberlana.myrecipepuppy.ui.search

import android.os.Bundle
import android.support.v7.widget.SearchView
import android.support.v7.widget.StaggeredGridLayoutManager
import com.iliaberlana.myrecipepuppy.R
import com.iliaberlana.myrecipepuppy.domain.entities.Recipe
import com.iliaberlana.myrecipepuppy.ui.BaseListActivity
import kotlinx.android.synthetic.main.recycler_withprogressbar_andtext.*
import org.koin.android.scope.currentScope
import android.view.Menu
import com.iliaberlana.myrecipepuppy.ui.commons.logDebug


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

        val layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
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
                // TODO Do your search
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                this.javaClass.name.logDebug("********* onQueryTextChange ${newText.length}")

                if(newText.length > 3) {
                    presenter.searchRecipesWithText(newText)
                }

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

    override fun listRecipes(recipes: List<Recipe>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun cleanRecipes() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun cleanSearchBar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showToastMessage(stringId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showErrorCase(stringId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideErrorCase() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}