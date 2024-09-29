package com.example.todoapplication.viewmodel

import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.todoapplication.database.tasks.TaskRepository

class DashboardViewModel(context: Context): ViewModel() {
    private  val taskRepository: TaskRepository = TaskRepository(context)


}