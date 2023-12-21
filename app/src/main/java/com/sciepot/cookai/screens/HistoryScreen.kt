package com.sciepot.cookai.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sciepot.cookai.MainViewModel
import com.sciepot.cookai.data.TableSelected

@Composable
fun HistoryScreen(
    mainViewModel: MainViewModel,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Button(onClick = {mainViewModel.clearTable(TableSelected.INGREDIENT)}) {
            Text(text = "Delete ingredients")
        }
        Button(onClick = {mainViewModel.clearTable(TableSelected.RECIPE)}) {
            Text(text = "Delete recipes")
        }
    }
}