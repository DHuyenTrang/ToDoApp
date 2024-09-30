package com.example.todoapplication.ui.add

import android.content.ClipDescription
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todoapplication.database.tasks.TaskRepository
import com.example.todoapplication.model.Task
import com.example.todoapplication.ui.add.CategoryViewModel
import kotlinx.coroutines.launch
import java.util.Date

class AddFragmentViewModel(context: Context): ViewModel() {
    private  val taskRepository: TaskRepository = TaskRepository(context)

    fun addTask(title: String,
                description: String,
                date: Date,
                status: String,
                category: Int,
                user: Int
                ) = viewModelScope.launch {
        val newTask = Task(title, description, date, status, category, user)
        taskRepository.insertTask(newTask)
    }

    class AddFragmentViewModelFactory(private val context: Context): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(AddFragmentViewModel::class.java)) {
                return AddFragmentViewModel(context) as T
            }
            throw IllegalArgumentException("Unable Construct Viewmodel")
        }
    }
}