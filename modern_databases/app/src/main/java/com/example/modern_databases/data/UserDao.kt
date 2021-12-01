package com.example.modern_databases.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

//    @Query("SELECT*FROM user_table ORDER BY id ASC")
//    fun readAllData(): LiveData<List<User>>

    @Query("SELECT * FROM user_table WHERE login_user=(:login) and password=(:password)")
    fun getUser(login:String,password:String): List<User>

    @Query("SELECT * FROM user_table WHERE login_user=(:login)")
    fun getUserSameLogin(login:String): List<User>


}