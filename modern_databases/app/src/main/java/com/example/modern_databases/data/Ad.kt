package com.example.modern_databases.data

import android.graphics.Bitmap
import androidx.room.*

@Entity(tableName = "ad_table", foreignKeys = [
    ForeignKey(entity = User::class,
        parentColumns = ["id_user"],
        childColumns = ["id_user_"],
        onDelete = ForeignKey.CASCADE
    )])
class Ad (
    @PrimaryKey(autoGenerate = true)
    val id_ad: Int,
    val title: String,
    val description: String,
    val price: Int,
    val date: Int,
    val id_user_:Int
)
//    @Ignore val picture: Bitmap
