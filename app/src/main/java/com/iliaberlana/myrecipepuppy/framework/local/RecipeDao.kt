package com.iliaberlana.myrecipepuppy.framework.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.iliaberlana.myrecipepuppy.framework.local.model.RecipeDbEntity

@Dao
interface RecipeDao {
    @Query("SELECT * FROM RecipeDbEntity")
    suspend fun getAll(): List<RecipeDbEntity>

    @Insert
    suspend fun insert(recipeDbEntity: RecipeDbEntity)

    @Delete
    suspend fun delete(user: RecipeDbEntity)
}