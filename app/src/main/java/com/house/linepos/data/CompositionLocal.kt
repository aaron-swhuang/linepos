package com.house.linepos.data

import androidx.compose.runtime.compositionLocalOf

// Define CompositionLocal variable
val LocalProductCategoryRepository = compositionLocalOf<ProductCategoryRepository> {
    error("No ProductCategoryRepository provided")
}