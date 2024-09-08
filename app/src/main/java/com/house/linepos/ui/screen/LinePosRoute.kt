package com.house.linepos.ui.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
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

interface LinePosRoute : BaseLinePosRoute {
    val icon: ImageVector
}

object Main : LinePosRoute {
    override val icon = Icons.AutoMirrored.Outlined.ExitToApp
    override val route = "main"
    override val label = "Main"
    override val description = "main screen"
}

object LoginPage : LinePosRoute {
    override val icon = Icons.AutoMirrored.Outlined.ExitToApp
    override val route = "login"
    override val label = "Login"
    override val description = "login screen"
}

object Logout : LinePosRoute {
    override val icon = Icons.AutoMirrored.Outlined.ExitToApp
    override val route = "logout"
    override val label = "Logout"
    override val description = "Direct ot login screen"
}

object ShoppingCart : LinePosRoute {
    override val icon = Icons.Outlined.ShoppingCart
    override val route = "shopping_cart"
    override val label = "Shopping Cart"
    override val description = "shopping cart"
}

object Home : LinePosRoute {
    override val icon = Icons.Outlined.Home
    override val route = "home"
    override val label = "Home"
    override val description = "home screen"
}

object NewOrder : LinePosRoute {
    override val icon = Icons.Outlined.AddCircle
    override val route = "new_order"
    override val label = "New Order"
    override val description = "create a new order"
}

object Info : LinePosRoute {
    override val icon = Icons.AutoMirrored.Outlined.List
    override val route = "info"
    override val label = "Info"
    override val description = "Info"
}

object About : LinePosRoute {
    override val icon = Icons.Outlined.Info
    override val route = "about"
    override val label = "About"
    override val description = "About"
}

object Product : BaseLinePosRoute {
    override val route = "product"
    override val label = "Create Product"
    override val description = "Create a new product"
}

object ProductCategory : BaseLinePosRoute {
    override val route = "product_category"
    override val label = "Product Category"
    override val description = "Product category"
}

object CreateProductCategory : BaseLinePosRoute {
    override val route = "create_product_category"
    override val label = "Create Product Category"
    override val description = "Create a new product category"
}

object ProductTag : BaseLinePosRoute {
    override val route = "product_tag"
    override val label = "Product Tag"
    override val description = "Product tag"
}

// TODO: Currently, this is used by dropdown menu and it does not navigate to
//       another screen so this should be considered redefine for dropdown menu
//       or set the icon and description inline.
object Settings : LinePosRoute {
    override val icon = Icons.Outlined.Settings
    override val route = "settings"
    override val label = "Settings"
    override val description = "settings"
}

val bottomItems = listOf(Home, NewOrder, Info)
val settingsMenuItems = listOf(Logout, About)
val drawerItems = listOf(Product, ProductTag, ProductCategory, CreateProductCategory)