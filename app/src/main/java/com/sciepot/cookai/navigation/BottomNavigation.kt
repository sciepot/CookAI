package com.sciepot.cookai.navigation

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController


@Composable
fun BottomNavigation(
    navController: NavHostController,
    navBackStackEntry: NavBackStackEntry?
) {
    val items = listOf(
        BottomNavItem.History,
        BottomNavItem.Search,
        BottomNavItem.Home,
        BottomNavItem.Charts,
        BottomNavItem.Settings
    )

    AnimatedVisibility(
        visible = when (navBackStackEntry?.destination?.route) {
            Screen.RecipesLoading.route -> false
            else -> true
        },
        enter = slideInVertically {it},
        exit = slideOutVertically {it}
    ) {
        NavigationBar (
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            tonalElevation = 0.dp
        ) {
            items.forEach { item ->
                AddItem(
                    screen = item,
                    currentDestination = navBackStackEntry?.destination,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomNavItem,
    currentDestination: NavDestination?,
    navController: NavHostController
) {

    NavigationBarItem(
        // Text that shows bellow the icon
        label = {
            Text(text = screen.title)
        },

        // The icon resource
        icon = {
            Icon(
                painterResource(id = screen.icon),
                contentDescription = screen.title
            )
        },

        // Display if the icon it is select or not
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,

        // Always show the label bellow the icon or not
        alwaysShowLabel = true,

        // Click listener for the icon
        onClick = {
            Log.d(currentDestination?.route, "current destination")
            Log.d(screen.route, "current destination")
            navController.navigate(screen.route)
        },

        // Control all the colors of the icon
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.onSurface,
            selectedTextColor = MaterialTheme.colorScheme.onSurface,
            indicatorColor = MaterialTheme.colorScheme.surface,
            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    )
}