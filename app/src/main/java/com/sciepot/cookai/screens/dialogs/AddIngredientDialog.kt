package com.sciepot.cookai.screens.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun AddIngredientDialog (
    onAddManually: () -> Unit,
    onDismissRequest: () -> Unit) {
    Dialog (onDismissRequest = {onDismissRequest()}) {
        Card (
            modifier = Modifier.fillMaxWidth()
                .height(220.dp)
                .padding(20.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column (
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Column (
                    modifier = Modifier.fillMaxWidth()
                        .height(130.dp)
                        .padding(top = 20.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally,) {

                    Button(
                        modifier = Modifier.size(width = 180.dp, height = 40.dp),
                        onClick = {
                            onAddManually()
                        }) {
                        Text(text = "Add Manually")
                    }
                    Button(
                        modifier = Modifier.size(180.dp, height = 40.dp),
                        contentPadding = PaddingValues(0.dp),
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.background)) {
                        Box (modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.linearGradient(
                                    listOf(Color(0xFFAD00FF), Color(0xFFFF1C1C)),
                                    start = Offset(0.0F, 0.0F),
                                    end = Offset(100.0F, 300.0F)
                                )
                            ),
                            contentAlignment = Alignment.Center)
                        {
                            Text(text = "AI Scan")
                        }
                    }
                }
                TextButton (
                    onClick = { onDismissRequest() },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colorScheme.onBackground,
                        containerColor = Color.Transparent))
                {
                    Text("Dismiss")
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun AddIngredientDialogPreview() {
//    AddIngredientDialog({}, {})
//}