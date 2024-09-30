package com.example.todoapplication.ui.dashboard

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.todoapplication.database.tasks.TaskRepository
import com.example.todoapplication.ui.add.CategoryViewModel

class DashboardViewModel(context: Context): ViewModel() {
    private  val taskRepository: TaskRepository = TaskRepository(context)



    class DashBoardViewModelFactory(private val context: Context): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
            if(modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
                return DashboardViewModel(context) as T
            }
            throw IllegalArgumentException("Unable Construct Viewmodel")
        }
    }
}