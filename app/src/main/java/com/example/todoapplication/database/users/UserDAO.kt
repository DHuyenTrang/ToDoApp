package com.example.todoapplication.database.users

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todoapplication.model.User

@Dao
interface UserDAO {
    @Insert
    suspend fun insertUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Query("SELECT * FROM users")
    fun getAllUser(): LiveData<List<User>>

    @Query("SELECT * FROM users WHERE id = :idUser")
    fun getUserById(idUser: Int): LiveData<User>

}