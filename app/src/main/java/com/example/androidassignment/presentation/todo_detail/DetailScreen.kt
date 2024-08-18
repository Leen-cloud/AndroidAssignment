package com.example.androidassignment.presentation.todo_detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(viewModel: TodoDetailViewModel = hiltViewModel(), todoId: Int) {
    val todoDetail by viewModel.todoDetail.collectAsState()

    LaunchedEffect(todoId) {
        viewModel.fetchTodoDetail(todoId)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Detail Screen") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF6200EE),
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        todoDetail?.let { todo ->
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .padding(innerPadding)
            ) {
                Text(
                    text = "Title: ${todo.title}",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "Completed: ${todo.completed}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}