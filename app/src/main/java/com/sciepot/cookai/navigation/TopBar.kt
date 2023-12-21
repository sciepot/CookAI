package com.sciepot.cookai.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import com.sciepot.cookai.R

@Composable
fun TopBar(navBackStackEntry: NavBackStackEntry?) {
    val currentDestination = navBackStackEntry?.destination?.route
    val titleMap = mapOf(
        Screen.Charts.route to "Charts",
        Screen.History.route to "History",
        Screen.Settings.route to "Settings")

    AnimatedVisibility(
        visible = when (navBackStackEntry?.destination?.route) {
            Screen.RecipesLoading.route -> false
            else -> true
        },
        enter = slideInVertically {-it },
        exit = slideOutVertically {-it}
    ) {
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(16.dp),
            contentAlignment = Alignment.BottomCenter,
        ) {
            Box {
                when (currentDestination) {
                    Screen.Home.route, Screen.SuggestionScreen.route, Screen.RecipeViewer.route-> {
                        Image (
                            modifier = Modifier.size(width = 100.dp, height = 50.dp),
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "Logo"
                        )
                    }
                    Screen.Charts.route, Screen.History.route, Screen.Settings.route-> {
                        Text (
                            text = titleMap[currentDestination] ?: currentDestination.replaceFirstChar { it.uppercase() },
                            style = MaterialTheme.typography.displayMedium
                        )
                    }
                    Screen.Search.route -> {
                    }
                }
            }

            if (currentDestination == Screen.Home.route) {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Image(
                        modifier = Modifier.size(50.dp),
                        painter = painterResource(id = R.drawable.user_icon),
                        contentDescription = "User icon")
                }
            }
        }
    }

}