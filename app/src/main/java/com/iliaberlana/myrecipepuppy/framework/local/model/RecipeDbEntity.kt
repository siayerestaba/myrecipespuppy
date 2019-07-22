package com.iliaberlana.myrecipepuppy.framework.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecipeDbEntity(
    @PrimaryKey(autoGenerate = true) val id : Int?,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "ingredients") val ingredients: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "link") val link: String
) {

}