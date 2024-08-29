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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.house.linepos.Settings
import com.house.linepos.ShoppingCart
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LinePosTopBar() {
    var current by remember { mutableStateOf(LocalDateTime.now()) }

    LaunchedEffect(Unit) {
        while (true) {
            current = LocalDateTime.now()
            delay(1000L) // update every second
        }
    }

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val formattedTime = current.format(formatter)
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = formattedTime,
                fontSize = 16.sp
                )},
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
    LinePosTopBar()
}