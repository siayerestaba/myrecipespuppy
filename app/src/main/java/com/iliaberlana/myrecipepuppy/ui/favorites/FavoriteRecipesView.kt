package com.iliaberlana.myrecipepuppy.ui.favorites

import com.iliaberlana.myrecipepuppy.ui.commons.BaseView
import com.iliaberlana.myrecipepuppy.ui.model.RecipeUI

interface FavoriteRecipesView : BaseView {
    fun listFavorites(favorites: List<RecipeUI>)
}