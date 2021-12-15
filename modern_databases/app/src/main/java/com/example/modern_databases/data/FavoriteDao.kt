package com.example.modern_databases.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavorite(favorite: Favorite)

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    @Update
    suspend fun updateFavorite(favorite: Favorite)

//    @Query("SELECT*FROM ad_table ORDER BY date")
//    fun readAllAd(): LiveData<List<Ad>>
//
    @Query("SELECT* FROM favorite_table WHERE id_user_=(:id_user) ORDER BY id_favorite")
    fun getAllFavoriteByUse(id_user: Int): List<Favorite>


//
//    @Query("SELECT*FROM ad_table WHERE id_ad=(:id)")
//    fun getAdById(id: Int): List<Ad>
//
//    @Query("SELECT*FROM ad_table WHERE id_user_=(:id_user)")
//    fun getAdByIdUser(id_user: Int): List<Ad>
//
//    @Query("SELECT*FROM ad_table WHERE title LIKE '%' || :keyword || '%' ")
//    fun getByKeyword(keyword: String): LiveData<List<Ad>>

}