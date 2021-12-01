package com.example.modern_databases.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "parchasehistory_table", foreignKeys = [
    ForeignKey(entity = User::class,
        parentColumns = ["id_user"],
        childColumns = ["id_user_"],
        onDelete = ForeignKey.CASCADE
    ),ForeignKey(entity = Ad::class,
        parentColumns = ["id_ad"],
        childColumns = ["id_ad_"],
        onDelete = ForeignKey.CASCADE
    )])
class Order (
    @PrimaryKey(autoGenerate = true)
    val id_order: Int,
    val id_ad_:Int,
    val title: String,
    val price: Int,
    val num:Int,
    val date: Int,
    val id_user_:Int
)