package com.iliaberlana.myrecipepuppy.ui.search

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.iliaberlana.myrecipepuppy.ui.commons.cleanImage
import com.iliaberlana.myrecipepuppy.ui.commons.loadImage
import com.iliaberlana.myrecipepuppy.ui.model.RecipeUI
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.recipe_item.view.*

class SearchRecipeViewHolder(
    override val containerView: View,
    private val presenter: SearchRecipesPresenter
) : RecyclerView.ViewHolder(containerView), LayoutContainer {
    fun bind(recipe: RecipeUI) {
        containerView.recipe_name.text = recipe.name
        containerView.recipe_ingredients.text = recipe.ingredients

        if (recipe.imageUrl.isEmpty()) containerView.recipe_image.cleanImage()
        else containerView.recipe_image.loadImage(recipe.imageUrl)

        if (recipe.hasLactose) containerView.recipe_haslactose.visibility = View.VISIBLE
        else containerView.recipe_haslactose.visibility = View.GONE

        if (recipe.isFavorite) containerView.recipe_addfavorite.visibility = View.GONE
        else containerView.recipe_addfavorite.visibility = View.VISIBLE

        containerView.recipe_addfavorite.setOnClickListener {
            presenter.addFavorite(recipe)
            containerView.recipe_addfavorite.visibility = View.GONE
        }

        containerView.setOnClickListener { presenter.onRecipeClicked(recipe) }
    }
}