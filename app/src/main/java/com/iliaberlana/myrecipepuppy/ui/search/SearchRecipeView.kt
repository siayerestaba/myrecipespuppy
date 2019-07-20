package com.iliaberlana.myrecipepuppy.ui.search

import com.iliaberlana.myrecipepuppy.domain.entities.Recipe
import com.iliaberlana.myrecipepuppy.ui.BaseView

interface SearchRecipeView: BaseView {
    fun listRecipes(recipes: List<Recipe>)
    fun cleanRecipes()
    fun cleanSearchBar()
}