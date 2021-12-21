package com.example.modern_databases.data.database

import android.content.Context
import androidx.room.*
import com.example.modern_databases.data.converter.DataConverter
import com.example.modern_databases.data.dao.*
import com.example.modern_databases.data.entities.*

@Database(
    version = 4,
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
                    "user_database"
                ).fallbackToDestructiveMigration().build()
//                    .createFromAsset("new_1.db")

                INSTANCE = instance
                return instance
            }
        }
    }
}


