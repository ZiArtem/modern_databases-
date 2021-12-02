package com.example.modern_databases.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Query("SELECT * FROM user_table WHERE login=(:login) and password=(:password)")
    fun getUser(login:String,password:String): List<User>

    @Query("SELECT * FROM user_table WHERE login=(:login)")
    fun checkUniqLogin(login:String): List<User>
}