package com.sciepot.cookai.navigation

sealed class Screen (val route: String) {
    data object Home: Screen("home")
    data object Charts: Screen("charts")
    data object Search: Screen("search")
    data object Settings: Screen("settings")
    data object History: Screen("history")
    data object RecipesLoading: Screen("recipes_loading")
    data object SuggestionScreen: Screen("suggestion_screen")
    data object RecipeViewer: Screen("recipe_viewer")
}