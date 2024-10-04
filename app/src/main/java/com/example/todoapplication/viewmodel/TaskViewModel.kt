package com.example.todoapplication.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todoapplication.database.tasks.TaskRepository
import com.example.todoapplication.model.Task
import kotlinx.coroutines.launch

class TaskViewModel(context: Context): ViewModel() {
    private val taskRepository: TaskRepository = TaskRepository(context)

    fun getAllTaskByCategory(idCategory: Int): LiveData<List<Task>> = taskRepository.getAllTaskByCategory(idCategory)

    fun getAllTaskByUser(idUser: Int): LiveData<List<Task>> = taskRepository.getAllTaskByUser(idUser)

    fun getAllTaskByUserStatus(status: String, idUser: Int): LiveData<List<Task>> = taskRepository.getAllTaskByStatus(status, idUser)

    fun getTaskById(idTask: Int): LiveData<Task> = taskRepository.getTaskById(idTask)

    fun getTaskCategory(idTask: Int): LiveData<Int> = taskRepository.getTaskCategory(idTask)
    fun updateTask(task: Task) = viewModelScope.launch {
        taskRepository.updateTask(task)
    }

    class TaskViewModelFactory(private val context: Context): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(TaskViewModel::class.java)) {
                return TaskViewModel(context) as T
            }
            throw IllegalArgumentException("Unable Construct Viewmodel")
        }
    }
}