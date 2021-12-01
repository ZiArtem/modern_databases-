package com.example.modern_databases.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AdDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAd(ad: Ad)

    @Query("SELECT*FROM ad_table ORDER BY date")
    fun readAllAd(): LiveData<List<Ad>>

    @Query("SELECT*FROM ad_table WHERE id_ad=(:id)")
    fun getAdById(id:Int): List<Ad>


//    @Query("SELECT * FROM user_table WHERE login=(:login) and password=(:password)")
//    fun getUser(login:String,password:String): List<User>
//
//    @Query("SELECT * FROM user_table WHERE login=(:login)")
//    fun checkUniqLogin(login:String): List<User>
//
}