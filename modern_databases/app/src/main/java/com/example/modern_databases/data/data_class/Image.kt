package com.example.modern_databases.data.data_class

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.modern_databases.data.data_class.Ad

@Entity(
    tableName = "images_table", foreignKeys = [
        ForeignKey(
            entity = Ad::class,
            parentColumns = ["id_ad"],
            childColumns = ["id_ad_"],
            onDelete = ForeignKey.CASCADE
        )]
)

class Image(
    @PrimaryKey(autoGenerate = true)
    val id_image: Int,
    val image: Bitmap,
    val id_ad_: Int,
    val rank_in_ad: Int
)