package com.iliaberlana.myrecipepuppy.ui.listrecipe

import com.iliaberlana.myrecipepuppy.ui.commons.BaseView
import com.iliaberlana.myrecipepuppy.ui.model.RecipeUI

interface ListRecipeView: BaseView {
    fun listRecipes(recipes: List<RecipeUI>)
    fun cleanRecipes()
}