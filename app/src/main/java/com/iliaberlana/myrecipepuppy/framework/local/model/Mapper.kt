package com.iliaberlana.myrecipepuppy.framework.local.model

import com.iliaberlana.myrecipepuppy.domain.entities.Recipe

fun RecipeDB.toRecipe(): Recipe
        = Recipe(this.name, this.ingredients, this.imageUrl, this.link)
