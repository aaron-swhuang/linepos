package com.house.linepos.data

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavController

// Define CompositionLocal variable
val LocalProductCategoryRepositoryProvider = compositionLocalOf<LocalProductCategoryRepository> {
    error("No ProductCategoryRepository provided")
}

val LocalProductTagRepositoryProvider = compositionLocalOf<LocalProductTagRepository> {
    error("No ProductTagRepository provided")
}

val LocalProductRepositoryProvider = compositionLocalOf<LocalProductRepository> {
    error("No ProductRepository provided")
}

// Navigation controller
val LocalNavController = staticCompositionLocalOf<NavController> {
    error("No NavController provided")
}