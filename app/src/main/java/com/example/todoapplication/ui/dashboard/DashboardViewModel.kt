package com.example.todoapplication.ui.dashboard

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todoapplication.database.tasks.TaskRepository
import com.example.todoapplication.model.Task
import kotlinx.coroutines.launch
import java.util.Date

class DashboardViewModel(context: Context): ViewModel() {
    private  val taskRepository: TaskRepository = TaskRepository(context)

    fun getAllTaskByUser(id: Int): LiveData<List<Task>> = taskRepository.getAllTaskByUser(id)

    fun updateTaskOverdue(task: Task) = viewModelScope.launch {
        task.status = "Overdue"
        taskRepository.updateTask(task)
    }

    fun updateTaskTodo(task: Task) = viewModelScope.launch {
        task.status = "To Do"
        taskRepository.updateTask(task)
    }

    fun updateTaskCompleted(task: Task) = viewModelScope.launch {
        task.status = "Completed"
        taskRepository.updateTask(task)
    }

    class DashBoardViewModelFactory(private val context: Context): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
                return DashboardViewModel(context) as T
            }
            throw IllegalArgumentException("Unable Construct Viewmodel")
        }
    }
}