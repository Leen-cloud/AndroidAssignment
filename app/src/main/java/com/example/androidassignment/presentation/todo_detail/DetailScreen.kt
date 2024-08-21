package com.example.androidassignment.presentation.todo_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.androidassignment.domain.model.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(viewModel: TodoDetailViewModel = hiltViewModel(), todoId: Int) {
    val viewState by viewModel.viewState.collectAsState()
    val backgroundColor by remember { mutableStateOf(Color.White) }

    LaunchedEffect(todoId) {
        viewModel.processIntent(TodoDetailIntent.LoadTodoDetail(todoId))
    }

    DisposableEffect(Unit) {
        onDispose {
            println("disposed")
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Detail Screen") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        when (viewState) {
            is Resource.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(backgroundColor),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is Resource.Error -> {
                Text(
                    "Error: ${(viewState as Resource.Error).message}",
                    modifier = Modifier.padding(innerPadding)
                )
            }

            is Resource.Success -> {
                Column(
                    modifier = Modifier
                        .padding(4.dp)
                        .padding(innerPadding)
                ) {
                    val todo = viewState.data
                    todo?.let {
                        Text(
                            text = "Title: ${todo.title}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Completed: ${todo.completed}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}
