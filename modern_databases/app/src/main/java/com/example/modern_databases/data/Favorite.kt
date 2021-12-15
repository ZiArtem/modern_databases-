package com.example.modern_databases.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "favorite_table", foreignKeys = [
    ForeignKey(entity = Ad::class,
        parentColumns = ["id_ad"],
        childColumns = ["id_ad_"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(entity = User::class,
        parentColumns = ["id_user"],
        childColumns = ["id_user_"],
        onDelete = ForeignKey.CASCADE
    )])

class Favorite (
    @PrimaryKey(autoGenerate = true)
    val id_favorite:Int,
    val id_ad_: Int,
    val id_user_:Int
)