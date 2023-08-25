package com.example.todolistapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.todolistapp.data.RememberStates

@Composable
fun NotificationScreen(rememberStates: RememberStates) {

    val numberOfTasks by remember {
        mutableStateOf(rememberStates.badgeCount.value)
    }

    rememberStates.badgeCount.value = 0

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ){
        if(numberOfTasks == 0){
            Text(
                text = "Go and complete some tasks!"
            )
        } else{
            Text(
                text = "Congratulations for finishing $numberOfTasks tasks! :)"
            )
        }

    }
}