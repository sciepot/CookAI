package com.sciepot.cookai.data

import com.sciepot.cookai.data.ingredients.*
import com.sciepot.cookai.data.recipe.*
import kotlinx.coroutines.flow.Flow

class MainRepository(private val recipeDao: RecipeDao, private val ingredientDao: IngredientDao) {

    // Recipe database
    val recommendedRecipes: Flow<List<Recipe>> = recipeDao.getRecommendedRecipes()
    val newRecipes: Flow<List<Recipe>> = recipeDao.getNewRecipes()
    val favoriteRecipes: Flow<List<Recipe>> = recipeDao.getFavoriteRecipes()
    val myRecipes: Flow<List<Recipe>> = recipeDao.getMyRecipes()

    suspend fun upsertRecipe(recipe: Recipe) {
        recipeDao.upsertRecipe(recipe)
    }
    suspend fun deleteRecipe(recipe: Recipe) {
        recipeDao.deleteRecipe(recipe)
    }
    fun findRecipeByName(name: String): Flow<List<Recipe>> {
        return recipeDao.findRecipeByName(name)
    }

    // Ingredient database
    val localIngredients: Flow<List<Ingredient>> = ingredientDao.getLocalIngredients()
    val allIngredients: Flow<List<Ingredient>> = ingredientDao.getAllIngredients()

    suspend fun upsertIngredient(ingredient: Ingredient) {
        ingredientDao.upsertIngredient(ingredient)
    }

    suspend fun deleteIngredient(ingredient: Ingredient) {
        ingredientDao.deleteIngredient(ingredient)
    }

    suspend fun deleteEmptyIngredients() {
        ingredientDao.deleteEmptyIngredients()
    }

    // Clear tables
    suspend fun clearTable(selected: TableSelected) {
        when (selected) {
            TableSelected.ALL -> {
                recipeDao.clearTable()
                ingredientDao.clearTable() }
            TableSelected.INGREDIENT -> ingredientDao.clearTable()
            TableSelected.RECIPE -> recipeDao.clearTable()
        }
    }
}