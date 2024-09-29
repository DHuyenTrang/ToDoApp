package com.example.todoapplication.viewmodel

import android.content.ClipDescription
import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.todoapplication.database.tasks.TaskRepository
import com.example.todoapplication.database.users.UserRepository
import com.example.todoapplication.model.Category
import com.example.todoapplication.model.Task
import java.util.Date

class TaskViewModel(context: Context): ViewModel() {
    private val taskRepository: TaskRepository = TaskRepository(context)
    private val userRepository: UserRepository = UserRepository(context)

    fun insertTask(title: String, description: String, dueDate: Date, categoryId: Int) {

    }
}