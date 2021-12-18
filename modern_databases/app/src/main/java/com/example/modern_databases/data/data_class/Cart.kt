package com.example.modern_databases.data.data_class

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "cart_table", foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id_user"],
            childColumns = ["id_user_"],
            onDelete = ForeignKey.CASCADE
        ), ForeignKey(
            entity = Ad::class,
            parentColumns = ["id_ad"],
            childColumns = ["id_ad_"],
            onDelete = ForeignKey.CASCADE
        )]
)

class Cart (
    @PrimaryKey(autoGenerate = true)
    val id_cart: Int,
    val num: Int,
    val date: Date,
    val id_ad_: Int,
    val id_user_: Int
)