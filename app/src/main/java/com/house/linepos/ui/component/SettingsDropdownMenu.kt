package com.house.linepos.ui.component

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.house.linepos.About
import com.house.linepos.LoginPage

@Composable
fun SettingsDropdownMenu(
    rootNavHostController: NavHostController,
    mainNavHostController: NavHostController,
    expanded:Boolean,
    onDismissRequest: () -> Unit,
) {
    DropdownMenu(expanded = expanded, onDismissRequest = onDismissRequest) {
        DropdownMenuItem(
            text = { Text("Logout") },
            trailingIcon = { Icon(LoginPage.icon, LoginPage.description) },
            onClick = {
                /* TODO: logout logic */
                rootNavHostController.navigate(LoginPage.route) {
                    // Clear all content from stack to avoid that the screen can go back to main
                    // screen again from login screen.
                    popUpTo(0) { inclusive = true }
                    launchSingleTop = true
                }
                onDismissRequest()
            }
        )
        DropdownMenuItem(
            text = { Text(About.label) },
            trailingIcon = { Icon(About.icon, About.description) },
            onClick = {
                mainNavHostController.navigate((About.route))
                onDismissRequest()
            }
        )
    }
}