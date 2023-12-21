package com.sciepot.cookai.screens.dialogs

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.sciepot.cookai.R
import com.sciepot.cookai.measurement.MUnit

@Composable
fun IndicatorLine (isError: Boolean, stroke: Dp = 1.dp) {
    val errorColor = MaterialTheme.colorScheme.error

    Canvas (
        modifier = Modifier
            .fillMaxWidth()
            .height(stroke)
    ) {
        drawLine(
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            brush = Brush.horizontalGradient(if (isError) listOf(errorColor, errorColor) else listOf(
                Color(0xFFAD00FF), Color(0xFFFF1C1C))),
            strokeWidth = stroke.toPx(),
            cap = StrokeCap.Butt
        )
    }
}

@Composable
private fun CloseButton(onDismissRequest: () -> Unit) {
    Button(
        modifier = Modifier.size(24.dp),
        contentPadding = PaddingValues(0.dp),
        shape = CircleShape,
        onClick = {onDismissRequest()}) {
        Icon (
            painter = painterResource(id = R.drawable.close_icon),
            contentDescription = "Close button"
        )
    }
}

@Composable
private fun AddButton(
    name: String,
    quantity: String,
    selectedUnit: MUnit,
    onError: () -> Unit,
    onAdd: (String, Double, MUnit) -> Unit,
    onDismissRequest: () -> Unit
) {
    Button (
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        shape = RoundedCornerShape(10.dp),
        onClick = {
            if (quantity.isBlank() || name.isBlank()) {
                onError()
            } else {
                onDismissRequest()
                onAdd(name, quantity.toDouble(), selectedUnit)
            }
        }) {
        Text(text = "Add")
    }
}

@Composable
private fun NameTextField (name: String, error: Boolean, onChange: (String) -> Unit) {
    Column (
        modifier = Modifier.fillMaxWidth(),
    ) {
        TextField(
            value = name,
            onValueChange = onChange,
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyMedium,
            singleLine = true,
            isError = error && (name.isBlank()),
            colors = TextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                focusedPlaceholderColor = Color.Transparent,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.scrim,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                errorPlaceholderColor = MaterialTheme.colorScheme.error,
            ),
            placeholder = {
                Text(
                    text = "Enter an ingredient name",
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = FontStyle.Italic
                )
            }
        )
        IndicatorLine(isError = error && (name.isBlank()))
    }
}

@Composable
private fun QuantityTextField (quantity: String, error: Boolean, onChange: (String) -> Unit) {
    Column (
        modifier = Modifier.fillMaxWidth(fraction = 0.5f),
    ) {
        TextField (
            modifier = Modifier.fillMaxWidth(),
            value = quantity,
            onValueChange = onChange,
            textStyle = MaterialTheme.typography.bodyMedium,
            singleLine = true,
            isError = error && (quantity.isBlank()),
            colors = TextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                focusedPlaceholderColor = Color.Transparent,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.scrim,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                errorPlaceholderColor = MaterialTheme.colorScheme.error,),
            placeholder = { Text (
                text = "Enter quantity",
                style = MaterialTheme.typography.bodyMedium,
                fontStyle = FontStyle.Italic,
                maxLines = 1,
                overflow = TextOverflow.Visible)},
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            )
        )
        IndicatorLine(isError = error && (quantity.isBlank()))
    }
}

@Composable
private fun UnitDropdown (
    selectedUnit: MUnit,
    expanded: Boolean,
    onClick: () -> Unit,
    onSelect: (MUnit) -> Unit,
    onDismissRequest: () -> Unit) {
    Column (modifier = Modifier
        .fillMaxWidth()
        .padding(start = 20.dp)) {
        TextButton(onClick = onClick) {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                verticalAlignment = Alignment.CenterVertically) {
                Box (
                    modifier = Modifier.size(width = 24.dp, height = 24.dp),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Icon(
                        modifier = Modifier
                            .size(16.dp)
                            .rotate(if (expanded) 180f else 0f),
                        painter = painterResource(R.drawable.expanded_icon),
                        contentDescription = "Expand icon",
                        tint = MaterialTheme.colorScheme.primary)
                }

                Text(
                    modifier = Modifier,
                    text = selectedUnit.show,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground)
            }
        }
        IndicatorLine(false, 1.5.dp)
        DropdownMenu (
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background),
            expanded = expanded,
            onDismissRequest = onDismissRequest,
        ) {
            Column (
                modifier = Modifier
                    .height(200.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                MUnit.entries.forEach {
                    DropdownMenuItem (
                        text = { Text(
                            text = it.show,
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.bodyMedium) },
                        onClick = {
                            onSelect(it)
                            onDismissRequest()
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun AddManuallyDialog (
    onAdd: (String, Double, MUnit) -> Unit,
    onDismissRequest: () -> Unit
) {
    var error by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var selectedUnit by remember { mutableStateOf(MUnit.G) }
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card (
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                contentAlignment = Alignment.TopEnd,
            ) {
                CloseButton(onDismissRequest)
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp, horizontal = 8.dp)
                ) {
                    NameTextField(name = name, error = error, onChange = {
                        name = it
                        error = false
                    })
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                    )
                    {
                        QuantityTextField(quantity = quantity, error = error, onChange = {
                            quantity = it
                            error = false})

                        UnitDropdown(
                            selectedUnit = selectedUnit,
                            expanded = expanded,
                            onClick = {expanded = true},
                            onSelect = {it: MUnit ->
                                selectedUnit = it},
                            onDismissRequest = {expanded = false}
                        )
                    }
                }
                Box (modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    AddButton(
                        name = name,
                        quantity = quantity,
                        selectedUnit = selectedUnit,
                        onError = { error = true },
                        onAdd = onAdd,
                        onDismissRequest = onDismissRequest
                    )
                }
            }
        }
    }
}