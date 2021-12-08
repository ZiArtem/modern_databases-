package com.example.modern_databases.data

import androidx.room.*
import java.util.*

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
    val category: Int,
    val price: Int,
    val date: Date,
    val id_user_:Int
)
