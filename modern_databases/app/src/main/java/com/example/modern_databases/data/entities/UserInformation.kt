package com.example.modern_databases.data.entities

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "user_information_table",
    indices = [Index(value = ["id_user_"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id_user"],
            childColumns = ["id_user_"],
            onDelete = ForeignKey.CASCADE
        )]
)

data class UserInformation(
    @PrimaryKey(autoGenerate = true)
    val id_info: Int,
    val phone_number: String,
    val email: String,
    val date_registry: Date,
    val location: String,
    val image_user: Bitmap,
    val id_user_: Int
)