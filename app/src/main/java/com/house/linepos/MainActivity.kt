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
import com.house.linepos.ui.component.LinePosBottomBar
import com.house.linepos.ui.component.LinePosTopBar
import com.house.linepos.ui.component.NavigationDrawer
import com.house.linepos.ui.screen.About
import com.house.linepos.ui.screen.CreateProductCategory
import com.house.linepos.ui.screen.Home
import com.house.linepos.ui.screen.Info
import com.house.linepos.ui.screen.LoginPage
import com.house.linepos.ui.screen.LoginScreen
import com.house.linepos.ui.screen.Main
import com.house.linepos.ui.screen.NewOrder
import com.house.linepos.ui.screen.ProductCategory
import com.house.linepos.ui.theme.LinePosTheme
import kotlinx.coroutines.launch

val TAG = "LinePOS"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PosApp()
        }
    }
}

@Composable
fun PosApp() {
    val navController = rememberNavController()
    LinePosTheme {
        NavHost(navController = navController, startDestination = LoginPage.route) {
            composable(LoginPage.route) { LoginScreen(navController) }
            composable(Main.route) { Main.screen(navController) }
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
        onItemSelected = { selectedItem ->
            scope.launch {
                drawerState.close()
            }
            when(selectedItem) {
                CreateProductCategory.label ->
                    mainNavController.navigate(CreateProductCategory.route) {
                        popUpTo(mainNavController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                About.label -> mainNavController.navigate(About.route) {
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
                    LinePosBottomBar(mainNavController)
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
        composable(Home.route) { Home.screen() }
        composable(NewOrder.route) { NewOrder.screen() }
        composable(Info.route) { Info.screen() }
        // SettingsDropdownMenu
        composable(LoginPage.route) { LoginPage.screen(mainNavController) }
        composable(About.route) { About.screen() }
        // drawer
        composable(CreateProductCategory.route) { CreateProductCategory.screen() }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    val navController = TestNavHostController(LocalContext.current)
    MainScreen(navController)
}