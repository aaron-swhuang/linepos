package com.house.linepos.ui.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.house.linepos.R
import com.house.linepos.data.LocalOrderItemRepositoryProvider
import com.house.linepos.data.LocalOrderRepositoryProvider
import com.house.linepos.data.LocalProductCategoryRepositoryProvider
import com.house.linepos.data.LocalProductRepositoryProvider
import com.house.linepos.data.LocalProductTagRepositoryProvider
import com.house.linepos.data.OrderMenuItemDetails
import com.house.linepos.data.Product
import com.house.linepos.data.ViewModelFactory
import com.house.linepos.ui.viewmodel.CartViewModel
import com.house.linepos.ui.viewmodel.ProductViewModel

@Composable
fun NewOrderScreen() {
    val productRepo = LocalProductRepositoryProvider.current
    val categoryRepo = LocalProductCategoryRepositoryProvider.current
    val tagRepo = LocalProductTagRepositoryProvider.current
    val orderRepo = LocalOrderRepositoryProvider.current
    val orderItemRepo = LocalOrderItemRepositoryProvider.current

    val cartViewModel: CartViewModel =
        viewModel(factory = ViewModelFactory(CartViewModel::class) {
            CartViewModel(productRepo, categoryRepo, tagRepo, orderRepo, orderItemRepo)
        })

    val orderMenuItems by cartViewModel.orderMenuItems.observeAsState()
    OrderScreen(orderMenuItems, cartViewModel)
}

@Composable
fun OrderScreen(
    products: List<OrderMenuItemDetails>?,
    cartViewModel: CartViewModel
) {
    val hasSelectedItems by cartViewModel.hasSelectedItems.observeAsState(false)

    // The items in shopping cart
    Scaffold(
        topBar = { Text("search bar") },
        content = { padding ->
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(padding)
                    .background(Color.Red)) {

                LazyColumn {
                    if (products != null) {
                        items(products) { product ->
                            ProductItemCard(product,
                                // TODO: showImgage should be designed as a config so user can
                                //  decide whether the image will be shown or not.
                                true,
                                cartViewModel
                            )
                        }
                    }

                }
            }

        }, // End of content
        bottomBar = {
            Button(
                onClick = {
                    cartViewModel.addItemsToCart()
                },
                enabled = hasSelectedItems,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add to shopping cart")
            }
        }

    )
}

// TODO: EditOrderMenuScreen to adjust the order of the content.

// TODO: EditOrderScreen to edit the order.

@Composable
fun ProductItemCard(
    details: OrderMenuItemDetails,
    showImage: Boolean,
    cartViewModel: CartViewModel
) {
    val maxQuantity = 99
    val product = details.productDetails
    val quantity = details.quantity.toIntOrNull() ?: 0

    Card(modifier = Modifier.fillMaxWidth()) {
        Row{
            if (showImage) {
                // TODO: Define the initial value of the imagePath to be only "" or null.
                if (product.imagePath.equals("") || product.imagePath == null) {
                    Image(
                        painter = painterResource(id = R.drawable.no_image_128dp),
                        contentDescription = "Placeholder Image",
                        modifier = Modifier.size(128.dp)
                    )
                } else {
                    // show image
                    AsyncImage(
                        model = product.imagePath,
                        contentDescription = "Product Image",
                        modifier = Modifier.size(128.dp)
                    )
                }
            }
            Column() {
                Text(text = product.name)

                // TODO: format price
                Text("price: ${product.price}")

                Row() {
                    IconButton(onClick = {
                        if (quantity > 0) {
                            Log.d(TAG, "quantity: ${quantity}")
                            cartViewModel.updateQuantity(product, (quantity - 1).toString())
                        }
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_remove_24dp),
                            contentDescription = null,
                            modifier = Modifier
                                .size(16.dp)
                                .background(Color.LightGray))
                    }

                    // TODO: Input logic should be refined.
                    TextField(
                        value = quantity.toString(),
                        onValueChange = {
                            input ->
                            val newQuantity = input.toIntOrNull() ?: 0
                            cartViewModel.updateQuantity(product, newQuantity.toString())
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        modifier = Modifier
                            .width(80.dp)
                            .height(56.dp)
                            .background(Color.Magenta)
                    )

                    IconButton(onClick = {
                        if (quantity <= maxQuantity) {
                            cartViewModel.updateQuantity(product, (quantity + 1).toString())
                            Log.d(TAG, "quantity: ${quantity}")
                        }
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_add_24dp),
                            contentDescription = null,
                            modifier = Modifier
                                .size(16.dp)
                                .background(Color.LightGray)
                        )
                    }
                }
            }
        }
    }
}

/*
@Composable
fun NewOrderScreen() {
    val productRepo = LocalProductRepositoryProvider.current
    val categoryRepo = LocalProductCategoryRepositoryProvider.current
    val tagRepo = LocalProductTagRepositoryProvider.current
    val orderRepo = LocalOrderRepositoryProvider.current
    val orderItemRepo = LocalOrderItemRepositoryProvider.current

    val productViewModel: ProductViewModel =
        viewModel(factory = ViewModelFactory(ProductViewModel::class) {
            ProductViewModel(productRepo, categoryRepo, tagRepo)
        })
    val cartViewModel: CartViewModel =
        viewModel(factory = ViewModelFactory(CartViewModel::class) {
            CartViewModel(productRepo, categoryRepo, tagRepo, orderRepo, orderItemRepo)
        })
    //val orderMenuItems by cartViewModel.orderMenuItems.observeAsState()
    OrderScreen(cartViewModel)
}

@Composable
fun OrderScreen(
    //products: List<OrderMenuItemDetails>?,
    cartViewModel: CartViewModel
) {
    val products by cartViewModel.orderMenuItems.observeAsState(emptyList())
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // LazyColumn to list all items
        LazyColumn {
            items(products ?: emptyList()) { product ->
                ProductItemCard(details = product, cartViewModel = cartViewModel)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Add to cart button
        Button(
            onClick = {
                // add items to cart and reset the quantity to 0
                cartViewModel.addToCartAndReset()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Add to cart")
        }
    }
}

@Composable
fun ProductItemCard(
    details: OrderMenuItemDetails,
    cartViewModel: CartViewModel
) {
    // current quantity，from string to Int
    //var quantity by rememberSaveable { mutableStateOf(details.quantity.toInt()) }
    val quantity = details.quantity.toIntOrNull() ?: 0

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = details.productDetails.name)
                Text(text = "$${details.productDetails.price}")
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(
                    onClick = {
                        if (quantity > 0) //quantity-- // keep quantity not less than 0
                        cartViewModel.updateProductQuantity(details.productDetails, quantity - 1)
                    }
                ) {
                    Text("-")
                }

                Spacer(modifier = Modifier.width(8.dp))

                // TextField for display and input quantity
                TextField(
                    value = quantity.toString(),
                    onValueChange = { input ->
                        val newQuantity = input.toIntOrNull() ?: 0
                        //quantity = newQuantity
                        cartViewModel.updateProductQuantity(details.productDetails, newQuantity)
                    },
                    modifier = Modifier.width(50.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        //quantity++
                        cartViewModel.updateProductQuantity(details.productDetails, quantity + 1)
                    }
                ) {
                    Text("+")
                }
            }
        }
    }
}

 */