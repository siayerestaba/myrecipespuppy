package com.iliaberlana.myrecipepuppy

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import assertk.assertions.isEqualToWithGivenProperties
import com.iliaberlana.myrecipepuppy.domain.entities.Recipe
import com.iliaberlana.myrecipepuppy.framework.local.RecipeDao
import com.iliaberlana.myrecipepuppy.framework.local.RecipeDatabase
import com.iliaberlana.myrecipepuppy.framework.local.model.RecipeDbEntity
import com.iliaberlana.myrecipepuppy.ui.search.SearchRecipesActivity
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class RecipeDaoTest {

//    @get:Rule
//    val activityRule = ActivityTestRule(SearchRecipesActivity::class.java, false, false)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var recipeDatabase: RecipeDatabase
    private lateinit var recipeDao: RecipeDao

    @Before
    fun before() {
        recipeDatabase = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), RecipeDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        recipeDao = recipeDatabase.recipeDao()
    }

    @After
    fun after() {
        recipeDatabase.close()
    }

    @Test
    fun save_in_database_when_execute_save() = runBlocking {
        val firstList = recipeDatabase.recipeDao().getAll()
        assertTrue(firstList.isEmpty())

        val recipe = RecipeDbEntity(1,"name", "eggs, bacon", "https://via.placeholder.com/150","http://www.recipepuppy.com/150")

        recipeDatabase.recipeDao().insert(recipe)

        val list = recipeDatabase.recipeDao().getAll()
        assertk.assertThat(recipe)
            .isEqualToWithGivenProperties(list[0], RecipeDbEntity::name, RecipeDbEntity::ingredients, RecipeDbEntity::imageUrl, RecipeDbEntity::link)
    }
}