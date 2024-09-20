package com.example.todoapplication.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.Date

@Entity(
    tableName = "tasks",
    foreignKeys = arrayOf(
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.CASCADE
        ))
    )
data class Task(
    @ColumnInfo var title: String,
    @ColumnInfo var description: String,
    @ColumnInfo var due_date: Date,
    @ColumnInfo var is_allDay: Boolean,
    @ColumnInfo var status: String,
    @ColumnInfo var category_id: Int,
    @ColumnInfo var user_id: Int,
) {
    @PrimaryKey (autoGenerate = true)
    var id: Int = 0
}