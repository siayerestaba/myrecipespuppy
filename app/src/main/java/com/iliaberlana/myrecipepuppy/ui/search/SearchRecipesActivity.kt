package com.iliaberlana.myrecipepuppy.ui.search

import android.os.Bundle
import android.support.v7.widget.StaggeredGridLayoutManager
import com.iliaberlana.myrecipepuppy.R
import com.iliaberlana.myrecipepuppy.domain.entities.Recipe
import com.iliaberlana.myrecipepuppy.ui.BaseListActivity
import kotlinx.android.synthetic.main.recycler_withprogressbar_andtext.*
import org.koin.android.scope.currentScope

class SearchRecipesActivity : BaseListActivity(), SearchView {
    private val presenter: SearchRecipesPresenter by currentScope.inject()

    private lateinit var adapter: RecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.recycler_withprogressbar_andtext)

        initializeRecyclerView()
    }

    private fun initializeRecyclerView() {
        adapter = RecipeAdapter({presenter.renderMoreRecipes()})

        val layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS

        recipes_recyclerview.adapter = adapter
        recipes_recyclerview.layoutManager = layoutManager
    }

    override fun onResume() {
        super.onResume()
        presenter.view = this
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