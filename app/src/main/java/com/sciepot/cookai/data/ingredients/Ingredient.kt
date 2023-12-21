package com.sciepot.cookai.data.ingredients

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import com.sciepot.cookai.measurement.MUnit
import retrofit2.http.Url
import java.util.Date

@Entity(tableName = "ingredient_table")
data class Ingredient(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val quantity: Double,
    @ColumnInfo(name = "original_unit") val originalUnit: MUnit,
    @ColumnInfo(name = "presented_quantity") val presentedQuantity: Double,
    @ColumnInfo(name = "presented_unit") val presentedUnit: MUnit,
    @ColumnInfo(name = "input_date") val inputDate: Long = 0,
    @ColumnInfo(name = "expiry_date") val expiryDate: Long = 0,
    @ColumnInfo(name = "image_chosen") val imageChosen: Int = 0,
    @ColumnInfo(name = "image_url_1") val imageUrl1: String = "",
    @ColumnInfo(name = "image_url_2") val imageUrl2: String = "",
    @ColumnInfo(name = "image_url_3") val imageUrl3: String = "",
)
