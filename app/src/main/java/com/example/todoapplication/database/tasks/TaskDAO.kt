package com.example.todoapplication.database.tasks

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
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
    fun getAllTaskByUser(userId: Int): LiveData<List<Task>>

    @Query("SELECT * FROM tasks WHERE category_id =:categoryId ORDER BY due_date ASC")
    fun getAllTaskByCategory(categoryId: Int): LiveData<List<Task>>

    @Query("SELECT * FROM tasks WHERE category_id =:categoryId ORDER BY due_date DESC")
    fun getAllTaskByCategoryDescending(categoryId: Int): LiveData<List<Task>>

    @Query("SELECT * FROM tasks WHERE status = :status AND user_id = :userId")
    fun getAllTaskByStatus(status: String, userId: Int): LiveData<List<Task>>

    @Query("SELECT * FROM tasks WHERE id = :idTask")
    fun getTaskById(idTask: Int): LiveData<Task>

    @Query("SELECT category_id FROM tasks WHERE id = :idTask")
    fun getTaskCategory(idTask: Int): LiveData<Int>
}