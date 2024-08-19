package com.example.androidassignment.presentation.todolist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.androidassignment.domain.model.Resource
import com.example.androidassignment.domain.model.Todo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: TodoViewModel = hiltViewModel(), onItemClick: (Int) -> Unit) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchTodos()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Home Screen") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF6200EE),
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        when (state) {
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
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Error: ${(state as Resource.Error).message}")
                }
            }

            is Resource.Success -> {
                if ((state.data as List<Todo>).isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No data available")
                    }
                } else {
                    LazyColumn(
                        contentPadding = innerPadding
                    ) {
                        items((state.data as List<Todo>)) { todo ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 3.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color(0xFFBBDEFB))
                                    .clickable { onItemClick(todo.id) }
                                    .padding(12.dp)
                            ) {
                                Text(
                                    text = todo.title,
                                    style = MaterialTheme.typography.headlineSmall,
                                    modifier = Modifier.padding(
                                        start = 16.dp,
                                        end = 16.dp,
                                        top = 8.dp,
                                        bottom = 8.dp
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


