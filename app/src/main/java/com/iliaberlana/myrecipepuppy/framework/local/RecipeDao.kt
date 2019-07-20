package com.iliaberlana.myrecipepuppy.framework.local

import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.iliaberlana.myrecipepuppy.framework.local.model.RecipeDB

interface RecipeDao {
    @Query("SELECT * FROM recipedb")
    suspend fun getAll(): List<RecipeDB>

    @Insert
    suspend fun insert(recipeDB: RecipeDB)

    @Delete
    suspend fun delete(user: RecipeDB)
}