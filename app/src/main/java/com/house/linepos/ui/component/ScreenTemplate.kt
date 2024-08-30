package com.house.linepos.ui.component

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/*
* Screens in this file are used for design the navigation behaviour,
* it would be removed after the screen is designed completely.
* */

@Composable
fun HomeScreen() {
    Log.v(com.house.linepos.TAG, "Loading...Home screen")
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
    Log.v(com.house.linepos.TAG, "Loading...New order screen")
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
    Log.v(com.house.linepos.TAG, "Loading...Info screen")
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text("Welcome to the Info Screen!")
    }
}

@Composable
fun AboutScreen() {
    Log.v(com.house.linepos.TAG, "Loading...About screen")
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text("Welcome to the About Screen!")
    }
}