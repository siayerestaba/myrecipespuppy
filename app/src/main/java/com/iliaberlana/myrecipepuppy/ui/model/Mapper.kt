package com.iliaberlana.myrecipepuppy.ui.model

import com.iliaberlana.myrecipepuppy.domain.entities.Recipe

fun Recipe.toRecipeUI(): RecipeUI {
    val hasLactose = this.ingredients.contains("milk") || this.ingredients.contains("cheese")

    return RecipeUI(this.name, this.ingredients, this.imageUrl, this.link, false, hasLactose) // TODO Mirar el mapper con lo del favorito
}