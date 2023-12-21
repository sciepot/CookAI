package com.sciepot.cookai.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.sciepot.cookai.MainViewModel
import com.sciepot.cookai.R
import com.sciepot.cookai.navigation.Screen

@Composable
fun RecipesLoadingScreen (
    navController: NavHostController,
    mainViewModel: MainViewModel
) {
    val isLoading by mainViewModel.recLoading.collectAsStateWithLifecycle()
    if (isLoading) {
        val infiniteTransition = rememberInfiniteTransition(label = "Infinite transition")

        val index by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 100f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 700,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Reverse
            ),
            label = "Frames"
        )
        val phase = listOf(30f, 50f, 100f)
        val degreeLimit = 16f
        val initialPadding = 16.dp

        val paddingChange: (Float) -> Dp = { x ->
            if (x < phase[0]) initialPadding * (1f - (x / phase[0]))
            else 0.dp
        }

        val rotation: (Float) -> Float = { x ->
            if (x < phase[0]) 0f
            else if (x < phase[1]) degreeLimit * (x - phase[0]) / (phase[1] - phase[0])
            else if (x <= phase[2]) degreeLimit * (2 * x - phase[1] - phase[2]) / (phase[1] - phase[2])
            else -degreeLimit
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(width = 100.dp, height = 216.dp)
                    .padding(vertical = paddingChange(index))
            ) {
                Icon(
                    modifier = Modifier
                        .size(100.dp)
                        .rotate(rotation(index)),
                    painter = painterResource(id = R.drawable.pot_cap),
                    contentDescription = "Frame $index",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Icon(
                modifier = Modifier.size(100.dp),
                painter = painterResource(id = R.drawable.pot_body),
                contentDescription = "Frame $index",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    } else {
        navController.navigate(Screen.SuggestionScreen.route)
    }
}