package com.example.todoapplication.database.categories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import com.example.todoapplication.database.AppDatabase
import com.example.todoapplication.model.Category

class CategoryRepository(context: Context) {
    private val categoryDAO: CategoryDAO
    init {
        val appDatabase = AppDatabase.getInstance(context)
        categoryDAO = appDatabase.getCategoryDao()
    }

    suspend fun insertCategory(category: Category) = categoryDAO.insertCategory(category)

    suspend fun deleteCategory(category: Category) = categoryDAO.deleteCategory(category)

    suspend fun updateCategory(category: Category) = categoryDAO.updateCategory(category)

    fun getAllCategory(): LiveData<List<Category>> = categoryDAO.getAllCategory()

    fun getCategoryById(id: Int): LiveData<Category> = categoryDAO.getCategoryById(id)
}