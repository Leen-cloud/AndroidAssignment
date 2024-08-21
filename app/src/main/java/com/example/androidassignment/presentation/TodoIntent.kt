package com.example.androidassignment.presentation

sealed class TodoIntent {
    object LoadTodos : TodoIntent()
}