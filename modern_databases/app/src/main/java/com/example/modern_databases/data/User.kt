package com.example.modern_databases.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "user_table", indices = [Index(value = ["login"], unique = true)])
class User(
    @PrimaryKey(autoGenerate = true)
    val id_user: Int,
    val firstName: String,
    val lastName: String,
    val login: String,
    val password: String
)
