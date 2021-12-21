package com.example.modern_databases.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.modern_databases.data.entities.Ad
import com.example.modern_databases.data.entities.Favorite
import com.example.modern_databases.data.entities.Image

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

    @Query("SELECT* FROM favorite_table WHERE id_user_=(:id_user) ORDER BY id_ad_")
    fun getAllFavoriteByUser(id_user: Int): LiveData<List<FavoriteAndAdAndImage>>

    //
    @Query("SELECT id_ad_ FROM favorite_table WHERE id_user_=(:id_user) ORDER BY id_favorite")
    fun getAllFavoriteAd(id_user: Int): List<Int>

    @Query("SELECT * FROM favorite_table WHERE id_ad_=(:id_ad) ")
    fun getFavoriteByIdAd(id_ad: Int): List<Favorite>

//
//    @Query("SELECT*FROM ad_table WHERE id_ad=(:id)")
//    fun getAdById(id: Int): List<Ad>
//
//    @Query("SELECT*FROM ad_table WHERE id_user_=(:id_user)")
//    fun getAdByIdUser(id_user: Int): List<Ad>
//
//    @Query("SELECT*FROM ad_table WHERE title LIKE '%' || :keyword || '%' ")
//    fun getByKeyword(keyword: String): LiveData<List<Ad>>


    data class FavoriteAndAdAndImage(
        @Embedded val favorite: Favorite,
        @Relation(
            parentColumn = "id_ad_",
            entity = Ad::class,
            entityColumn = "id_ad"
        )
        val adList: List<AdAndImage>
    )


    data class AdAndImage(
        @Embedded val ad: Ad,
        @Relation(
            parentColumn = "id_ad",
            entity = Image::class,
            entityColumn = "id_ad_"
        )
        val imageList: List<Image>
    )


}