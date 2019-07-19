package com.iliaberlana.myrecipepuppy.ui.search

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.iliaberlana.myrecipepuppy.R
import com.iliaberlana.myrecipepuppy.ui.commons.inflate
import com.iliaberlana.myrecipepuppy.ui.model.RecipeUI

class RecipeAdapter(
    val fetchNewPage: () -> Unit
) : RecyclerView.Adapter<RecipeViewHolder>() {
    private var distance: Int = 6
    private var waitingForNextPage: Boolean = false

    private var recipes: MutableList<RecipeUI> = ArrayList()

    fun addAll(collection: Collection<RecipeUI>) {
        setWaitingForNextPageFalse()
        recipes.addAll(collection)
        notifyDataSetChanged()
    }

    fun clean() {
        setWaitingForNextPageFalse()
        recipes.clear()
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        if (!waitingForNextPage) {
            if (position.plus(distance) >= itemCount) {
                setWaitingForNextPageTrue()
                fetchNewPage()
            }
        }

        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder =
        RecipeViewHolder(parent.inflate(R.layout.recipe_item))

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) = holder.bind(recipes.get(position))

    override fun getItemCount(): Int = recipes.size

    private fun setWaitingForNextPageFalse() {
        waitingForNextPage = false
    }

    private fun setWaitingForNextPageTrue() {
        waitingForNextPage = true
    }

}