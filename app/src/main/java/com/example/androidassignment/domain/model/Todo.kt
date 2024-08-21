package com.example.androidassignment.domain.model

data class Todo(
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean
)
fun Todo.toDomain(): Todo {
    return Todo(
        id = this.id,
        userId = this.userId,
        title = this.title,
        completed = this.completed
    )
}
