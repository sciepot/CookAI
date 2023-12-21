package com.sciepot.cookai

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sciepot.cookai.data.LocalDatabase
import com.sciepot.cookai.data.MainRepository
import com.sciepot.cookai.data.TableSelected
import com.sciepot.cookai.data.ingredients.Ingredient
import com.sciepot.cookai.data.recipe.Recipe
import com.sciepot.cookai.api.chatGptApi.ChatGptClient
import com.sciepot.cookai.api.chatGptApi.ChatGptService
import com.sciepot.cookai.api.chatGptApi.ResponseRecipe
import kotlinx.coroutines.Dispatchers
import com.sciepot.cookai.api.serpApi.SerpApiRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import okhttp3.internal.notify
import okhttp3.internal.notifyAll
import okhttp3.internal.wait
import okio.AsyncTimeout.Companion.lock
import java.lang.Thread.State

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val repository: MainRepository =
        MainRepository(
            LocalDatabase.getDatabase(application).recipeDao(),
            LocalDatabase.getDatabase(application).ingredientDao())

    private val _focusedRecipe = MutableStateFlow<Recipe?>(null)
    val focusedRecipe: StateFlow<Recipe?> = _focusedRecipe
    fun setFocusedRecipe(recipe: Recipe) {
        _focusedRecipe.value = recipe
    }

    private val serpApiRepository: SerpApiRepository = SerpApiRepository()

    private val chatGptService : ChatGptService = ChatGptService()

    private val _recLoading = MutableStateFlow(false)
    val recLoading: StateFlow<Boolean> = _recLoading

    private val maxGptRequests = 9
    private val count = MutableStateFlow(0)

    // Recipes
    val recommendedRecipes : StateFlow<List<Recipe>> = repository.recommendedRecipes.flowOn(Dispatchers.IO)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val favoriteRecipes: StateFlow<List<Recipe>> = repository.favoriteRecipes.flowOn(Dispatchers.IO)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val newRecipes: StateFlow<List<Recipe>> = repository.newRecipes.flowOn(Dispatchers.IO)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val myRecipes: StateFlow<List<Recipe>> = repository.myRecipes.flowOn(Dispatchers.IO)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun upsertRecipe(recipe: Recipe) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.upsertRecipe(recipe)
        }
    }

    fun deleteRecipe(recipe: Recipe) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteRecipe(recipe)
        }
    }

    fun findByName(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.findRecipeByName(name)
        }
    }

    // Ingredients
    val allIngredients: StateFlow<List<Ingredient>> = repository.allIngredients.flowOn(Dispatchers.IO)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val localIngredients: StateFlow<List<Ingredient>> = repository.localIngredients.flowOn(Dispatchers.IO)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())


    fun upsertIngredient(ingredient: Ingredient) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.upsertIngredient(ingredient)
        }
    }

    fun deleteIngredient(ingredient: Ingredient) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteIngredient(ingredient)
        }
    }

    fun deleteEmptyIngredient() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteEmptyIngredients()
        }
    }

    // Clear tables
    fun clearTable(selected: TableSelected) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearTable(selected)
        }
    }

    // Suggest recipes
    fun suggestRecipe() {
        viewModelScope.launch(Dispatchers.IO) {
            _recLoading.value = true
            val recipes = chatGptService.suggest(
                maxRequests = maxGptRequests,
                ingredients = localIngredients.value,
                count = count,
                coroutineScope = viewModelScope)
            recipes.forEach { recipe ->
                getPhotoUrl(recipe.name) { urls ->
                    upsertRecipe(Recipe(
                        name = recipe.name,
                        ingredients = recipe.ingredients,
                        instructions = recipe.instructions,
                        rating = -1.0,
                        isRecommended = true,
                        imageUrl1 = urls[0],
                        imageUrl2 = urls[1],
                        imageUrl3 = urls[2]
                    ))
                }
            }
            _recLoading.value = false
        }
    }

    // Get photos from serpApi
    fun getPhotoUrl(name: String, onComplete: (List<String>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            serpApiRepository.get(name, onComplete)
        }
    }
}