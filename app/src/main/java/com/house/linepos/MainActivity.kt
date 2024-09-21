package com.house.linepos

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.testing.TestNavHostController
import com.house.linepos.dao.PosDatabase
import com.house.linepos.data.LocalProductCategoryRepository
import com.house.linepos.data.LocalProductRepository
import com.house.linepos.data.LocalProductRepositoryProvider
import com.house.linepos.data.LocalProductTagRepository
import com.house.linepos.data.ProductCategoryRepository
import com.house.linepos.data.LocalProductTagRepositoryProvider
import com.house.linepos.ui.component.LinePosBottomBar
import com.house.linepos.ui.component.LinePosTopBar
import com.house.linepos.ui.component.NavigationDrawer
import com.house.linepos.ui.screen.About
import com.house.linepos.ui.screen.AboutScreen
import com.house.linepos.ui.screen.CreateProductCategory
import com.house.linepos.ui.screen.Home
import com.house.linepos.ui.screen.HomeScreen
import com.house.linepos.ui.screen.Info
import com.house.linepos.ui.screen.InfoScreen
import com.house.linepos.ui.screen.LoginPage
import com.house.linepos.ui.screen.LoginScreen
import com.house.linepos.ui.screen.Logout
import com.house.linepos.ui.screen.Main
import com.house.linepos.ui.screen.NewOrder
import com.house.linepos.ui.screen.NewOrderScreen
import com.house.linepos.ui.screen.Product
import com.house.linepos.ui.screen.ProductCategory
import com.house.linepos.ui.screen.ProductCategoryScreen
import com.house.linepos.ui.screen.ProductScreen
import com.house.linepos.ui.screen.ProductTag
import com.house.linepos.ui.screen.ProductTagScreen
import com.house.linepos.ui.theme.LinePosTheme
import kotlinx.coroutines.launch

val TAG = "LinePOS"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val database = PosDatabase.getDatabase(LocalContext.current)
            val productRepository = LocalProductRepository(database.productDao())
            val productCategoryRepository = ProductCategoryRepository(database.productCategoryDao())
            val productTagRepository = LocalProductTagRepository(database.productTagDao())
            CompositionLocalProvider(
                LocalProductCategoryRepository provides productCategoryRepository,
                LocalProductTagRepositoryProvider provides productTagRepository,
                LocalProductRepositoryProvider provides productRepository
            ) {
                PosApp()
            }

        }
    }
}

@Composable
fun PosApp() {
    val navController = rememberNavController()
    LinePosTheme {
        NavHost(navController = navController, startDestination = LoginPage.route) {
            composable(LoginPage.route) { LoginScreen(navController) }
            composable(Main.route) { MainScreen(navController) }
            // Add navigation graph if Logout from settings.
            composable(Logout.route) { LoginScreen(navController) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PosAppPreview() {
    PosApp()
}

// TODO: Replace main screen with home screen
@Composable
fun MainScreen(rootNavController: NavHostController) {
    val mainNavController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    //val currentRoute = rootNavController.currentBackStackEntry?.destination?.route
    //Log.v(TAG, "current route: ${currentRoute}")

    NavigationDrawer(
        drawerState = drawerState,
        onItemSelected = { route ->
            scope.launch {
                drawerState.close()
            }
            when(route) {
                Product.route ->
                    mainNavController.navigate(route) {
                        popUpTo(mainNavController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                ProductTag.route ->
                    mainNavController.navigate(route) {
                        popUpTo(mainNavController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                ProductCategory.route -> mainNavController.navigate(route) {
                    popUpTo(mainNavController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    restoreState = true
                    launchSingleTop = true
                }
            }
        },
        content = {
            Scaffold(
                topBar = {
                    //if (currentRoute != LoginPage.route) {
                    LinePosTopBar(
                        rootNavHostController = rootNavController,
                        mainNavHostController =  mainNavController,
                        drawerState = drawerState,
                        scope = scope
                    )
                    //}
                },
                bottomBar = {
                    //if (currentRoute != LoginPage.route) {
                    LinePosBottomBar(
                        onItemClick = { route ->
                            Log.v(com.house.linepos.ui.component.TAG, "navigate to ${route}")
                            when (route) {
                                Home.route -> {
                                    mainNavController.navigate(route) {
                                        popUpTo(mainNavController.graph.findStartDestination().id) {
                                            Log.v(
                                                com.house.linepos.ui.component.TAG,
                                                "${route} save state"
                                            )
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                                NewOrder.route -> {
                                    mainNavController.navigate(route) {
                                        popUpTo(mainNavController.graph.findStartDestination().id) {
                                            Log.v(
                                                com.house.linepos.ui.component.TAG,
                                                "${route} save state"
                                            )
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                                Info.route -> {
                                    mainNavController.navigate(route) {
                                        popUpTo(mainNavController.graph.findStartDestination().id) {
                                            Log.v(
                                                com.house.linepos.ui.component.TAG,
                                                "${route} save state"
                                            )
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }

                            }
                        }
                )
                    //}
                }
            ){ innerPadding ->
                // Use LaunchedEffect to register listener
                LaunchedEffect(mainNavController) {
                    mainNavController.addOnDestinationChangedListener { _, destination, _ ->
                        Log.d(TAG, "Navigated to: ${destination.route}")
                    }
                }

                NavigationHost(mainNavController, innerPadding)
            }
        }
    )
}

// TODO: Consider moving this method to another file.
@Composable
fun NavigationHost(
    mainNavController: NavHostController,
    padding: PaddingValues
) {
    NavHost(
        navController = mainNavController,
        startDestination = Home.route,
        modifier = Modifier.padding(padding)
    ) {
        composable(Home.route) { HomeScreen() }
        composable(NewOrder.route) { NewOrderScreen() }
        composable(Info.route) { InfoScreen() }
        // SettingsDropdownMenu
        composable(About.route) { AboutScreen() }
        // drawer
        composable(Product.route) { ProductScreen() }
        composable(ProductCategory.route) { ProductCategoryScreen() }
        composable(ProductTag.route) { ProductTagScreen() }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    val navController = TestNavHostController(LocalContext.current)
    MainScreen(navController)
}