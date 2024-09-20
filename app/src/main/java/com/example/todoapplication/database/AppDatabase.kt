package com.example.todoapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todoapplication.database.categories.CategoryDAO
import com.example.todoapplication.database.tasks.TaskDAO
import com.example.todoapplication.database.users.UserDAO
import com.example.todoapplication.model.Category
import com.example.todoapplication.model.Task
import com.example.todoapplication.model.User

@Database(
    entities = [
        User::class,
        Task::class,
        Category::class], version = 1
)

abstract class AppDatabase: RoomDatabase() {
    abstract fun getUserDao() : UserDAO
    abstract fun getTaskDao() : TaskDAO
    abstract fun getCategoryDao() : CategoryDAO

    companion object {
        @Volatile
        private var database_name = "todo_database"
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = androidx.room.Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    database_name
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}