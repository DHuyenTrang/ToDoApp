package com.example.todoapplication.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @ColumnInfo var username: String,
    @ColumnInfo var password: String,
    @ColumnInfo var email: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}