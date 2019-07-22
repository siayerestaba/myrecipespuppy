package com.iliaberlana.myrecipepuppy.ui.favorites

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iliaberlana.myrecipepuppy.R
import com.iliaberlana.myrecipepuppy.ui.commons.inflate
import com.iliaberlana.myrecipepuppy.ui.model.RecipeUI

class FavoriteRecipesAdapter(
    private val presenter: FavoritesRecipesPresenter
) : RecyclerView.Adapter<FavoriteRecipesViewHolder>() {
    private val favorites: MutableList<RecipeUI> = ArrayList()

    fun addAll(list: List<RecipeUI>) {
        favorites.addAll(list)
        notifyDataSetChanged()
    }

    fun removeFavorite(favorite: RecipeUI) {
        favorites.remove(favorite)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteRecipesViewHolder =
        FavoriteRecipesViewHolder(parent.inflate(R.layout.recipe_item), presenter)

    override fun onBindViewHolder(holder: FavoriteRecipesViewHolder, position: Int) = holder.bind(favorites[position])

    override fun getItemCount() = favorites.size
}