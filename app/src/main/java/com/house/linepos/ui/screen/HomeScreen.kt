package com.house.linepos.ui.screen

import ActionButton
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.house.linepos.R

const val maxItemNum = 4

data class actionItem(
    val imageVector: ImageVector,
    val label: String
)

@Composable
fun HomeScreen() {
    val items = listOf(
        actionItem(ImageVector.vectorResource(id = R.drawable.ic_menu_book_24dp), "Menu"),
        actionItem(ImageVector.vectorResource(id = R.drawable.ic_table_restaurant_24dp), "Table"),
    )
    ListActionsScreen(items)
}

@Composable
fun ListActionsScreen(actions: List<actionItem>) {
    Surface(modifier = Modifier.padding(8.dp)) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(maxItemNum),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(actions) { action ->
                ActionButton(
                    img = action.imageVector,
                    label = { Text(action.label, fontSize = 12.sp) }
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}