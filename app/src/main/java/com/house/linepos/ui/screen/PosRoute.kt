package com.house.linepos.ui.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

/*
* TODO: String should be defined in string resource.
* */
interface BaseLinePosRoute {
    val route: String
    val label: String
    val description: String?
}

interface PosRoute : BaseLinePosRoute {
    val icon: ImageVector
}

object Main : PosRoute {
    override val icon = Icons.AutoMirrored.Outlined.ExitToApp
    override val route = "main"
    override val label = "Main"
    override val description = "main screen"
}

object LoginPage : PosRoute {
    override val icon = Icons.AutoMirrored.Outlined.ExitToApp
    override val route = "login"
    override val label = "Login"
    override val description = "login screen"
}

object ShoppingCart : PosRoute {
    override val icon = Icons.Outlined.ShoppingCart
    override val route = "shopping_cart"
    override val label = "Shopping Cart"
    override val description = "shopping cart"
}

// Settings menu
object About : PosRoute {
    override val icon = Icons.Outlined.Info
    override val route = "about"
    override val label = "About"
    override val description = "About"
}

object Logout : PosRoute {
    override val icon = Icons.AutoMirrored.Outlined.ExitToApp
    override val route = "logout"
    override val label = "Logout"
    override val description = "Direct to login screen"
}

object Notification : PosRoute {
    override val icon = Icons.Outlined.Notifications
    override val route = "notification"
    override val label = "Notification"
    override val description = "Direct to notification screen"
}

// Items of bottom bar
object Home : PosRoute {
    override val icon = Icons.Outlined.Home
    override val route = "home"
    override val label = "Home"
    override val description = "home screen"
}

object NewOrder : PosRoute {
    override val icon = Icons.Outlined.Add
    override val route = "new_order"
    override val label = "New Order"
    override val description = "create a new order"
}

object Info : PosRoute {
    override val icon = Icons.AutoMirrored.Outlined.List
    override val route = "info"
    override val label = "Info"
    override val description = "Info"
}

// items of drawer
object Product : BaseLinePosRoute {
    override val route = "product"
    override val label = "Product"
    override val description = "Create a new product"
}

object ProductCategory : BaseLinePosRoute {
    override val route = "product_category"
    override val label = "Product Category"
    override val description = "Product category"
}

object ProductTag : BaseLinePosRoute {
    override val route = "product_tag"
    override val label = "Product Tag"
    override val description = "Product tag"
}

// TODO: Currently, this is used by dropdown menu and it does not navigate to
//       another screen so this should be considered redefine for dropdown menu
//       or set the icon and description inline.
object Settings : PosRoute {
    override val icon = Icons.Outlined.Settings
    override val route = "settings"
    override val label = "Settings"
    override val description = "settings"
}

val bottomItems = listOf(Home, NewOrder, Info)
val settingsMenuItems = listOf(Notification, Logout, About)
val drawerItems = listOf(Product, ProductTag, ProductCategory)