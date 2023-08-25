package com.example.todolistapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ViewCompletedScreen(tasks: List<String>) {
    
    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier.padding(16.dp)
    ) {

        Text(
            text = "Completed Tasks",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Divider()
        Spacer(modifier = Modifier.height(10.dp))

        tasks.forEach {
            Text(
                text = it,
                fontSize = 15.sp
            )
        }
    }
}