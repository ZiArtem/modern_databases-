package com.example.modern_databases.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ImageDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addImage(image: Image)

    @Delete
    suspend fun deleteImage (image:Image)

    @Update
    suspend  fun updateImage (image:Image)

    @Query("SELECT*FROM images_table WHERE id_image=(:id_image)")
    fun getImageById(id_image:Int): List<Image>

    @Query("SELECT*FROM images_table WHERE id_ad_=(:id_ad) ORDER BY rank_in_ad")
    fun getImagesByIdAd(id_ad:Int): List<Image>

    @Query("SELECT*FROM images_table ORDER BY id_ad_")
    fun getAllImage(): LiveData<List<Image>>
}