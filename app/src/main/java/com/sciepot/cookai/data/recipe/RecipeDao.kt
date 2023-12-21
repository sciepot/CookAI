package com.sciepot.cookai.data.recipe

import androidx.room.Dao
import androidx.room.Delete
import kotlinx.coroutines.flow.Flow
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface RecipeDao {
    @Upsert
    suspend fun upsertRecipe(recipe: Recipe)

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)

    @Query("SELECT * FROM recipe_table WHERE name LIKE :name")
    fun findRecipeByName(name: String): Flow<List<Recipe>>

    @Query("SELECT * FROM recipe_table WHERE is_favorite = 1")
    fun getFavoriteRecipes(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipe_table WHERE is_recommended = 1 ORDER BY is_new DESC")
    fun getRecommendedRecipes(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipe_table WHERE is_mine = 1")
    fun getMyRecipes(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipe_table WHERE is_new = 1")
    fun getNewRecipes(): Flow<List<Recipe>>

    @Query("DELETE FROM recipe_table")
    suspend fun clearTable()
}