package com.example.todolistapp.ui.navigation

sealed class Routes(val route: String){
    object HomeScreen: Routes("home")
    object NotificationScreen: Routes("notifications")
    object ViewCompletedScreen: Routes("viewCompleted"){
        fun passTasks(tasks: String) : String {
            return buildString {
                append(route)
                append("/$tasks")
            }
        }
    }
}
