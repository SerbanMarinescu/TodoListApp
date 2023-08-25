package com.example.todolistapp.data

import androidx.compose.runtime.mutableStateOf

class RememberStates {

    val badgeCount = mutableStateOf(0)

    val listOfTasks = mutableStateOf(listOf<String>())
}