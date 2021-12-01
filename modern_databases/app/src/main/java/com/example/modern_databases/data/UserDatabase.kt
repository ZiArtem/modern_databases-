package com.example.modern_databases.data

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    version = 6,
    entities = [User::class,Ad::class]
        )

abstract  class UserDatabase:RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun adDao(): AdDao
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
