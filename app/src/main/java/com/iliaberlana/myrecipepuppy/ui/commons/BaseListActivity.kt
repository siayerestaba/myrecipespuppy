package com.iliaberlana.myrecipepuppy.ui.commons

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.iliaberlana.myrecipepuppy.R
import com.iliaberlana.myrecipepuppy.ui.listrecipe.RecipeAdapter
import kotlinx.android.synthetic.main.recycler_withprogressbar_andtext.*

abstract class BaseListActivity : AppCompatActivity() {

    protected lateinit var adapter: RecipeAdapter
    protected lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.recycler_withprogressbar_andtext)
    }

    protected fun initializeRecyclerView() {
        adapter = RecipeAdapter()

        linearLayoutManager = LinearLayoutManager(this)

        recipes_recyclerview.adapter = adapter
        recipes_recyclerview.layoutManager = linearLayoutManager
    }
}