package com.iliaberlana.myrecipepuppy.framework.local

import assertk.assertThat
import assertk.assertions.isEqualToWithGivenProperties
import com.iliaberlana.myrecipepuppy.domain.entities.Recipe
import com.iliaberlana.myrecipepuppy.framework.local.model.RecipeDB
import com.iliaberlana.myrecipepuppy.framework.local.model.toRecipe
import com.iliaberlana.myrecipepuppy.framework.remote.model.RecipeRemote
import com.iliaberlana.myrecipepuppy.framework.remote.model.toRecipe
import org.junit.Test

class MapperRecipeDBToRecipe {

    @Test
    fun `should return Recipe from RecipeDB with same values`() {
        val expected = Recipe("Recipe title", "onion, garlic", "https://via.placeholder.com/150", "http://www.recipepuppy.com/150")

        val recipeDB = RecipeDB("Recipe title", "onion, garlic","https://via.placeholder.com/150", "http://www.recipepuppy.com/150")

        val actual = recipeDB.toRecipe()

        assertThat(expected).isEqualToWithGivenProperties(actual, Recipe::name, Recipe::ingredients, Recipe::imageUrl, Recipe::link)
    }
}