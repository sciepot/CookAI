package com.sciepot.cookai.navigation

import com.sciepot.cookai.R

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: Int
) {
    data object Home: BottomNavItem(
        route = Screen.Home.route,
        title = "Home",
        icon = R.drawable.home_icon
    )
    data object History: BottomNavItem(
        route = Screen.History.route,
        title = "History",
        icon = R.drawable.history_icon
    )

    data object Search: BottomNavItem(
        route = Screen.Search.route,
        title = "Search",
        icon = R.drawable.search_icon
    )

    data object Charts: BottomNavItem(
        route = Screen.Charts.route,
        title = "Charts",
        icon = R.drawable.charts_icon
    )

    data object Settings: BottomNavItem(
        route = Screen.Settings.route,
        title = "Settings",
        icon = R.drawable.settings_icon
    )
}
