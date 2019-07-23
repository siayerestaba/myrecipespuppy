package com.iliaberlana.myrecipepuppy.framework

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import assertk.assertions.isEqualToWithGivenProperties
import com.iliaberlana.myrecipepuppy.framework.local.RecipeDao
import com.iliaberlana.myrecipepuppy.framework.local.RecipeDatabase
import com.iliaberlana.myrecipepuppy.framework.local.model.RecipeDbEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class RecipeDaoTest {

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