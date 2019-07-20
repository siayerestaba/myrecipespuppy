package com.iliaberlana.myrecipepuppy.framework.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.iliaberlana.myrecipepuppy.framework.local.model.RecipeDB

@Database(entities = arrayOf(RecipeDB::class), version = 1)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}