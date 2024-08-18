package com.example.androidassignment.data.mapper

import com.example.androidassignment.domain.model.Todo
import com.example.androidassignment.data.source.local.entity.TodoEntity

object TodoMapper {
    fun toDomain(dto: Todo): Todo {
        return Todo(
            userId = dto.userId,
            id = dto.id,
            title = dto.title,
            completed = dto.completed
        )
    }

    fun toDomainList(dtos: List<Todo>): List<Todo> {
        return dtos.map { toDomain(it) }
    }

    fun Todo.toEntity(): TodoEntity {
        return TodoEntity(
            id = id,
            userId = userId,
            title = title,
            completed = completed
        )
    }

    fun TodoEntity.toDomain(): Todo {
        return Todo(
            id = id,
            userId = userId,
            title = title,
            completed = completed
        )
    }
}

