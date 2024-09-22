package com.house.linepos.ui.screen

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.house.linepos.R
import com.house.linepos.data.LocalNavController
import com.house.linepos.data.LocalProductCategoryRepositoryProvider
import com.house.linepos.data.LocalProductRepositoryProvider
import com.house.linepos.data.LocalProductTagRepositoryProvider
import com.house.linepos.data.Product
import com.house.linepos.data.ProductCategory
import com.house.linepos.data.ProductTag
import com.house.linepos.data.ViewModelFactory
import com.house.linepos.ui.viewmodel.ProductViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

val TAG = "LinePOS"

@Composable
fun ProductScreen() {
    val navController = rememberNavController()
    val productRepo = LocalProductRepositoryProvider.current
    val categoryRepo = LocalProductCategoryRepositoryProvider.current
    val tagRepo = LocalProductTagRepositoryProvider.current
    val viewModel: ProductViewModel =
        viewModel(factory = ViewModelFactory(ProductViewModel::class) {
            ProductViewModel(productRepo, categoryRepo, tagRepo)
        })

    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(
            navController = navController,
            startDestination = "productList"
        ) {
            composable("productList") {
                ProductListScreen(viewModel)
            }
            composable("viewProduct/{productId}") { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId")?.toIntOrNull()
                ViewProductScreen(viewModel, productId)
            }
            composable("editProduct/{productId}") { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId")?.toIntOrNull()
                EditProductScreen(viewModel, productId)
            }
            composable("addProduct") {
                EditProductScreen(viewModel, null)  // null means add a new product.
            }
        }
    }
}

@Composable
fun ProductListScreen(viewModel: ProductViewModel) {
    val products by viewModel.allProducts.observeAsState(emptyList())
    val navController = LocalNavController.current
    var isConfirmingDelete by rememberSaveable { mutableStateOf(false) }
    var currentProduct by rememberSaveable { mutableStateOf<Product?>(null) }

    if (isConfirmingDelete) {
        ConfirmDeleteDialog(
            product = currentProduct,
            onConfirm = {
                viewModel.delete(currentProduct!!)
                isConfirmingDelete = false
                currentProduct = null
            },
            onDismiss = {
                isConfirmingDelete = false
                currentProduct = null
            }
        )
    }
    Column {
        Button(
            onClick = { navController.navigate("addProduct") }) {
            Text("Add New Product")
        }

        LazyColumn {
            items(products) { product ->
                ProductItem(
                    product = product,
                    onItemClick = {
                        navController.navigate("viewProduct/${product.id}")
                    },
                    onViewClick = {
                        navController.navigate("viewProduct/${product.id}")
                    },
                    onEditClick = {
                        navController.navigate("editProduct/${product.id}")
                    },
                    onDeleteClick = {
                        currentProduct = product
                        isConfirmingDelete = true
                    }
                )
            }
        }
    }
}

@Composable
fun ProductItem(
    product: Product,
    onItemClick: () -> Unit,
    onViewClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
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
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = product.name, modifier = Modifier.clickable { onItemClick() })
        Row(verticalAlignment = Alignment.CenterVertically) {
            // If the product is not available then show a block icon.
            if (!product.isAvailable) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_block_24dp),
                    contentDescription = "Block",
                    tint = Color.Red
                )
            }
            IconButton(onClick = onViewClick) {
                Icon(Icons.Default.Search, contentDescription = "View")
            }
            IconButton(onClick = onEditClick) {
                Icon(Icons.Default.Edit, contentDescription = "Edit")
            }
            IconButton(onClick = onDeleteClick) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EditProductScreen(viewModel: ProductViewModel, productId: Int?) {
    val navController = LocalNavController.current
    val context = LocalContext.current
    var imageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    // Keep track of old image path. If the image is replaced with new image then old one should
    // be deleted.
    var oldImagePath by rememberSaveable { mutableStateOf<String?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            imageUri = uri
        }
    )

    val activeCategories by viewModel.activeCategories.observeAsState()
    val activeTags by viewModel.activeTags.observeAsState()

    var selectedCategory by rememberSaveable { mutableStateOf<ProductCategory?>(null) }

    var isDropdownExpanded by rememberSaveable { mutableStateOf(false) }
    var isDropdownTagsExpanded by rememberSaveable { mutableStateOf(false) }
    var isDropdownAvailableTagsExpanded by rememberSaveable { mutableStateOf(false) }
    // Tag selection using TextField (with auto-suggestions on input of #)
    var tagInput by rememberSaveable { mutableStateOf("") }

    var availableTags by rememberSaveable { mutableStateOf(activeTags?.toMutableList() ?: mutableListOf()) }
    // Extract selectedTags from product's tagId.
    var selectedTags by rememberSaveable { mutableStateOf<MutableList<ProductTag>>(mutableListOf()) }

    if (availableTags.isEmpty() && activeTags != null) {
        availableTags = activeTags!!.toMutableList()
    }

    var name by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var price by rememberSaveable { mutableStateOf(0.0) }
    var isAvailable by rememberSaveable { mutableStateOf(false) }
    var imagePath by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(productId) {
        // viewModel will save the result at the variable productById.
        productId?.let { viewModel.getProductById(it) }
    }

    val product by viewModel.productById.observeAsState()
    LaunchedEffect(product) {
        product?.let {
            name = it.name
            price = it.price
            description = it.description ?: ""
            imagePath = it.imagePath ?: ""
            isAvailable = it.isAvailable
            selectedCategory = activeCategories?.find { category -> category.id == it.categoryId }
            selectedTags = activeTags?.filter { tag -> it.tags?.contains(tag.id) == true }?.toMutableList() ?: mutableListOf()
            availableTags = activeTags?.filter { tag -> it.tags?.contains(tag.id) == false }?.toMutableList() ?: mutableListOf()
        }
    }

    val scrollState = rememberScrollState()

    Column(
        Modifier
            .padding(16.dp)
            .verticalScroll(scrollState) ) {
        Button(
            onClick = { navController.popBackStack() }
        ) {
            Text(text = "Back to list")
        }
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Product Name") },
            placeholder = { if (productId == null) Text("Enter product name")}
        )
        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") }
        )
        TextField(
            value = price.toString(),
            onValueChange = { price = it.toDoubleOrNull() ?: 0.0 },
            label = { Text("Price") }
        )
        // Select category
        Box {
            TextField(
                value = selectedCategory?.category ?: "",
                onValueChange = { },
                label = { Text("Product Category") },
                readOnly = true,
                trailingIcon = { Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        Log.e(TAG, "Dropdown menu is clicked via icon")
                        isDropdownExpanded = true }
                )},
                modifier = Modifier
                    .clickable {
                        Log.e(TAG, "Dropdown menu is clicked")
                        isDropdownExpanded = true
                    }
                    .fillMaxWidth()
            )
            // TODO: scrollState
            DropdownMenu(
                expanded = isDropdownExpanded,
                onDismissRequest = { isDropdownExpanded = false }
            ) {
                activeCategories?.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(text = category.category) },
                        onClick = {
                            selectedCategory = category
                            isDropdownExpanded = false
                        }
                    )
                }
            }
        }

        // Tag input field
        TextField(
            value = tagInput,
            onValueChange = {
                tagInput = it
                isDropdownTagsExpanded = it.isNotEmpty() },
            label = { Text("Add Tags") },
            placeholder = { Text("Enter tags") },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Tag",
                    modifier = Modifier.clickable {
                        // If input is empty, show all available tags
                        isDropdownAvailableTagsExpanded = tagInput.isEmpty()
                        if (tagInput.isNotEmpty()) {
                            isDropdownTagsExpanded = true
                        }
                    }
                ) },
            modifier = Modifier.fillMaxWidth()
        )

        // Dropdown for searching tags
        // TODO-1: The position of the dropdown menu should be adjusted
        // TODO-2: Maybe this DropdownMenu can be combined with the menu controlled by
        //         isDropdownAvailableTagsExpanded
        DropdownMenu(
            expanded = isDropdownTagsExpanded,
            onDismissRequest = { isDropdownTagsExpanded = false }
        ) {
            availableTags.filter { it.name.startsWith(tagInput, ignoreCase = true) }.forEach { tag ->
                DropdownMenuItem(
                    text = { Text(text = tag.name) },
                    onClick = {
                        selectedTags = selectedTags.toMutableList().apply { add(tag) }
                        availableTags = availableTags.toMutableList().apply { remove(tag) }
                        tagInput = "" // Clear input
                        isDropdownTagsExpanded = false
                        Log.e(TAG, "searching ${tag.name}, availableTags: ${availableTags}") })
            }
        }
        // Display selected tags
        FlowRow(
            modifier = Modifier.padding(top = 8.dp)
        ) {
            selectedTags.forEach { tag ->
                InputChip(
                    label = { Text(text = tag.name) },
                    onClick = {},
                    selected = false,
                    trailingIcon = {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Remove Tag",
                            modifier = Modifier.clickable {
                                // TODO: Try selectedTags.remove(tag) and availableTags.add(tag)
                                selectedTags = selectedTags.toMutableList().apply { remove(tag) }
                                availableTags = availableTags.toMutableList().apply { add(tag) }
                                Log.e(TAG, "delete ${tag.name}, availableTags: ${availableTags}")
                            }
                        )
                    },
                    modifier = Modifier.padding(2.dp)
                )
            }
        }

        // TODO: The position of the dropdown menu should be adjusted
        //       like offset = DpOffset(x = 0.dp, y = 10.dp)
        // Dropdown for available tags
        DropdownMenu(
            expanded = isDropdownAvailableTagsExpanded,
            onDismissRequest = { isDropdownAvailableTagsExpanded = false }
        ) {
            availableTags.forEach { tag ->
                DropdownMenuItem(
                    text = { Text(text = tag.name) },
                    onClick = {
                        // TODO: Try selectedTags.add(tag) and availableTags.remove(tag)
                        selectedTags = selectedTags.toMutableList().apply { add(tag) }
                        availableTags = availableTags.toMutableList().apply { remove(tag) }
                        isDropdownAvailableTagsExpanded = false
                        Log.e(TAG, "available ${tag.name}, availableTags: ${availableTags}") })
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Active", modifier = Modifier.padding(end = 12.dp))
            Switch(
                checked = isAvailable,
                onCheckedChange = { isAvailable = it }
            )
        }

        Button(onClick = { imagePickerLauncher.launch("image/*") }) {
            Text("Pick Image")
        }

        // Display selected image
        imageUri?.let { uri ->
            AsyncImage(
                model = uri,
                contentDescription = "Selected Image",
                modifier = Modifier
                    .size(128.dp)
                    .padding(8.dp)
            )
        }

        Button(onClick = {
            // Save the image to device and get the file path
            val savedImagePath = imageUri?.let { saveImageToInternalStorage(context, it) }
            if (savedImagePath != null) {
                imagePath = savedImagePath
                // Delete the old image if it exists and a new image was selected
                oldImagePath?.let { deleteImageFromInternalStorage(context, it) }
            }
            // Convert selectedTags to List<Int>
            val tagIds = selectedTags.map { it.id }
            val categoryId = selectedCategory?.id

            if (productId == null) {
                // create new product
                viewModel.insert(Product(0, name, price, description, imagePath, isAvailable, tagIds, categoryId))
            } else {
                // update product
                viewModel.update(Product(productId, name, price, description, imagePath, isAvailable, tagIds, categoryId))
            }
            navController.popBackStack()
        }) {
            Text("Save")
        }
    }
}

// Function to save image to internal storage
private fun saveImageToInternalStorage(context: Context, uri: Uri): String? {
    return try {
        // Create a file in the app's internal storage
        val fileName = "${System.currentTimeMillis()}.jpg"
        val file = File(context.filesDir, fileName)

        // Open an input stream to read the selected image
        val inputStream = context.contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)

        // Copy the input stream to the output stream (saving the file)
        inputStream?.copyTo(outputStream)

        inputStream?.close()
        outputStream.close()

        // Return the absolute path of the saved file
        file.absolutePath
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}

// Function to delete the old image from internal storage
private fun deleteImageFromInternalStorage(context: Context, imagePath: String): Boolean {
    val file = File(imagePath)
    return file.exists() && file.delete()
}

@Composable
fun ViewProductScreen(viewModel: ProductViewModel, productId: Int?) {
    val navController = LocalNavController.current
    LaunchedEffect(productId) {
        productId?.let { viewModel.getProductById(it) }
    }
    val product by viewModel.productById.observeAsState()


    Column(Modifier.padding(16.dp)) {
        Button(
            onClick = { navController.popBackStack() }
        ) {
            Text(text = "Back to list")
        }
        product?.let {
            Text("Product Name: ${it.name}")
            Text("Description: ${it.description}")
            Text("Price: ${it.price}")
            Text(
                "Available: ${if (it.isAvailable) "Yes" else "No"}",
                color = if (!it.isAvailable) Color.Red else Color.Unspecified
            )
            Spacer(modifier = Modifier.height(16.dp))

            it.imagePath?.let { imagePath ->
                AsyncImage(
                    model = imagePath,
                    contentDescription = "Product Image",
                    modifier = Modifier.size(128.dp)
                )
            } ?: Text("No image available")
        } ?: Text("Product not found.")
    }
}

@Composable
fun ConfirmDeleteDialog(
    product: Product?,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Confirm Deletion") },
        text = { Text("Are you sure you want to delete the category \"${product?.name}\"?") },
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