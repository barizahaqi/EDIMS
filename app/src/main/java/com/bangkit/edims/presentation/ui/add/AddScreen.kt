package com.bangkit.edims.presentation.ui.add

import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.bangkit.edims.R
import com.bangkit.edims.database.Product
import com.bangkit.edims.components.CameraCapture
import com.bangkit.edims.components.CategoryItem
import com.bangkit.edims.theme.MyProjectTheme
import com.bangkit.edims.theme.Orange2
import com.bangkit.edims.theme.Shapes
import com.bangkit.edims.core.utils.DatePickerDialog

@Composable
fun AddScreen(
    addViewModel: AddViewModel,
    navigateToHome: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AddContent(
            onSaveDataClick = addViewModel::insert,
            navigateToHome = navigateToHome
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddContent(
    onSaveDataClick: (Product) -> Unit,
    navigateToHome: () -> Unit
) {
    var inputName by remember { mutableStateOf("") }
    var inputDate by remember { mutableStateOf("") }
    var dueDateMillis by remember {
        mutableStateOf(System.currentTimeMillis())
    }

    var selectedCategory by remember { mutableStateOf("Category") }
    var expanded by remember { mutableStateOf(false) }

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var cameraSelect by remember {
        mutableStateOf(false)
    }

    var showDatePicker by remember {
        mutableStateOf(false)
    }

    if (!cameraSelect) {
        Column(
            modifier = Modifier
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Add Product",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .padding(16.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .border(1.dp, Color.Black)
            ) {
                AsyncImage(
                    model = imageUri,
                    contentDescription = "Preview Image",
                    placeholder = painterResource(R.drawable.ic_placeholder),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(30.dp)
                        .align(Alignment.Center),
                    error = painterResource(R.drawable.ic_placeholder)
                )
                FloatingActionButton(
                    onClick = {
                        cameraSelect = true
                    },
                    modifier = Modifier
                        .padding(15.dp)
                        .size(50.dp)
                        .align(Alignment.BottomEnd),
                    shape = CircleShape,
                ) {
                    Icon(
                        imageVector = Icons.Default.PhotoCamera,
                        contentDescription = "Camera",
                        modifier = Modifier
                            .size(30.dp)
                    )
                }
            }
            OutlinedTextField(
                value = inputName,
                label = { Text(text = "Name") },
                onValueChange = { newInput ->
                    inputName = newInput
                },
                shape = Shapes.medium,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = inputDate,
                    label = { Text(text = "Date") },
                    onValueChange = {},
                    readOnly = true,
                    shape = Shapes.medium,
                    modifier = Modifier
                        .weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = "Choose Date",
                    modifier = Modifier
                        .clickable {
                            showDatePicker = true
                        }
                        .padding(8.dp)
                )
            }
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                OutlinedTextField(
                    value = selectedCategory,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    val categoryItem = CategoryItem.items
                    categoryItem.map { item ->
                        DropdownMenuItem(
                            text = { Text(item.name) },
                            onClick = {
                                selectedCategory = item.name
                                expanded = false
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = null,
                                )
                            }
                        )
                    }
                }
            }
            Button(
                onClick = {
                    val product = Product(
                        name = inputName,
                        category = selectedCategory,
                        image = imageUri.toString(),
                        dueDateMillis = dueDateMillis,
                    )
                    onSaveDataClick(product)
                    navigateToHome()
                },
                shape = Shapes.medium,
                modifier = Modifier
                    .width(120.dp)
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(Orange2)
            ) {
                Text(
                    text = "Save",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    } else {
        CameraCapture(
            onImageFile = { file ->
                imageUri = file.toUri()
            },
            onDismiss = { cameraSelect = false }
        )
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDateSelected = { inputDate = it },
            onDateMillisSelected = { dueDateMillis = it },
            onDismiss = { showDatePicker = false }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun AddContentScreenPreview() {
    MyProjectTheme {
        AddContent(
            onSaveDataClick = {},
            navigateToHome = {}
        )
    }
}
