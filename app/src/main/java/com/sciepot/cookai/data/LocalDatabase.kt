package com.sciepot.cookai.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sciepot.cookai.data.ingredients.Ingredient
import com.sciepot.cookai.data.ingredients.IngredientDao
import com.sciepot.cookai.data.recipe.Recipe
import com.sciepot.cookai.data.recipe.RecipeDao

@Database(entities = [Recipe::class, Ingredient::class], version = 1, exportSchema = false)
abstract class LocalDatabase: RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    abstract fun ingredientDao(): IngredientDao

    companion object {
        @Volatile
        private var INSTANCE: LocalDatabase? = null

        fun getDatabase(context: Context): LocalDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            } else {
                synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        LocalDatabase::class.java,
                        "local_database"
                    ).build()
                    INSTANCE = instance
                    return instance
                }
            }
        }
    }
}
