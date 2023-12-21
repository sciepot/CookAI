package com.sciepot.cookai.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sciepot.cookai.data.ingredients.Ingredient

@Composable
fun IngredientRectangle (
    fraction: Float,
    ingredient: Ingredient,
    onClick: () -> Unit
    ) {

    Box(modifier = Modifier
        .fillMaxWidth(fraction)
        .aspectRatio(1f)
        .padding(10.dp)
        .shadow(
            elevation = 4.dp,
            shape = RoundedCornerShape(10.dp)
        )
        .clip(RoundedCornerShape(10.dp))
        .clickable { onClick() },
        contentAlignment = Alignment.BottomCenter) {

        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = when (ingredient.imageChosen) {
                1 -> ingredient.imageUrl2
                2 -> ingredient.imageUrl3
                else -> ingredient.imageUrl1 },
            contentDescription = ingredient.name,
            contentScale = ContentScale.Crop)

        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.Green,
        ) {
            Text(
                text = ingredient.name,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                maxLines = 1)
        }

    }
}