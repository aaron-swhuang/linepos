package com.house.linepos.ui.screen

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProductCategoryScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Button(onClick = { /*TODO*/ }) {
            Text(text = "New")
        }
        LazyColumn {
            /* TODO: Get category data from database */
        }
    }
}

@Composable
fun CreateProductCategory() {
    var category by rememberSaveable { mutableStateOf("")}
    var description by rememberSaveable { mutableStateOf("")}
    var checked by rememberSaveable { mutableStateOf(false) }

    Surface(modifier = Modifier.padding(8.dp)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .verticalScroll(rememberScrollState())) {
            Text(
                text = "Create a new product category",
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            TextField(
                value = category,
                label = { Text("Category") },
                onValueChange = { category = it },
                singleLine = true,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            TextField(
                value = description,
                label = { Text("Description") },
                onValueChange = { description = it },
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "is active?",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(end = 132.dp))
                Switch(
                    checked = checked,
                    onCheckedChange = { checked = it}
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .width(IntrinsicSize.Max)

            ) {
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    Text(text = "Confirm")
                }
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                ) {
                    Text(text = "Cancel")
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductCategoryScreenPreview() {

    ProductCategoryScreen()
}

@Preview(showBackground = true)
@Composable
fun CreateProductCategoryPreview() {
    CreateProductCategory()
}