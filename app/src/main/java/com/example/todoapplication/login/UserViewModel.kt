package com.example.todoapplication.login

import android.content.Context
import androidx.collection.intSetOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todoapplication.database.users.UserRepository
import com.example.todoapplication.model.User
import kotlinx.coroutines.launch
import java.sql.Struct

class UserViewModel(context: Context): ViewModel(){
    private val userRepository: UserRepository = UserRepository(context)

    fun insertUser(username: String, email: String, password: String) = viewModelScope.launch {
        val user = User(username, password, email)
        userRepository.insertUser(user)
    }

    fun updateUser(user: User, newName: String) = viewModelScope.launch {
        user.username = newName
        userRepository.updateUser(user)
    }

    fun getUser(idUser: Int): LiveData<User> = userRepository.getUserById(idUser)

    fun getAllUser(): LiveData<List<User>> = userRepository.getAllUser()

    class UserViewModelFactory(private val context: Context): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(UserViewModel::class.java)) {
                return UserViewModel(context) as T
            }
            throw IllegalArgumentException("Unable Construct Viewmodel")
        }
    }
}