package com.iliaberlana.myrecipepuppy.framework.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.iliaberlana.myrecipepuppy.framework.local.model.RecipeDbEntity

@Database(entities = [RecipeDbEntity::class], version = 1, exportSchema = false)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}

object RecipeDb {
    private lateinit var db: RecipeDatabase

    fun initializeDb(context: Context) {
        db = Room.databaseBuilder(
            context,
            RecipeDatabase::class.java,
            "recipe-db"
        ).build()
    }

    fun getDb(): RecipeDatabase {
        if (!::db.isInitialized) {
            throw Error("Database not initialized")
        }
        return db
    }

}