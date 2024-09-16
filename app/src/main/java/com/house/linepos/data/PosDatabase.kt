package com.house.linepos.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.house.linepos.data.Converters
import com.house.linepos.data.Product
import com.house.linepos.data.ProductCategory
import com.house.linepos.data.ProductTag

@Database(entities = [Product::class, ProductCategory::class, ProductTag::class],
    version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class PosDatabase : RoomDatabase() {
    //abstract fun productDao(): ProductDao
    abstract fun productCategoryDao(): ProductCategoryDao
    //abstract fun productTagDao(): ProductTagDao


    companion object {
        @Volatile
        private var INSTANCE: PosDatabase? = null

        fun getDatabase(context: Context): PosDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    PosDatabase::class.java,
                    "pos_database"
                )
                    .fallbackToDestructiveMigration()
                    .build().also { INSTANCE = it }
            }
        }
    }
}