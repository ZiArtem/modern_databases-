package com.example.modern_databases.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "parchasehistory_table", foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id_user"],
            childColumns = ["id_user_"],
            onDelete = ForeignKey.CASCADE
        )]
)

data class Order(
    @PrimaryKey(autoGenerate = true)
    val id_order: Int,
    val price: Double,
    val date: Date,
    val address: String,
    val id_user_: Int
)