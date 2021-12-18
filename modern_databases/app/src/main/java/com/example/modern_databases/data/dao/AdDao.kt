package com.example.modern_databases.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.modern_databases.data.data_class.Ad
import com.example.modern_databases.data.data_class.Favorite
import com.example.modern_databases.data.data_class.Image

@Dao
interface AdDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAd(ad: Ad)

    @Delete
    suspend fun deleteAd(ad: Ad)

    @Update
    suspend fun updateAd(ad: Ad)

    @Query("SELECT*FROM ad_table ORDER BY id_ad")
    fun readAllAd(): LiveData<List<Ad>>

    @Query("SELECT id_ad FROM ad_table ORDER BY id_ad")
    fun readAllAdId(): List<Int>

    @Query("SELECT*FROM ad_table ORDER BY id_ad")
    fun getAllAd(): List<Ad>

    @Query("SELECT*FROM ad_table WHERE id_ad=(:id)")
    fun getAdById(id: Int): List<Ad>

    @Query("SELECT*FROM ad_table WHERE id_user_=(:id_user)")
    fun getAdByIdUser(id_user: Int): List<Ad>

    @Query("SELECT*FROM ad_table WHERE title LIKE '%' || :keyword || '%' ")
    fun getByKeyword(keyword: String): LiveData<List<Ad>>

    @Query("SELECT * FROM ad_table WHERE id_ad IN (:id_ad_)")
    fun getAdByListIdAd(id_ad_:List<Int>): LiveData<List<Ad>>

    @Transaction
    @Query("SELECT * FROM ad_table")
    fun TestALlAd(): LiveData<List<FullAd>>

    @Transaction
    @Query("SELECT * FROM ad_table WHERE id_ad IN (:favList)")
    fun TestALlAdByIdAd(favList:List<Int>): LiveData<List<FullAd>>

    data class FullAd (
        @Embedded val ad: Ad,
        @Relation (
            parentColumn = "id_ad",
            entity = Image::class,
            entityColumn = "id_ad_"
                )
        val imageList: List<Image>,

        @Relation (
            parentColumn = "id_ad",
            entity = Favorite::class,
            entityColumn = "id_ad_"
        )
        val fav: List<Favorite>
            )
}