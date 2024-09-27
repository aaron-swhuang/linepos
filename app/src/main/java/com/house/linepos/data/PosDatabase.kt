package com.house.linepos.dao

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.house.linepos.data.Converters
import com.house.linepos.data.Order
import com.house.linepos.data.OrderItem
import com.house.linepos.data.Product
import com.house.linepos.data.ProductCategory
import com.house.linepos.data.ProductTag
import java.io.IOException

@Database(
    entities = [
        Product::class,
        ProductCategory::class,
        ProductTag::class,
        Order::class,
        OrderItem::class],
    version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class PosDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun productCategoryDao(): ProductCategoryDao
    abstract fun productTagDao(): ProductTagDao
    abstract fun orderDao(): OrderDao
    abstract fun orderItemDao(): OrderItemDao

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
                    // This will destroy database if current data cannot migrate. This can be used
                    // while in develop phase and remove it before release.
                    // TODO: add migration.
                    //.fallbackToDestructiveMigration()
                    // Import database
                    //.addCallback(DatabaseCallback(context))
                    .build().also { INSTANCE = it }
            }
        }
    }

    private class DatabaseCallback(private val context: Context) : RoomDatabase.Callback() {
        private fun exeSql(db: SupportSQLiteDatabase, input: String) {
            val sqlStatements = input.split(";").map { it.trim() }
            db.beginTransaction()
            try {
                for (statement in sqlStatements) {
                    // Ensure statement is not empty
                    if (statement.isNotEmpty()) {
                        db.execSQL(statement)
                    }
                }
                db.setTransactionSuccessful()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                db.endTransaction()
            }
        }

        private fun importTable(db: SupportSQLiteDatabase, fname: String) {
            try {
                val importTb = context.assets.open(fname)
                val importSql = importTb.bufferedReader().use { it.readText() }
                exeSql(db, importSql)
            } catch (e: IOException) {
                e.printStackTrace()
                Log.e("FileError", "Failed to open file: ${e.message}")
            }
            //db.execSQL(dataSql)
        }

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            // Load schema.sql and data.sql from assets
            importTable(db, "pos_database-product_category.sql")
            importTable(db, "pos_database-product_tag.sql")
            importTable(db, "pos_database-product.sql")
        }
    }
}