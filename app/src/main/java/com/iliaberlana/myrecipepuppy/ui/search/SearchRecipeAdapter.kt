package com.iliaberlana.myrecipepuppy.ui.search

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iliaberlana.myrecipepuppy.R
import com.iliaberlana.myrecipepuppy.ui.commons.inflate
import com.iliaberlana.myrecipepuppy.ui.model.RecipeUI

class SearchRecipeAdapter(
    private val presenter: SearchRecipesPresenter
) : RecyclerView.Adapter<SearchRecipeViewHolder>() {
    private val recipes: MutableList<RecipeUI> = ArrayList()

    fun addAll(list: List<RecipeUI>) {
        recipes.addAll(list)
        notifyDataSetChanged()
    }

    fun clean() {
        recipes.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchRecipeViewHolder =
        SearchRecipeViewHolder(parent.inflate(R.layout.recipe_item), presenter)

    override fun onBindViewHolder(holder: SearchRecipeViewHolder, position: Int) = holder.bind(recipes[position])

    override fun getItemCount() = recipes.size
}