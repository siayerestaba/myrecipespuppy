package com.iliaberlana.myrecipepuppy.ui.search

import android.os.Bundle
import com.iliaberlana.myrecipepuppy.R
import com.iliaberlana.myrecipepuppy.ui.BaseListActivity
import org.koin.android.scope.currentScope

class SearchRecipesActivity : BaseListActivity() {
    private val presenter: SearchRecipesPresenter by currentScope.inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.recycler_withprogressbar_andtext)

//        initializeRecyclerView()
    }
}