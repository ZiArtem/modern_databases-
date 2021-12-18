package com.example.modern_databases.data.dao

import androidx.room.*
import com.example.modern_databases.data.data_class.User

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

    @Query("SELECT firstName,lastName FROM user_table WHERE id_user=(:id)")
    fun getUserInfo(id: Int): List<UserInfo>

    @Query("SELECT id_user FROM user_table WHERE login=(:login_)")
    fun getUserIdByLogin(login_: String): List<Int>

    data class UserInfo(val firstName: String?, val lastName: String?)
}