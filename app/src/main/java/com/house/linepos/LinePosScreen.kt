package com.house.linepos

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

/*
* TODO: String should be defined in string resource.
* */
interface LinePosScreen {
    val icon: ImageVector
    val route: String
    val label: String
    val screen: @Composable () -> Unit
    val description: String?
}

object LoginPage : LinePosScreen {
    override val icon = Icons.AutoMirrored.Outlined.ExitToApp
    override val route = "login"
    override val label = "Login"
    override val screen: @Composable () -> Unit = { /* TODO: LoginScreen(navController) */ }
    override val description = "login screen"
}

object Settings : LinePosScreen {
    override val icon = Icons.Outlined.Settings
    override val route = "settings"
    override val label = "Settings"
    override val screen: @Composable () -> Unit = { /* TODO: SettingsScreen() */ }
    override val description = "settings"
}

object ShoppingCart : LinePosScreen {
    override val icon = Icons.Outlined.ShoppingCart
    override val route = "shoppingCart"
    override val label = "Shopping Cart"
    override val screen: @Composable () -> Unit = { /* TODO: ShoppingCartScreen() */ }
    override val description = "shopping cart"
}

object Home : LinePosScreen {
    override val icon = Icons.Outlined.Home
    override val route = "home"
    override val label = "Home"
    override val screen: @Composable () -> Unit = { /* TODO: HomeScreen() */ HomeScreen() }
    override val description = "home screen"
}

object NewOrder : LinePosScreen {
    override val icon = Icons.Outlined.AddCircle
    override val route = "newOrder"
    override val label = "New Order"
    override val screen: @Composable () -> Unit = { /* TODO: NewOrderScreen() */ NewOrderScreen() }
    override val description = "create a new order"
}

object Info : LinePosScreen {
    override val icon = Icons.Outlined.Info
    override val route = "info"
    override val label = "Info"
    override val screen: @Composable () -> Unit = { /* TODO: NewOrderScreen() */ InfoScreen() }
    override val description = "create a new order"
}

val bottomItems = listOf(Home, NewOrder, Info)