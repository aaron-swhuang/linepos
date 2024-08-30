package com.house.linepos.ui.component

import androidx.compose.material3.DrawerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.testing.TestNavHostController
import com.house.linepos.Settings
import com.house.linepos.ShoppingCart
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LinePosTopBar(
    rootNavHostController: NavHostController,
    mainNavHostController: NavHostController,
    drawerState: DrawerState,
    scope: CoroutineScope
    ) {
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
            IconButton(
                onClick = { /* TODO: Menu */
                    scope.launch {
                        if (drawerState.isClosed) {
                            drawerState.open()
                        } else {
                            drawerState.close()
                        }
                    }
                }) {
                // TODO: Make screen can go back to previous screen.
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
                //Icon(
                //    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                //    contentDescription = "Localized description"
                //)
            }
        },
        actions = {
            // TODO: show this icon only when there's a new order is active.
            IconButton(onClick = { /* TODO: ShoppingCartScreen */ }) {
                Icon(imageVector = ShoppingCart.icon, contentDescription = ShoppingCart.description)
            }
            var expanded by rememberSaveable { mutableStateOf(false) }
            IconButton(onClick = { /*TODO: SettingsScreen */ expanded = true}) {
                Icon(imageVector = Settings.icon, contentDescription = Settings.description)
            }
            SettingsDropdownMenu(
                rootNavHostController = rootNavHostController,
                mainNavHostController = mainNavHostController,
                expanded = expanded,
                onDismissRequest = { expanded = false }
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun LinePosTopBarPreview() {
    val navController = TestNavHostController(LocalContext.current)
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    LinePosTopBar(navController, navController, drawerState, scope)
}