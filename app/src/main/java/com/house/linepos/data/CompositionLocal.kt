package com.house.linepos.data

import androidx.compose.runtime.compositionLocalOf

// Define CompositionLocal variable
// TODO: Rename to LocalProductCategoryRepositoryProvider
val LocalProductCategoryRepository = compositionLocalOf<ProductCategoryRepository> {
    error("No ProductCategoryRepository provided")
}

val LocalProductTagRepositoryProvider = compositionLocalOf<LocalProductTagRepository> {
    error("No ProductTagRepository provided")
}