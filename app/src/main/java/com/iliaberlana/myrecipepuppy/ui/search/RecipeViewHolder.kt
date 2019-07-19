package com.iliaberlana.myrecipepuppy.ui.search

import android.support.v7.widget.RecyclerView
import android.view.View
import com.iliaberlana.myrecipepuppy.ui.model.RecipeUI
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.recipe_item.view.*

class RecipeViewHolder(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer
{
    fun bind(recipe: RecipeUI) {
//        containerView.recipe_image.loadImage(recipe.image)
        containerView.recipe_name.text = recipe.name
    }
}