package com.sciepot.cookai.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.sciepot.cookai.MainViewModel
import com.sciepot.cookai.R
import com.sciepot.cookai.data.recipe.Recipe
import com.sciepot.cookai.navigation.Screen
import com.sciepot.cookai.ui.theme.CookAITheme


@Composable
fun Block (recipe: Recipe, onHeart: () -> Unit, onCook: () -> Unit) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.8f)
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(32.dp)
            ),
        shape = RoundedCornerShape(32.dp)) {
        Box (
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomStart
        ) {
            Column (
                modifier = Modifier.fillMaxSize()
            ) {
                AsyncImage (
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    model = when (recipe?.imageChosen) {
                        1 -> recipe?.imageUrl2
                        2 -> recipe?.imageUrl3
                        else -> recipe?.imageUrl1},
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
            Surface (
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2.0f),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.tertiary,
            ) {
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = recipe.name,
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Row (
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier
                                .fillMaxWidth(0.2f)
                                .aspectRatio(1f)
                                .clickable {
                                    onHeart()
                                },
                            painter = painterResource(
                                if (recipe.isFavorite) R.drawable.favorite_fill
                                else R.drawable.favorite_light),
                            contentDescription = "Favorite Light",
                            tint = if (recipe.isFavorite) MaterialTheme.colorScheme.error
                                    else MaterialTheme.colorScheme.onBackground
                        )
                        Button (
                            onClick = onCook,
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .aspectRatio(2f)
                                .padding(0.dp),
                            shape = RoundedCornerShape(16.dp)
                            ) {
                            Text(
                                text = "Cook",
                                style = MaterialTheme.typography.headlineSmall)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun SuggestionScreen(
    paddingValues: PaddingValues,
    navController: NavController,
    mainViewModel: MainViewModel
) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues)
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        val recipes by mainViewModel.recommendedRecipes.collectAsStateWithLifecycle()
        recipes.forEach {
            Block (
                recipe = it,
                onHeart = {
                    mainViewModel.upsertRecipe(it.copy(isFavorite = !it.isFavorite))
                },
                onCook = {
                    mainViewModel.setFocusedRecipe(it)
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
