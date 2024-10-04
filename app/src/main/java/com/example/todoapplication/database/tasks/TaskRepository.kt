package com.example.todoapplication.database.tasks

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import com.example.todoapplication.database.AppDatabase
import com.example.todoapplication.model.Category
import com.example.todoapplication.model.Task
import java.util.Date

class TaskRepository(context: Context) {
    private val taskDAO: TaskDAO
    init {
        val appDatabase = AppDatabase.getInstance(context)
        taskDAO = appDatabase.getTaskDao()
    }

    suspend fun insertTask(task: Task) = taskDAO.insertTask(task)

    suspend fun deleteTask(task: Task) = taskDAO.deleteTask(task)

    suspend fun updateTask(task: Task) = taskDAO.updateTask(task)

    fun getAllTaskByUser(userId: Int): LiveData<List<Task>> = taskDAO.getAllTaskByUser(userId)

    fun getAllTaskByCategory(categoryId: Int): LiveData<List<Task>> = taskDAO.getAllTaskByCategory(categoryId)

    fun getAllTaskByStatus(status: String, userId: Int): LiveData<List<Task>> = taskDAO.getAllTaskByStatus(status, userId)

    fun getTaskById(idTask: Int): LiveData<Task> = taskDAO.getTaskById(idTask)

    fun getTaskCategory(idTask: Int): LiveData<Int> = taskDAO.getTaskCategory(idTask)
}