package com.example.androidassignment.domain.model

data class TodoState(
    val todos: List<Todo> = emptyList(),
    val selectedTodo: Todo? = null
)