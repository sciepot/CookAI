package com.sciepot.cookai.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sciepot.cookai.MainViewModel
import com.sciepot.cookai.R
import com.sciepot.cookai.data.ingredients.Ingredient
import com.sciepot.cookai.measurement.MUnit
import com.sciepot.cookai.navigation.Screen
import com.sciepot.cookai.screens.components.IngredientRectangle
import com.sciepot.cookai.screens.components.RecipeCircle
import com.sciepot.cookai.screens.dialogs.AddIngredientDialog
import com.sciepot.cookai.screens.dialogs.AddManuallyDialog
import com.sciepot.cookai.screens.dialogs.DeleteAlertDialog
import com.sciepot.cookai.screens.dialogs.SelectIngredientDialog
import kotlin.math.min

@Composable
fun RecommendationLine (
    mainViewModel: MainViewModel,
    paddingEdge: Dp,
    navController: NavController,
    circlesLimit: Int = 10,
) {
    var state = remember { mutableStateOf(true) }
    Column (
        modifier = Modifier.padding(paddingEdge)
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.clickable { state.value = true },
                text = "Recommendation",
                style = MaterialTheme.typography.titleLarge,
                color = if (state.value) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.scrim
            )
            Text(
                modifier = Modifier.clickable { state.value = false },
                text = "Favorite",
                style = MaterialTheme.typography.titleLarge,
                color =  if (state.value) MaterialTheme.colorScheme.scrim else MaterialTheme.colorScheme.onBackground
            )
            Button(
                modifier = Modifier.size(width = 30.dp, height = 30.dp),
                contentPadding = PaddingValues(0.dp),
                shape = RectangleShape,
                onClick = { /*TODO*/ },
                colors = buttonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                )
            ) {
                Icon(
                    painterResource(R.drawable.refresh),
                    contentDescription = "Refresh button",
                    modifier = Modifier.fillMaxSize(),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

        }
        val recRecipes by mainViewModel.recommendedRecipes.collectAsState()
        val favRecipes by mainViewModel.favoriteRecipes.collectAsState()
        if (state.value) {
            LazyRow {
                items(min(recRecipes.size, circlesLimit)) { index ->
                    RecipeCircle(recRecipes[index]) {
                        mainViewModel.setFocusedRecipe(recRecipes[index])
                        navController.navigate(Screen.RecipeViewer.route)
                    }
                }
            }
        } else {
            LazyRow {
                items(min(favRecipes.size, circlesLimit)) { index ->
                    RecipeCircle(favRecipes[index]) {
                        mainViewModel.setFocusedRecipe(favRecipes[index])
                        navController.navigate(Screen.RecipeViewer.route)
                    }
                }
            }
        }
    }
}

@Composable
fun AddButton(
    fraction: Float,
    onAdd: (String, Double, MUnit) -> Unit
) {
    val openDialog = remember { mutableStateOf(false) }
    val openAddManually = remember { mutableStateOf(false) }
    Button(
        modifier = Modifier
            .fillMaxWidth(fraction)
            .aspectRatio(1f),
        contentPadding = PaddingValues(10.dp),
        shape = RectangleShape,
        onClick = { openDialog.value = !openDialog.value },
        colors = buttonColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onSurface,
        )
    ) {
        Icon(
            painterResource(R.drawable.add_icon),
            contentDescription = "Add button",
            modifier = Modifier.fillMaxSize(),
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
    when {
        openDialog.value -> {
            AddIngredientDialog(
                onAddManually = {
                    openDialog.value = false
                    openAddManually.value = !openAddManually.value },
                onDismissRequest = { openDialog.value = false },
            )
        }
    }
    when {
        openAddManually.value -> {
            AddManuallyDialog (
                onAdd = onAdd,
                onDismissRequest = { openAddManually.value = false }
            )
        }
    }
}

@Composable
fun GroceryList(mainViewModel: MainViewModel, paddingEdge: Dp) {
    val fractions = listOf(0.25f, 1.0f / 3.0f, 0.5f, 1.0f)
    val emptyIngredient = Ingredient(0, "", 0.0, MUnit.G, 0.0, MUnit.G)
    val selected = remember { mutableStateOf(false) }
    val toDelete = remember { mutableStateOf(false) }
    val toEdit = remember { mutableStateOf(false) }
    val selectedIngredient = remember { mutableStateOf(emptyIngredient) }
    Column (
      modifier = Modifier
          .fillMaxWidth()
          .padding(paddingEdge)
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(text = "Grocery List", style = MaterialTheme.typography.titleLarge)
            Button(
                modifier = Modifier.size(width = 30.dp, height = 30.dp),
                contentPadding = PaddingValues(0.dp),
                shape = RectangleShape,
                onClick = { /*TODO*/ },
                colors = buttonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                )
            ) {
                Icon(
                    painterResource(R.drawable.list_view_icon),
                    contentDescription = "List view",
                    modifier = Modifier.fillMaxSize(),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        Column (
          modifier = Modifier.fillMaxWidth()
        ) {

            val ingredients by mainViewModel.localIngredients.collectAsState()
            val rows: Int = ((ingredients.size + 1) / 4) + 1
            LazyColumn {
                items(rows) {rowIndex ->
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        for (i in rowIndex * 4 until rowIndex * 4 + 4) {
                            if (i < ingredients.size) {
                                IngredientRectangle (
                                    fraction = fractions[i % 4],
                                    ingredient = ingredients[i]) {
                                    selected.value = true
                                    selectedIngredient.value = ingredients[i]
                                }
                            } else if (i == ingredients.size){
                                AddButton (fractions[i % 4]) { name: String, quantity: Double, unit: MUnit ->
                                    mainViewModel.getPhotoUrl(name) {
                                        mainViewModel.upsertIngredient(
                                            Ingredient(
                                                name = name,
                                                quantity = quantity,
                                                originalUnit = unit,
                                                presentedQuantity = quantity,
                                                presentedUnit = unit,
                                                imageUrl1 = it[0],
                                                imageUrl2 = it[1],
                                                imageUrl3 = it[2]
                                            )
                                        )
                                    }
                                }
                                break
                            } else break
                        }
                    }
                }
            }
        }
    }
    when {
        selected.value -> {
            SelectIngredientDialog(
                ingredient = selectedIngredient,
                onEdit = {
                    mainViewModel.upsertIngredient(selectedIngredient.value.copy(imageChosen = (selectedIngredient.value.imageChosen + 1) % 3))
                },
                onDelete = {
                    toDelete.value = true
                },
                onDismissRequest = {
                    selected.value = false
                })
        }
    }
    when {
        toDelete.value -> {
            DeleteAlertDialog(
                onDelete = {
                    selected.value = false
                    toDelete.value = false
                    mainViewModel.deleteIngredient(selectedIngredient.value.copy())
                },
                onDismissRequest = {
                    toDelete.value = false
                }
            )
        }
    }
    when {
        toEdit.value -> {

        }
    }
}

@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    mainViewModel: MainViewModel,
    navController: NavController
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
    )
    {
        val paddingEdge = 16.dp
        RecommendationLine(mainViewModel, paddingEdge, navController)
        GroceryList(mainViewModel, paddingEdge)
    }
}


