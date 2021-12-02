package com.example.modern_databases.data

import android.content.Context
import androidx.room.*

@Database(
    version = 13,
    entities = [User::class,Ad::class,Order::class,Image::class]
        )

@TypeConverters(Converters::class)
abstract  class UserDatabase:RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun adDao(): AdDao
    abstract fun orderDao(): OrderDao
    abstract fun imageDao(): ImageDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase (context: Context): UserDatabase {
            val tempInstance = INSTANCE
            if (tempInstance!= null) {
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                  UserDatabase::class.java,
                  "user_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}


