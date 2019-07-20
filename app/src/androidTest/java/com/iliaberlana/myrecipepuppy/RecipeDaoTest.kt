package com.iliaberlana.myrecipepuppy

import android.arch.persistence.room.Room
import com.iliaberlana.myrecipepuppy.framework.local.RecipeDatabase
import com.iliaberlana.myrecipepuppy.framework.local.model.RecipeDB
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class RecipeDaoTest: KoinTest {

    private val recipeDatabase: RecipeDatabase by inject()

    @Before
    fun before() {
        loadKoinModules(roomTestModule)
    }

    @After
    fun after() {
        recipeDatabase.close()
        stopKoin()
    }

    private val roomTestModule = module {
        factory(override = true) {
            // In-Memory database config
            Room.inMemoryDatabaseBuilder(get(), RecipeDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        }

        /*factory { get<RecipeDatabase>().recipeDao() }

        single { FavoriteRecipeRepositoryImpl(get()) }*/
    }

    @Test
    fun save_in_database_when_execute_save() = runBlocking {
        val recipe = RecipeDB("name", "eggs, bacon", "https://via.placeholder.com/150","http://www.recipepuppy.com/150")

        recipeDatabase.recipeDao().insert(recipe)

        val list = recipeDatabase.recipeDao().getAll()
        assertThat(list[0], equalTo(recipe))
    }
}