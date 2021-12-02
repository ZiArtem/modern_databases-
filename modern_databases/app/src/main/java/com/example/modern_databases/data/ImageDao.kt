package com.example.modern_databases.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ImageDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addImage(image: Image)

    @Query("SELECT*FROM images_table WHERE id_image=(:id_image)")
    fun getImageById(id_image:Int): List<Image>
}