package com.iliaberlana.myrecipepuppy.ui.search

import com.iliaberlana.myrecipepuppy.ui.BaseView
import com.iliaberlana.myrecipepuppy.ui.model.RecipeUI

interface SearchRecipeView: BaseView {
    fun listRecipes(recipes: List<RecipeUI>)
    fun cleanRecipes()
    fun cleanSearchBar()
}