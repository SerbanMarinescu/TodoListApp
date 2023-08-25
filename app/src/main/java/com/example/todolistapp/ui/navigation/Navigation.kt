package com.example.todolistapp.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.todolistapp.data.BottomNavItem
import com.example.todolistapp.ui.screens.HomeScreen
import com.example.todolistapp.ui.screens.NotificationScreen
import com.example.todolistapp.ui.screens.ViewCompletedScreen
import com.example.todolistapp.data.RememberStates
import com.example.todolistapp.data.database.TodoDatabase

@Composable
fun Navigation(
    navController: NavHostController,
    rememberStates: RememberStates,
    db: TodoDatabase
) {

    NavHost(navController = navController, startDestination = Routes.HomeScreen.route) {
        composable(route = Routes.HomeScreen.route) {
            HomeScreen(rememberStates, db)
        }
        composable(route = Routes.NotificationScreen.route) {
            NotificationScreen(rememberStates)
        }
        composable(
            route = "${Routes.ViewCompletedScreen.route}/{tasks}",
            arguments = listOf(
                navArgument("tasks"){
                    type = NavType.StringType
                    defaultValue = "TASK"
                }
            )

        ) {
            val tasksAsString = it.arguments?.getString("tasks")
            val taskList = tasksAsString?.split(",") ?: emptyList()
            ViewCompletedScreen(tasks = taskList)
        }
    }
}

@Composable
fun BottomNavigationMenu(
    items: List<BottomNavItem>,
    navController: NavController,
    onItemClick: (BottomNavItem) -> Unit,
    rememberStates: RememberStates
) {
    val backStackEntry = navController.currentBackStackEntryAsState()

    NavigationBar(
        containerColor = Color.DarkGray,
        tonalElevation = 5.dp
    ) {
        items.forEach {

            //val selected = it.route == backStackEntry.value?.destination?.route
            val selected = backStackEntry.value?.destination?.route?.contains(it.route) == true

            NavigationBarItem(
                selected = selected,
                onClick = {
                    onItemClick(it)
                },
                icon = {
                       BottomNavIcon(item = it, selected = selected, rememberStates)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = Color.Gray
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavIcon(
    item: BottomNavItem,
    selected: Boolean,
    rememberStates: RememberStates
) {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if(rememberStates.badgeCount.value > 0 && item.route == Routes.NotificationScreen.route){
             BadgedBox(badge = {
                 Badge {
                     Text(text = rememberStates.badgeCount.value.toString())
                 }
             }) {
                 Icon(painter = painterResource(id = item.iconId), contentDescription = null)
             }
        }
        else{
            Icon(painter = painterResource(id = item.iconId), contentDescription = null)
        }

        if(selected){
            Text(
                text = item.name,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 10.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

