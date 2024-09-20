package com.example.todoapplication.database.categories

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todoapplication.model.Category
import com.example.todoapplication.model.User

@Dao
interface CategoryDAO {
    @Insert
    suspend fun insertCategory(category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)

    @Update
    suspend fun updateCategory(category: Category)

    @Query("SELECT * FROM categories")
    fun getAllCategory(): LiveData<List<Category>>

    @Query("SELECT * FROM categories WHERE id =:id")
    fun getCategoryById(id: Int): LiveData<Category>
}