package com.iliaberlana.myrecipepuppy.framework.remote

import assertk.assertThat
import assertk.assertions.isEqualToWithGivenProperties
import com.iliaberlana.myrecipepuppy.domain.entities.Recipe
import com.iliaberlana.myrecipepuppy.framework.remote.model.RecipeRemote
import com.iliaberlana.myrecipepuppy.framework.remote.model.toRecipe
import org.junit.Test

class MapperRecipeRemoteToRecipe {

    @Test
    fun `should return Recipe from RecipeRemote with same values`() {
        val expected = Recipe("Recipe title", "onion, garlic", "https://via.placeholder.com/150", "http://www.recipepuppy.com/150")

        val recipeRemote = RecipeRemote("Recipe title", "http://www.recipepuppy.com/150", "onion, garlic", "https://via.placeholder.com/150")

        val actual = recipeRemote.toRecipe()

        assertThat(expected).isEqualToWithGivenProperties(actual, Recipe::name, Recipe::ingredients, Recipe::imageUrl, Recipe::link)
    }
}