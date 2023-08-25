package com.example.todolistapp.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolistapp.data.RememberStates
import com.example.todolistapp.data.Todo
import com.example.todolistapp.data.database.TodoDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    rememberStates: RememberStates,
    db: TodoDatabase
) {
    var title by remember {
        mutableStateOf("")
    }

    val todoDao = db.dao

    val todoList = todoDao.getAllTodos().collectAsState(initial = listOf())

    val scope = rememberCoroutineScope()

//    var itemList by remember {
//        mutableStateOf(listOf<ToDoListItem>())
//    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TextField(
                value = title,
                onValueChange = {
                    title = it
                },
                modifier = Modifier.weight(1f),
                label = {
                    Text(text = "Write ToDo...")
                }
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        if (title.isNotBlank()) {
//                        itemList = itemList + ToDoListItem(title)
//                        itemList = itemList.distinctBy {
//                            it.title
//                        }
//                            itemList = itemList
//                                .plus(ToDoListItem(title))
//                                .distinctBy {
//                                    it.title
//                                }



                            scope.launch {
                                val curTodo = Todo(title)
                                withContext(Dispatchers.IO) {
                                    todoDao.upsertTodo(curTodo)
                                }
                                withContext(Dispatchers.Main) {
                                    title = ""
                                }
                            }

                        }
                    },

                    ) {
                    Text(text = "Add ToDo")
                }

                if (todoList.value.any() {
                        it.isSelected
                    }
                ) {
                    Button(
                        onClick = {
//                            val finishedTasks = itemList.filter {
//                                it.isSelected
//                            }
//                            itemList = itemList.filter {
//                                !it.isSelected
//                            }

                            val finishedTasks = todoList.value.filter {
                                it.isSelected
                            }

                            scope.launch {
                                withContext(Dispatchers.IO){
                                    finishedTasks.forEach{
                                        rememberStates.listOfTasks.value += it.title
                                        todoDao.deleteTodo(it)
                                    }
                                }
                            }

                            rememberStates.badgeCount.value = finishedTasks.size

                        },

                        ) {
                        Text(text = "Finish!")
                    }
                }

            }

        }
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
//            items(itemList) {
//                ListItem(
//                    item = it,
//                    onItemClick = {
//                        //OnItemClick
//                        itemList = itemList.map { item ->
//                            if (it == item) {
//                                item.copy(isSelected = !item.isSelected)
//                            } else
//                                item
//                        }
//                    },
//                    onDeleteClick = {
//                        todoDao.deleteTodo(Todo(it.title))
//                    },
//                )
//
//
//                Divider()
//            }

//
            items(todoList.value) { todo ->
                ListItem(
                    item = todo,
                    onItemClick = {

                        todoList.value.map {
                            if(it == todo){
                                Log.d("SWAP", "Todo clicked on $it")
                                it.isSelected = !it.isSelected
                                Log.d("SWAP", "isSelected changed $it")
                                scope.launch {
                                    withContext(Dispatchers.IO){
                                        todoDao.upsertTodo(it)
                                        Log.d("SWAP", "Updated database ${todoList.value}")

                                    }
                                }
                            }
                        }


                    },
                    onDeleteClick = {
                        scope.launch {
                            withContext(Dispatchers.IO){
                                todoDao.deleteTodo(todo)
                            }

                        }
                    }
                )

                Divider()
            }
        }
    }
}

@Composable
fun ListItem(
    item: Todo,
    onItemClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick()
            }
    ) {
        Text(
            text = item.title,
            fontSize = 15.sp,
            modifier = Modifier
                .padding(10.dp)
                .weight(1f)
        )
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete",
            modifier = Modifier
                .size(40.dp)
                .clickable {
                    onDeleteClick()
                }
        )

        if (item.isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}





