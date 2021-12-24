package com.example.modern_databases.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "order_item_table", foreignKeys = [
        ForeignKey(
            entity = Ad::class,
            parentColumns = ["id_ad"],
            childColumns = ["id_ad_"],
            onDelete = ForeignKey.SET_NULL
        ), ForeignKey(
            entity = Order::class,
            parentColumns = ["id_order"],
            childColumns = ["id_order_"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)

data class OrderItem(
    @PrimaryKey(autoGenerate = true)
    val id_item: Int,
    val id_order_: Int,
    val price: Double,
    val id_ad_: Int,
    val num: Int
)