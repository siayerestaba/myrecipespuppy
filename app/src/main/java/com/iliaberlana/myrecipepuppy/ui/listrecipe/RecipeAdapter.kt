package com.iliaberlana.myrecipepuppy.ui.listrecipe

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.iliaberlana.myrecipepuppy.R
import com.iliaberlana.myrecipepuppy.ui.commons.inflate
import com.iliaberlana.myrecipepuppy.ui.model.RecipeUI

class RecipeAdapter : RecyclerView.Adapter<RecipeViewHolder>() {
    private val recipes: MutableList<RecipeUI> = ArrayList()

    fun addAll(list: List<RecipeUI>) {
        recipes.addAll(list)
        notifyDataSetChanged()
    }

    fun clean() {
        recipes.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder =
        RecipeViewHolder(parent.inflate(R.layout.recipe_item))

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) = holder.bind(recipes[position])

    override fun getItemCount() = recipes.size
}