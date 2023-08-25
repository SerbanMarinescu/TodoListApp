package com.example.todolistapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(
    @ColumnInfo("TodoTitle")
    val title: String,

    @ColumnInfo("IsSelected", defaultValue = "False")
    var isSelected: Boolean = false,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)