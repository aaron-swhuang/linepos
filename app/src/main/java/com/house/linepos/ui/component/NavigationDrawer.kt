package com.house.linepos.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.house.linepos.ui.screen.About
import com.house.linepos.ui.screen.CreateProductCategory
import com.house.linepos.ui.screen.Home
import com.house.linepos.ui.screen.ProductCategory
import com.house.linepos.ui.screen.ProductCategoryScreen

@Composable
fun NavigationDrawer(
    drawerState: DrawerState,
    onItemSelected: (String) -> Unit,
    content: @Composable () -> Unit
    ) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(modifier = Modifier.width(240.dp)) {
                Text("Line POS", modifier = Modifier.padding(16.dp))
                Divider()
                NavigationDrawerItem(
                    label = { Text(CreateProductCategory.label) },
                    selected = false ,
                    onClick = { onItemSelected(CreateProductCategory.label) }
                )
                NavigationDrawerItem(
                    label = { Text(About.label) },
                    selected = false ,
                    onClick = { onItemSelected(About.label) }
                )
            } // ModalDrawerSheet
        }, // drawer content
        content = content
    ) // ModalNavigationDrawer
}

@Preview(showBackground = true)
@Composable
fun NavigationDrawerPreview() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    NavigationDrawer(drawerState = drawerState, onItemSelected = { selectedItem ->
        println("Selected item: $selectedItem")
    }, content = {})
}