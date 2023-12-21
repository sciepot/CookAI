package com.sciepot.cookai.screens.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.sciepot.cookai.ui.theme.CookAITheme

@Composable
fun DeleteAlertDialog(
    onDelete: () -> Unit,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        title = { Text("Delete ingredient?") },
        text = {
            Column {
                Text(
                    text = "This operation will delete",)
                Text(
                    text = "the selected ingredient.",)
            }

        },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = onDelete) {
                Text(
                    text = "Confirm",
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(
                    text = "Dismiss",
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background,
        titleContentColor =  MaterialTheme.colorScheme.onBackground,
        textContentColor = MaterialTheme.colorScheme.onBackground,
    )
}

@Preview(showBackground = true)
@Composable
fun DeleteAlertDialogPreview() {
    CookAITheme {
        DeleteAlertDialog({}, {})
    }
}