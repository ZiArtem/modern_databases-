package com.example.modern_databases.data.dao

import androidx.room.*
import com.example.modern_databases.data.entities.UserInformation

@Dao
interface UserInformationDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUserInfo(userInformation: UserInformation)

    @Delete
    suspend fun deleteUserInfo(userInformation: UserInformation)

    @Update
    suspend fun updateUserInfo(userInformation: UserInformation)

    @Query("SELECT * FROM user_information_table WHERE id_user_=(:id)")
    fun getUserInfo(id: Int): List<UserInformation>

}