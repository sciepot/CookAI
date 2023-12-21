package com.sciepot.cookai.data.ingredients

import androidx.room.Dao
import androidx.room.Delete
import kotlinx.coroutines.flow.Flow
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface IngredientDao {
    @Upsert
    suspend fun upsertIngredient(ingredient: Ingredient)

    @Delete
    suspend fun deleteIngredient(ingredient: Ingredient)

    @Query("SELECT * FROM ingredient_table ORDER BY name ASC")
    fun getAllIngredients(): Flow<List<Ingredient>>

    @Query("SELECT * FROM ingredient_table WHERE quantity > 0")
    fun getLocalIngredients(): Flow<List<Ingredient>>

    @Query("DELETE FROM ingredient_table WHERE quantity = 0")
    suspend fun deleteEmptyIngredients()

    @Query("DELETE FROM ingredient_table")
    suspend fun clearTable()
}