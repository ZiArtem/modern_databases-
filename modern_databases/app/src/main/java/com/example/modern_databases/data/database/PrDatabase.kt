package com.example.modern_databases.data.database

import android.content.ContentValues
import android.content.Context
import androidx.lifecycle.lifecycleScope
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.modern_databases.data.dao.*
import com.example.modern_databases.data.data_class.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.*
import androidx.room.RoomDatabase




@Database(
    version = 7,
    entities = [User::class, Ad::class, Order::class, Image::class, Favorite::class, UserInformation::class, Cart::class]
)

@TypeConverters(DataConverter::class)
abstract class PrDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun adDao(): AdDao
    abstract fun orderDao(): OrderDao
    abstract fun imageDao(): ImageDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun userInformationDao(): UserInformationDao
    abstract fun cartDao(): CartDao

    companion object {
        @Volatile
        private var INSTANCE: PrDatabase? = null

        fun getDatabase(context: Context): PrDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PrDatabase::class.java,
                    "pr_database"
                ).fallbackToDestructiveMigration().createFromAsset("new_1.db").build()

                INSTANCE = instance
                return instance
            }
        }
    }
}


