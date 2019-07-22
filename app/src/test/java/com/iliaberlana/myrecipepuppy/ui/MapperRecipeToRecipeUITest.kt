package com.iliaberlana.myrecipepuppy.ui

import assertk.assertThat
import assertk.assertions.isEqualToWithGivenProperties
import com.iliaberlana.myrecipepuppy.domain.entities.Recipe
import com.iliaberlana.myrecipepuppy.ui.model.RecipeUI
import org.junit.Test

class MapperRecipeToRecipeUITest {

    @Test
    fun `should return Recipe from RecipeUI with same values`() {
        val expected = Recipe("Recipe title", "onion, garlic", "https://via.placeholder.com/150","http://www.recipepuppy.com/150")

        val recipeUI = RecipeUI("Recipe title", "onion, garlic", "https://via.placeholder.com/150", "http://www.recipepuppy.com/150", isFavorite = false, hasLactose = false)

        val actual = recipeUI.toDomain()

        assertThat(expected).isEqualToWithGivenProperties(actual, Recipe::name, Recipe::ingredients, Recipe::imageUrl, Recipe::link)
    }

    @Test
    fun `should return RecipeUI from Recipe with same values`() {
        val expected = RecipeUI("Recipe title", "onion, garlic", "https://via.placeholder.com/150", "http://www.recipepuppy.com/150", isFavorite = false, hasLactose = false)

        val recipe = Recipe("Recipe title", "onion, garlic", "https://via.placeholder.com/150","http://www.recipepuppy.com/150")

        val actual = RecipeUI.fromDomain(recipe)

        assertThat(expected).isEqualToWithGivenProperties(actual, RecipeUI::name, RecipeUI::ingredients, RecipeUI::imageUrl, RecipeUI::link, RecipeUI::isFavorite, RecipeUI::hasLactose)
    }

    @Test
    fun `should mark hasLactose if one ingredient is milk`() {
        val expected = RecipeUI("Recipe title", "onion, milk", "https://via.placeholder.com/150", "http://www.recipepuppy.com/150", isFavorite = false, hasLactose = true)

        val recipe = Recipe("Recipe title", "onion, milk", "https://via.placeholder.com/150","http://www.recipepuppy.com/150")

        val actual = RecipeUI.fromDomain(recipe)

        assertThat(expected).isEqualToWithGivenProperties(actual, RecipeUI::name, RecipeUI::ingredients, RecipeUI::imageUrl, RecipeUI::link, RecipeUI::isFavorite, RecipeUI::hasLactose)
    }

    @Test
    fun `should mark hasLactose if one ingredient is cheese`() {
        val expected = RecipeUI("Recipe title", "onion, cheese", "https://via.placeholder.com/150", "http://www.recipepuppy.com/150", isFavorite = false, hasLactose = true)

        val recipe = Recipe("Recipe title", "onion, cheese", "https://via.placeholder.com/150","http://www.recipepuppy.com/150")

        val actual = RecipeUI.fromDomain(recipe)

        assertThat(expected).isEqualToWithGivenProperties(actual, RecipeUI::name, RecipeUI::ingredients, RecipeUI::imageUrl, RecipeUI::link, RecipeUI::isFavorite, RecipeUI::hasLactose)
    }
}