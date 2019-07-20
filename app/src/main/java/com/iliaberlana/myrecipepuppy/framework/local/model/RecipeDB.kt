package com.iliaberlana.myrecipepuppy.framework.local.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class RecipeDB(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "ingredients") val ingredients: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "link") val link: String
) {
    @PrimaryKey(autoGenerate = true) val id : Int = 0
}