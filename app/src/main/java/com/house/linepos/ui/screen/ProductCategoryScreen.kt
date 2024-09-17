package com.house.linepos.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.house.linepos.R
import com.house.linepos.data.LocalProductCategoryRepository
import com.house.linepos.data.ProductCategory
import com.house.linepos.data.ViewModelFactory
import com.house.linepos.ui.viewmodel.ProductCategoryViewModel

@Composable
fun ProductCategoryScreen() {
    val repository = LocalProductCategoryRepository.current
    val viewModel: ProductCategoryViewModel =
        viewModel(factory = ViewModelFactory(ProductCategoryViewModel::class) {
        ProductCategoryViewModel(repository)
    })

    ProductCategoryListScreen(viewModel)
}

@Composable
fun ProductCategoryListScreen(
    viewModel: ProductCategoryViewModel
) {
    val categories by viewModel.allCategories.observeAsState(emptyList())
    var isEditing by rememberSaveable { mutableStateOf(false) }
    var isConfirmingDelete by rememberSaveable { mutableStateOf(false) }
    var isViewing by rememberSaveable { mutableStateOf(false) }
    var currentCategoryId by rememberSaveable { mutableStateOf<Int?>(null) }
    var currentCategory by rememberSaveable { mutableStateOf<ProductCategory?>(null) }

    // Dialog to handle add/edit actions
    if (isEditing) {
        CategoryEditDialog(
            category = currentCategory,
            onConfirm = { category ->
                if (category.id == 0) {
                    // Insert new category
                    viewModel.insert(category)
                } else {
                    // Update existing category
                    viewModel.update(category)
                }
                isEditing = false
                currentCategoryId = null
            },
            onDismiss = {
                isEditing = false
                currentCategory = null
            }
        )
    }

    // Confirmation Dialog for deletion
    if (isConfirmingDelete) {
        ConfirmDeleteDialog(
            category = currentCategory,
            onConfirm = {
                viewModel.delete(currentCategory!!)
                isConfirmingDelete = false
                currentCategory = null
            },
            onDismiss = {
                isConfirmingDelete = false
                currentCategory = null
            }
        )
    }

    // View Dialog for viewing details
    if (isViewing) {
        CategoryViewDialog(
            category = currentCategory,
            onDismiss = {
                isViewing = false
                currentCategory = null
            }
        )
    }

    Column {
        // Add New Category Button
        Button(
            onClick = {
            isEditing = true
            currentCategory = ProductCategory() // empty category for new entry
            },
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text("Add New Category")
        }
        LazyColumn {
            items(categories) { category ->
                CategoryItem(
                    category = category,
                    onViewClick = {
                        isViewing = true
                        currentCategory = category
                    },
                    onEditClick = {
                        isEditing = true
                        currentCategory = category
                                  },
                    onDeleteClick = {
                        currentCategory = category
                        isConfirmingDelete = true // Show delete confirmation dialog
                    }
                )
            }
        }
    }
}

@Composable
fun CategoryItem(
    category: ProductCategory,
    onViewClick: (ProductCategory) -> Unit,
    onEditClick: (ProductCategory) -> Unit,
    onDeleteClick: (ProductCategory) -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .drawBehind {
                val strokeWidth = 2f
                val y = size.height - strokeWidth / 2
                drawLine(
                    color = Color.Gray,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = strokeWidth
                )
            }
            .padding(2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = category.category)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (!category.isActive) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_block_24dp),
                    contentDescription = "Block",
                    tint = Color.Red
                )
            }
            // View Button
            IconButton(onClick = { onViewClick(category) }) {
                Icon(Icons.Default.Search, contentDescription = "View")
            }
            // Edit Button
            IconButton(onClick = { onEditClick(category) }) {
                Icon(Icons.Default.Edit, contentDescription = "Edit")
            }
            // Delete Button
            IconButton(onClick = { onDeleteClick(category) }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}

@Composable
fun CategoryEditDialog(
    category: ProductCategory?,
    onConfirm: (ProductCategory) -> Unit,
    onDismiss: () -> Unit
) {
    var categoryName by rememberSaveable { mutableStateOf(category?.category ?: "") }
    var description by rememberSaveable { mutableStateOf(category?.description ?: "") }
    var isActive by rememberSaveable { mutableStateOf(category?.isActive ?: true) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = if (category?.id == 0) "Add Category" else "Edit Category") },
        text = {
            Column {
                OutlinedTextField(
                    value = categoryName,
                    onValueChange = { categoryName = it },
                    label = { Text("Category Name") },
                    singleLine = true,
                    isError = categoryName.isBlank(), // This means that the field must be filled.
                    placeholder = { Text("Enter category name") }
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    placeholder = { Text("Enter description (optional)") }
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Active", modifier = Modifier.padding(end = 12.dp))
                    Switch(
                        checked = isActive,
                        onCheckedChange = { isActive = it }
                    )
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                if (categoryName.isNotEmpty()) {
                    onConfirm(
                        ProductCategory(
                            id = category?.id ?: 0,
                            category = categoryName,
                            description = description,
                            isActive = isActive
                        )
                    )
                } // TODO: else show error message
            }) {
                Text("Confirm")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun ConfirmDeleteDialog(
    category: ProductCategory?,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Confirm Deletion") },
        text = { Text("Are you sure you want to delete the category \"${category?.category}\"?") },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text("Delete")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun CategoryViewDialog(
    category: ProductCategory?,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Category Details") },
        text = {
            Column {
                Text("Category: ${category?.category}")
                Text("Description: ${category?.description ?: "No description"}")
                Text("Active: ${if (category?.isActive == true) "Yes" else "No"}")
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ProductCategoryScreenPreview() {
    ProductCategoryScreen()
}