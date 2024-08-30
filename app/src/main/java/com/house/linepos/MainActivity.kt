package com.house.linepos

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.testing.TestNavHostController
import com.house.linepos.ui.component.LinePosBottomBar
import com.house.linepos.ui.component.LinePosTopBar
import com.house.linepos.ui.theme.LinePosTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

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
    //val navController = TestNavHostController(LocalContext.current)
    PosApp()
}

// TODO: Replace main screen with home screen
@Composable
fun MainScreen(rootNavController: NavHostController) {
    val mainNavController = rememberNavController()
    //val currentRoute = rootNavController.currentBackStackEntry?.destination?.route
    //Log.v(TAG, "current route: ${currentRoute}")
    Scaffold(
        topBar = {
            //if (currentRoute != LoginPage.route) {
                LinePosTopBar(
                    rootNavHostController = rootNavController,
                    mainNavHostController =  mainNavController
                )
            //}
        },
        bottomBar = {
            //if (currentRoute != LoginPage.route) {
                LinePosBottomBar(mainNavController)
            //}
        }
    ){
        innerPadding ->
        NavHost(
            navController = mainNavController,
            startDestination = Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Home.route) { Home.screen() }
            composable(NewOrder.route) { NewOrder.screen() }
            composable(Info.route) { Info.screen() }
            // SettingsDropdownMenu
            composable(LoginPage.route) { LoginPage.screen(mainNavController) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    val navController = TestNavHostController(LocalContext.current)
    MainScreen(navController)
}

@Composable
fun HomeScreen() {
    Log.v(TAG, "Loading...Home screen")
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text("Welcome to the Home Screen!")
    }
}

@Composable
fun NewOrderScreen() {
    Log.v(TAG, "Loading...New order screen")
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text("Welcome to the New Order Screen!")
    }
}

@Composable
fun InfoScreen() {
    Log.v(TAG, "Loading...Info screen")
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text("Welcome to the Info Screen!")
    }
}