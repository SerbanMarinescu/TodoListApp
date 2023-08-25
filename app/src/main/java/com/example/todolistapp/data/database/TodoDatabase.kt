package com.example.todolistapp.data.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.example.todolistapp.data.Todo

@Database(
    entities = [Todo::class],
    version = 2,
    autoMigrations = [AutoMigration(from = 1, to = 2)]
)
abstract class TodoDatabase: RoomDatabase() {

    abstract val dao: TodoDao
}