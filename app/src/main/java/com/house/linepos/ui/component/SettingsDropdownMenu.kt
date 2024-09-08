package com.house.linepos.ui.component

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.house.linepos.ui.screen.settingsMenuItems

@Composable
fun SettingsDropdownMenu(
    expanded:Boolean,
    onMenuItemClick: (String) -> Unit,
    onDismissRequest: () -> Unit,
) {
    DropdownMenu(expanded = expanded, onDismissRequest = onDismissRequest) {
        settingsMenuItems.forEach { item ->
            DropdownMenuItem(
                text = { Text(item.label) },
                trailingIcon = { Icon(item.icon, item.description) },
                onClick = {
                    onMenuItemClick(item.route)
                    onDismissRequest()
                }
            )
        }
    }
}