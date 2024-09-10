package com.house.linepos.ui.component

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.house.linepos.ui.screen.bottomItems

val TAG = "LinePOS"
@Composable
fun LinePosBottomBar(
    onItemClick: (String) -> Unit
) {
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }
    NavigationBar {
        bottomItems.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(item.icon, item.description) },
                label = { Text(item.label) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    onItemClick(item.route)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LinePosBottomBarPreview() {
    LinePosBottomBar({})
}