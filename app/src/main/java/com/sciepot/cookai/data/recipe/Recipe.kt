package com.sciepot.cookai.data.recipe

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "recipe_table")
data class Recipe(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val ingredients: String,
    val instructions: String,
    val rating: Double,
    @ColumnInfo(name = "num_of_ratings") val numOfRatings: Long = 0,
    @ColumnInfo(name ="is_favorite") val isFavorite: Boolean = false,
    @ColumnInfo(name = "is_recommended") val isRecommended: Boolean = false,
    @ColumnInfo(name = "is_new") val isNew: Boolean = true,
    @ColumnInfo(name = "is_mine") val isMine: Boolean = false,
    @ColumnInfo(name = "image_chose") val imageChosen: Int = 0,
    @ColumnInfo(name = "image_url_1") val imageUrl1: String = "",
    @ColumnInfo(name = "image_url_2") val imageUrl2: String = "",
    @ColumnInfo(name = "image_url_3") val imageUrl3: String = "",
// TODO: Implement reviews and change type for recipe_text
)
