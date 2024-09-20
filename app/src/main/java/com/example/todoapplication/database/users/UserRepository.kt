package com.example.todoapplication.database.users

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.todoapplication.database.AppDatabase
import com.example.todoapplication.model.User

class UserRepository(context: Context) {
    private val userDAO: UserDAO

    init {
        val appDatabase = AppDatabase.getInstance(context)
        userDAO = appDatabase.getUserDao()
    }

    suspend fun insertUser(user: User) = userDAO.insertUser(user)

    suspend fun deleteUser(user: User) = userDAO.deleteUser(user)

    suspend fun updateUser(user: User) = userDAO.updateUser(user)

    fun getAllUser(): LiveData<List<User>> = userDAO.getAllUser()
}