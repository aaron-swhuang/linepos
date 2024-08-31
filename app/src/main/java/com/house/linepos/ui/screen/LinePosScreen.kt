package com.house.linepos.ui.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import com.house.linepos.MainScreen

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

interface LinePosScreenWithNavController : LinePosScreen {
    @Composable
    fun screen(navController: NavHostController)
}

/*
object Main {
    val route: String = "main"
    val screen: @Composable () -> Unit = { MainScreen() }
}
*/

object Main : LinePosScreenWithNavController {
    override val icon = Icons.AutoMirrored.Outlined.ExitToApp
    override val route = "main"
    override val label = "Main"
    override val screen: @Composable () -> Unit = { /* TODO: LoginScreen(navController) */ }
    override val description = "main screen"
    @Composable
    override fun screen(navController: NavHostController) {
        MainScreen(rootNavController = navController)
    }
}

object LoginPage : LinePosScreenWithNavController {
    override val icon = Icons.AutoMirrored.Outlined.ExitToApp
    override val route = "login"
    override val label = "Login"
    override val screen: @Composable () -> Unit = { /* TODO: LoginScreen(navController) */ }
    override val description = "login screen"
    @Composable
    override fun screen(navController: NavHostController) {
        LoginScreen(navController = navController)
    }
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
    override val screen: @Composable () -> Unit = { HomeScreen() }
    override val description = "home screen"
}

object NewOrder : LinePosScreen {
    override val icon = Icons.Outlined.AddCircle
    override val route = "newOrder"
    override val label = "New Order"
    override val screen: @Composable () -> Unit = { NewOrderScreen() }
    override val description = "create a new order"
}

object Info : LinePosScreen {
    override val icon = Icons.AutoMirrored.Outlined.List
    override val route = "info"
    override val label = "Info"
    override val screen: @Composable () -> Unit = { InfoScreen() }
    override val description = "Info"
}

object About : LinePosScreen {
    override val icon = Icons.Outlined.Info
    override val route = "about"
    override val label = "About"
    override val screen: @Composable () -> Unit = { AboutScreen() }
    override val description = "About"
}

// TODO: Currently, this is used by dropdown menu and it does not navigate to
//       another screen so this should be considered redefine for dropdown menu
//       or set the icon and description inline.
object Settings : LinePosScreen {
    override val icon = Icons.Outlined.Settings
    override val route = "settings"
    override val label = "Settings"
    override val screen: @Composable () -> Unit = { /* TODO: SettingsScreen() */ }
    override val description = "settings"
}
val bottomItems = listOf(Home, NewOrder, Info)