package com.example.todoapplication.database.tasks

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todoapplication.model.Category
import com.example.todoapplication.model.Task

@Dao
interface TaskDAO {
    @Insert
    suspend fun insertTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Query("SELECT * FROM tasks WHERE user_id = :userId")
    fun getAllTaskByUser(userId: Int): LiveData<List<Category>>
}