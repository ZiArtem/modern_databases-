package com.example.modern_databases.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.modern_databases.data.entities.Image

@Dao
interface ImageDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addImage(image: Image)

    @Delete
    suspend fun deleteImage(image: Image)

    @Update
    suspend fun updateImage(image: Image)

    @Query("SELECT*FROM images_table WHERE id_image=(:id_image)")
    fun getImageById(id_image: Int): List<Image>

    @Query("SELECT*FROM images_table WHERE id_ad_=(:id_ad) ORDER BY rank_in_ad")
    fun getImagesByIdAd(id_ad: Int): LiveData<List<Image>>

    @Query("SELECT*FROM images_table WHERE rank_in_ad = 1 ORDER BY id_ad_")
    fun getAllImage(): LiveData<List<Image>>

    @Query("SELECT * FROM images_table WHERE rank_in_ad = 1  AND (id_ad_ IN (:id_ad_)) ORDER BY id_ad_")
    fun getAllPreviewImage(id_ad_: List<Int>): LiveData<List<Image>>
}

