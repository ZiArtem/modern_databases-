package com.example.modern_databases.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.modern_databases.data.data_class.Favorite

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
    fun getAllFavoriteByUser(id_user: Int): LiveData<List<Favorite>>
//
    @Query("SELECT id_ad_ FROM favorite_table WHERE id_user_=(:id_user) ORDER BY id_favorite")
    fun getAllFavoriteAd(id_user: Int): List<Int>



    @Query("SELECT * FROM favorite_table WHERE id_ad_=(:id_ad) ")
    fun getFavoriteById(id_ad:Int): List<Favorite>

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