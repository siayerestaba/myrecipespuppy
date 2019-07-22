package com.iliaberlana.myrecipepuppy.framework.local

import assertk.assertThat
import assertk.assertions.isEqualToWithGivenProperties
import com.iliaberlana.myrecipepuppy.domain.entities.Recipe
import com.iliaberlana.myrecipepuppy.framework.local.model.RecipeDbEntity
import org.junit.Test

class MapperRecipeDBToRecipeTest {

    @Test
    fun `should return Recipe from RecipeDbEntity with same values`() {
        val expected = Recipe("Recipe title", "onion, garlic", "https://via.placeholder.com/150", "http://www.recipepuppy.com/150")

        val recipeDB = RecipeDbEntity(1, "Recipe title", "onion, garlic","https://via.placeholder.com/150", "http://www.recipepuppy.com/150")

        val actual = recipeDB.toDomain()

        assertThat(expected).isEqualToWithGivenProperties(actual, Recipe::name, Recipe::ingredients, Recipe::imageUrl, Recipe::link)
    }

    @Test
    fun `should return RecipeDbEntity from Recipe with same values`() {
        val expected = RecipeDbEntity(null, "Recipe title", "onion, garlic", "https://via.placeholder.com/150", "http://www.recipepuppy.com/150")

        val recipe = Recipe("Recipe title", "onion, garlic", "https://via.placeholder.com/150","http://www.recipepuppy.com/150")

        val actual = RecipeDbEntity.fromDomain(recipe)

        assertThat(expected).isEqualToWithGivenProperties(actual, RecipeDbEntity::name, RecipeDbEntity::ingredients, RecipeDbEntity::imageUrl, RecipeDbEntity::link)
    }
}