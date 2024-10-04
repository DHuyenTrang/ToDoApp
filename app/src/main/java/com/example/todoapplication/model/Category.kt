package com.example.todoapplication.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
    var name: String
) {
    @PrimaryKey (autoGenerate = true) var id: Int = 0

}