package com.iliaberlana.myrecipepuppy.ui.listrecipe

import android.support.v7.widget.RecyclerView
import android.view.View
import com.iliaberlana.myrecipepuppy.ui.commons.cleanImage
import com.iliaberlana.myrecipepuppy.ui.commons.loadImage
import com.iliaberlana.myrecipepuppy.ui.model.RecipeUI
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.recipe_item.view.*

class RecipeViewHolder(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer
{
    fun bind(recipe: RecipeUI) {
        containerView.recipe_name.text = recipe.name
        containerView.recipe_ingredients.text = recipe.ingredients

        if (recipe.imageUrl.isEmpty()) containerView.recipe_image.cleanImage()
        else containerView.recipe_image.loadImage(recipe.imageUrl)

        if(recipe.hasLactose) containerView.recipe_haslactose.visibility = View.VISIBLE
        else containerView.recipe_haslactose.visibility = View.GONE
    }
}