package com.example.modern_databases.data.entities

import androidx.room.*
import java.util.*

@Entity(tableName = "ad_table", foreignKeys = [
    ForeignKey(entity = User::class,
        parentColumns = ["id_user"],
        childColumns = ["id_user_"],
        onDelete = ForeignKey.CASCADE
    )])

data class Ad (
    @PrimaryKey(autoGenerate = true)
    val id_ad: Int,
    val title: String,
    val description: String,
    val about_this_item: String,
    val category: Int,
    val price: Double,
    val date: Date,
    val id_user_:Int
)
