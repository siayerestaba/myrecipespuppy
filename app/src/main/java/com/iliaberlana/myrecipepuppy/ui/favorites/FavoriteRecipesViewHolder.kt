package com.iliaberlana.myrecipepuppy.ui.favorites

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.iliaberlana.myrecipepuppy.ui.commons.cleanImage
import com.iliaberlana.myrecipepuppy.ui.commons.loadImage
import com.iliaberlana.myrecipepuppy.ui.model.RecipeUI
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.recipe_item.view.*

class FavoriteRecipesViewHolder(
    override val containerView: View,
    private val presenter: FavoritesRecipesPresenter
) : RecyclerView.ViewHolder(containerView), LayoutContainer {
    fun bind(favorite: RecipeUI) {
        containerView.recipe_name.text = favorite.name
        containerView.recipe_ingredients.text = favorite.ingredients

        if (favorite.imageUrl.isEmpty()) containerView.recipe_image.cleanImage()
        else containerView.recipe_image.loadImage(favorite.imageUrl)

        if (favorite.hasLactose) containerView.recipe_haslactose.visibility = View.VISIBLE
        else containerView.recipe_haslactose.visibility = View.GONE

        containerView.recipe_addfavorite.visibility = View.GONE
//        containerView.recipe_addfavorite.setOnClickListener { presenter.addFavorite(favorite) }

        containerView.setOnClickListener { presenter.onRecipeClicked(favorite) }
    }
}