package com.house.linepos.ui.component

import android.util.Log
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.testing.TestNavHostController
import com.house.linepos.bottomItems

val TAG = "LinePOS"
@Composable
fun LinePosBottomBar(
    navHostController: NavHostController
) {
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }
    NavigationBar {
        bottomItems.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(item.icon, item.description) },
                label = { Text(item.label) },
                selected = selectedItem == index,
                onClick = {
                    Log.v(TAG, "${item.route} is clicked")
                    selectedItem = index
                    navHostController.navigate(item.route) {
                        Log.v(TAG, "navigate to ${item.route}")
                        popUpTo(navHostController.graph.findStartDestination().id){
                            Log.v(TAG, "${item.route} save state")
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LinePosBottomBarPreview() {
    val textNavController = TestNavHostController(LocalContext.current)
    LinePosBottomBar(textNavController)
}