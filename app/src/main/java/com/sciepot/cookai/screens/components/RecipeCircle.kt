package com.sciepot.cookai.screens.components

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sciepot.cookai.data.recipe.Recipe

@SuppressLint("ResourceType")
@Composable
fun RecipeCircle (
    recipe: Recipe,
    onClick: () -> Unit
) {
    val gradientNew = listOf(
        Color(0xFFAD00FF),
        Color(0xFFFF1C1C)
    )
    Column (
        modifier = Modifier.width(100.dp)
            .padding(PaddingValues(end = 10.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (recipe.isNew) {
            AsyncImage (
                modifier = Modifier
                    .size(80.dp)
                    .border(
                        width = 3.dp,
                        brush = Brush.linearGradient(gradientNew),
                        shape = CircleShape
                    )
                    .border(
                        width = 5.dp,
                        color = MaterialTheme.colorScheme.background,
                        shape = CircleShape
                    )
                    .clip(CircleShape)
                    .padding(5.dp)
                    .clickable {onClick()},
                model = when (recipe.imageChosen) {
                    1 -> recipe.imageUrl2
                    2 -> recipe.imageUrl3
                    else -> recipe.imageUrl1
                },
                contentDescription = recipe.name,
                contentScale = ContentScale.Crop
            )
        } else {
            AsyncImage (
                modifier = Modifier
                    .size(80.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        shape = CircleShape
                    )
                    .clip(CircleShape)
                    .padding(1.dp)
                    .clickable {onClick()},
                model = when (recipe.imageChosen) {
                    1 -> recipe.imageUrl2
                    2 -> recipe.imageUrl3
                    else -> recipe.imageUrl1
                },
                contentDescription = recipe.name,
                contentScale = ContentScale.Crop
            )
        }
        Text(
            text = recipe.name,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Clip
        )
    }
}