package com.example.modern_databases.data

import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Query("SELECT * FROM user_table WHERE login=(:login) and password=(:password)")
    fun getUser(login: String, password: String): List<User>

    @Query("SELECT * FROM user_table WHERE login=(:login)")
    fun checkUniqueLogin(login: String): List<User>
}