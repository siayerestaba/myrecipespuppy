package com.iliaberlana.myrecipepuppy.framework.remote.model

import com.iliaberlana.myrecipepuppy.domain.entities.Recipe

fun RecipeRemote.toRecipe(): Recipe
        = Recipe(this.title, this.ingredients, this.thumbnail, this.href)