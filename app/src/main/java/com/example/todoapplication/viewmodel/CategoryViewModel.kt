package com.example.todoapplication.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todoapplication.database.categories.CategoryRepository
import com.example.todoapplication.login.UserViewModel
import com.example.todoapplication.model.Category
import kotlinx.coroutines.launch

class CategoryViewModel(context: Context): ViewModel() {
    private val categoryRepository = CategoryRepository(context)

    fun insertCategory(name: String) = viewModelScope.launch {
        val newCategory = Category(name)
        categoryRepository.insertCategory(newCategory)
    }

    fun getAllCategory(): LiveData<List<Category>> = categoryRepository.getAllCategory()

    fun getCategoryById(id: Int): LiveData<Category> = categoryRepository.getCategoryById(id)

    class CategoryViewModelFactory(private val context: Context): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
                return CategoryViewModel(context) as T
            }
            throw IllegalArgumentException("Unable Construct Viewmodel")
        }
    }
}