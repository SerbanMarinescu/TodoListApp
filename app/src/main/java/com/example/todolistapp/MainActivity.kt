package com.example.todolistapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.todolistapp.data.BottomNavItem
import com.example.todolistapp.ui.navigation.Routes
import com.example.todolistapp.ui.navigation.BottomNavigationMenu
import com.example.todolistapp.ui.navigation.Navigation
import com.example.todolistapp.ui.theme.TODOListAppTheme
import com.example.todolistapp.data.RememberStates
import com.example.todolistapp.data.database.TodoDatabase
import java.lang.IllegalArgumentException

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TODOListAppTheme {
                val navController = rememberNavController()
                val rememberStates = RememberStates()

                val db = Room.databaseBuilder(
                    applicationContext,
                    TodoDatabase::class.java,
                    "TODO.db"
                ).build()

                Scaffold(
                    bottomBar = {
                    BottomNavigationMenu(items = listOf(
                        BottomNavItem(
                            "Home",
                            Routes.HomeScreen.route,
                            R.drawable.ic_home
                        ),
                        BottomNavItem(
                            "Notifications",
                            Routes.NotificationScreen.route,
                            R.drawable.ic_notifications
                        ),
                        BottomNavItem(
                            "Completed Tasks",
                            Routes.ViewCompletedScreen.route,
                            R.drawable.ic_settings
                        )
                    ),

                        navController = navController,

                        onItemClick = {

                            try {
                                if(it.route == Routes.ViewCompletedScreen.route){
                                    val tasks = rememberStates.listOfTasks.value
                                    val taskAsString = tasks.joinToString(",")
                                    navController.navigate(Routes.ViewCompletedScreen.passTasks(taskAsString))
                                } else{
                                    navController.navigate(it.route)
                                }
                            } catch (illegalArgumentException: IllegalArgumentException){
                                Toast.makeText(applicationContext, "Complete some tasks first!", Toast.LENGTH_LONG).show()
                            }



                        },
                        rememberStates = rememberStates
                    )
                }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                    ){
                        Navigation(navController = navController, rememberStates, db)
                    }

                }
            }
        }
    }
}



