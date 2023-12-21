package com.sciepot.cookai.screens.components

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun SuggestFAB (
    navBackStackEntry: NavBackStackEntry?,
    iconId: (String?) -> Int,
    isVisible: (String?) -> Boolean,
    onClick: (String?) -> Unit) {
    val currentRoute = navBackStackEntry?.destination?.route

    AnimatedVisibility(
        visible = isVisible(currentRoute),
        enter = slideInVertically() + scaleIn(),
        exit = slideOutVertically() + scaleOut()
    ) {
        FloatingActionButton(
            modifier = Modifier.size(70.dp),
            onClick = { onClick(currentRoute) },
            shape = CircleShape,
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.background
        ) {
            Icon(
                modifier = Modifier.size(46.dp),
                painter = painterResource(iconId(currentRoute)),
                contentDescription = "Suggest Floating Action Button"
            )
        }
    }
}