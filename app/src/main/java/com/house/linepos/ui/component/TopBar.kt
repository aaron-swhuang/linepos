package com.house.linepos.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.house.linepos.Settings
import com.house.linepos.ShoppingCart

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LinePosTopBar(datetime: String) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = datetime,
                fontSize = 14.sp
                )
                },
        navigationIcon = {
            IconButton(onClick = { /* TODO: Menu */ }) {
                // TODO: Make screen can go back to previous screen.
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
                //Icon(
                //    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                //    contentDescription = "Localized description"
                //)
            }
        },
        actions = {
            IconButton(onClick = { /* TODO: ShoppingCartScreen */ }) {
                Icon(imageVector = ShoppingCart.icon, contentDescription = ShoppingCart.description)
            }
            IconButton(onClick = { /*TODO: SettingsScreen */ }) {
                Icon(imageVector = Settings.icon, contentDescription = Settings.description)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun LinePosTopBarPreview() {
    LinePosTopBar("Aug 29, 2024 10:21:22 PM")
}