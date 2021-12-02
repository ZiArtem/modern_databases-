package com.example.modern_databases.data

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images_table" )
class Image (
    @PrimaryKey(autoGenerate = true)
    val id_image: Int,
    val image: Bitmap
)