package com.house.linepos.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProductCategoryScreen() {

}

@Composable
fun CreateProductCategory() {
    var category by rememberSaveable { mutableStateOf("")}
    var description by rememberSaveable { mutableStateOf("")}
    var checked by rememberSaveable { mutableStateOf(false) }

    Surface(modifier = Modifier.padding(8.dp)) {
        Column(
            //horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)) {
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
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "is active?",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(end = 32.dp))
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
fun CreateProductCategoryPreview() {
    CreateProductCategory()
}