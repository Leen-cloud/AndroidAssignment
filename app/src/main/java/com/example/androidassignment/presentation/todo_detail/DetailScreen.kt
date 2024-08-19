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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.androidassignment.domain.model.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(viewModel: TodoDetailViewModel = hiltViewModel(), todoId: Int) {
    val state by viewModel.todoDetailState.collectAsState()

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
        when(state) {
            is Resource.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is Resource.Error -> {
                Text("Error: ${state as Resource.Error}", modifier = Modifier.padding(innerPadding))
            }
            is Resource.Success-> {
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .padding(innerPadding)
                ) {
                    Text(
                        text = "Title: ${state.data?.title}",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        text = "Completed: ${state.data?.completed}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
