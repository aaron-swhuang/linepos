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
import com.house.linepos.data.ProductTag
import com.house.linepos.data.LocalProductTagRepositoryProvider
import com.house.linepos.data.ViewModelFactory
import com.house.linepos.ui.viewmodel.ProductTagViewModel

@Composable
fun ProductTagScreen() {
    val repository = LocalProductTagRepositoryProvider.current
    val viewModel: ProductTagViewModel =
        viewModel(factory = ViewModelFactory(ProductTagViewModel::class) {
            ProductTagViewModel(repository)
        })

    ProductTagListScreen(viewModel)
}

@Composable
fun ProductTagListScreen(
    viewModel: ProductTagViewModel
) {
    val tags by viewModel.allTags.observeAsState(emptyList())
    var isEditing by rememberSaveable { mutableStateOf(false) }
    var isConfirmingDelete by rememberSaveable { mutableStateOf(false) }
    var isViewing by rememberSaveable { mutableStateOf(false) }
    var currentTagId by rememberSaveable { mutableStateOf<Int?>(null) }
    var currentTag by rememberSaveable { mutableStateOf<ProductTag?>(null) }

    // Dialog to handle add/edit actions
    if (isEditing) {
        TagEditDialog(
            tag = currentTag,
            onConfirm = { tag ->
                if (tag.id == 0) {
                    // Insert new category
                    viewModel.insert(tag)
                } else {
                    // Update existing category
                    viewModel.update(tag)
                }
                isEditing = false
                currentTagId = null
            },
            onDismiss = {
                isEditing = false
                currentTag = null
            }
        )
    }

    // Confirmation Dialog for deletion
    if (isConfirmingDelete) {
        ConfirmDeleteDialog(
            tag = currentTag,
            onConfirm = {
                viewModel.delete(currentTag!!)
                isConfirmingDelete = false
                currentTag = null
            },
            onDismiss = {
                isConfirmingDelete = false
                currentTag = null
            }
        )
    }

    // View Dialog for viewing details
    if (isViewing) {
        TagViewDialog(
            tag = currentTag,
            onDismiss = {
                isViewing = false
                currentTag = null
            }
        )
    }

    Column {
        // Add New Tag Button
        Button(
            onClick = {
                isEditing = true
                currentTag = ProductTag() // empty category for new entry
            },
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text("Add New Tag")
        }
        LazyColumn {
            items(tags) { tag ->
                TagItem(
                    tag = tag,
                    onViewClick = {
                        isViewing = true
                        currentTag = tag
                    },
                    onEditClick = {
                        isEditing = true
                        currentTag = tag
                    },
                    onDeleteClick = {
                        currentTag = tag
                        isConfirmingDelete = true // Show delete confirmation dialog
                    }
                )
            }
        }
    }
}

@Composable
fun TagItem(
    tag: ProductTag,
    onViewClick: (ProductTag) -> Unit,
    onEditClick: (ProductTag) -> Unit,
    onDeleteClick: (ProductTag) -> Unit) {

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
            Text(text = tag.name)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (!tag.isActive) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_block_24dp),
                    contentDescription = "Block",
                    tint = Color.Red
                )
            }

            // View Button
            IconButton(onClick = { onViewClick(tag) }) {
                Icon(Icons.Default.Search, contentDescription = "View")
            }
            // Edit Button
            IconButton(onClick = { onEditClick(tag) }) {
                Icon(Icons.Default.Edit, contentDescription = "Edit")
            }
            // Delete Button
            IconButton(onClick = { onDeleteClick(tag) }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}

@Composable
fun TagEditDialog(
    tag: ProductTag?,
    onConfirm: (ProductTag) -> Unit,
    onDismiss: () -> Unit
) {
    var tagName by rememberSaveable { mutableStateOf(tag?.name ?: "") }
    var description by rememberSaveable { mutableStateOf(tag?.description ?: "") }
    var isActive by rememberSaveable { mutableStateOf(tag?.isActive ?: true) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = if (tag?.id == 0) "Add Tag" else "Edit Tag") },
        text = {
            Column {
                OutlinedTextField(
                    value = tagName,
                    onValueChange = { tagName = it },
                    label = { Text("Tag Name") },
                    singleLine = true,
                    isError = tagName.isBlank(), // This means that the field must be filled.
                    placeholder = { Text("Enter tag name") }
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
                        onCheckedChange = {
                            // TODO: Option 1: Remove inactive tag id from product's id list,
                            //       Option 2: not showing the inactive tag while viewing the
                            //                 product or editing the product.
                            //       Option 3: It cannot be set as inactive if there's a product is
                            //                 available.
                            isActive = it
                        }
                    )
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                if (tagName.isNotEmpty()) {
                    onConfirm(
                        ProductTag(
                            id = tag?.id ?: 0,
                            name = tagName,
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
    tag: ProductTag?,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Confirm Deletion") },
        text = { Text("Are you sure you want to delete the category \"${tag?.name}\"?") },
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
fun TagViewDialog(
    tag: ProductTag?,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Tag Details") },
        text = {
            Column {
                Text("Category: ${tag?.name}")
                Text("Description: ${tag?.description ?: "No description"}")
                Text("Active: ${if (tag?.isActive == true) "Yes" else "No"}")
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
fun ProductTagScreenPreview() {
    ProductTagScreen()
}