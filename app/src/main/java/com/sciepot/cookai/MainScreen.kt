package com.sciepot.cookai

import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sciepot.cookai.navigation.BottomNavGraph
import com.sciepot.cookai.navigation.BottomNavigation
import com.sciepot.cookai.screens.components.SuggestFAB
import com.sciepot.cookai.navigation.Screen
import com.sciepot.cookai.navigation.TopBar

@Composable
fun MainScreen(mainViewModel: MainViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    Scaffold (
        topBar = {
            TopBar(navBackStackEntry)
        },
        bottomBar = {
            BottomNavigation(
                navController = navController,
                navBackStackEntry = navBackStackEntry)
        },
        floatingActionButton = {
            SuggestFAB (
                navBackStackEntry = navBackStackEntry,
                iconId = {
                    if (it == Screen.RecipeViewer.route) R.drawable.widget_icon
                    else R.drawable.suggest_icon
                },
                isVisible = {
                    when(it) {
                        Screen.Home.route, Screen.RecipeViewer.route -> true
                        else -> false
                    }
                },
                onClick = {
                    when(it) {
                        Screen.Home.route ->
                            if (mainViewModel.localIngredients.value.isNotEmpty()) {
                                mainViewModel.suggestRecipe()
                                navController.navigate(Screen.RecipesLoading.route)
                            }
                        Screen.RecipeViewer.route -> navController.popBackStack()
                        else -> {}
                        }
                    }
            )},
        floatingActionButtonPosition = FabPosition.Center
    ) {
        BottomNavGraph (
            navController = navController,
            paddingValues = it,
            mainViewModel = mainViewModel
        )
    }
}