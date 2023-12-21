package com.sciepot.cookai.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sciepot.cookai.MainViewModel
import com.sciepot.cookai.screens.*

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    mainViewModel: MainViewModel) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(
            route = Screen.Home.route
        ) {
            HomeScreen(paddingValues, mainViewModel, navController)
        }

        composable(
            route = Screen.History.route
        ) {
            HistoryScreen(mainViewModel)
        }

        composable(
            route = Screen.Search.route
        ) {
            SearchScreen(paddingValues)
        }

        composable(
                route = Screen.Settings.route
                ) {
            SettingsScreen(paddingValues)
        }

        composable(
            route = Screen.Charts.route
        ) {
            ChartsScreen(paddingValues)
        }

        composable(
            route = Screen.RecipesLoading.route,
        ) {
            RecipesLoadingScreen(
                navController = navController,
                mainViewModel = mainViewModel
            )
        }
        composable(
            route = Screen.RecipeViewer.route
        ) {
            RecipeViewer(
                paddingValues = paddingValues,
                mainViewModel = mainViewModel
            )
        }
        composable(
            route = Screen.SuggestionScreen.route
        ) {
            SuggestionScreen(
                paddingValues = paddingValues,
                navController = navController,
                mainViewModel = mainViewModel
            )
        }
    }
}