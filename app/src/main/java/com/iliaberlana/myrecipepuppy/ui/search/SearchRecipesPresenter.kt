package com.iliaberlana.myrecipepuppy.ui.search

import com.iliaberlana.myrecipespuppy.usecases.SearchRecipes

class SearchRecipesPresenter(
    private val searchRecipes: SearchRecipes
) {
    var view: SearchView? = null
    var page: Int = 1

    fun create(){

    }

    fun renderMoreRecipes() {

    }

    fun onDestroy() {
        view = null
    }
}