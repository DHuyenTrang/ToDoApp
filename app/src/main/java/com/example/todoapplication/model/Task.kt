package com.example.todoapplication.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time

@Entity(tableName = "tasks")
data class Task(
    @ColumnInfo var title: String,
    @ColumnInfo var description: String,
    @ColumnInfo var due_time: Time
) {
    @PrimaryKey (autoGenerate = true)
    var id: Int = 0
}