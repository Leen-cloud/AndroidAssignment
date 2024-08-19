package com.example.androidassignment.data.repository

import com.example.androidassignment.data.mapper.TodoMapper
import com.example.androidassignment.data.mapper.TodoMapper.toDomain
import com.example.androidassignment.data.mapper.TodoMapper.toEntity
import com.example.androidassignment.data.source.local.dao.TodoDao
import com.example.androidassignment.data.source.remote.api.ApiService
import com.example.androidassignment.domain.model.Todo
import com.example.androidassignment.domain.repository.TodoRepository
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val todoDao: TodoDao

) : TodoRepository {

    override suspend fun getTodos(): List<Todo> {
        val todosData = todoDao.getTodos()
        if (todosData.isNotEmpty()) {
            return todosData.map { it.toDomain() }
        } else {
            val response = apiService.getTodos()
            todoDao.insertTodos(response.map { it.toEntity() })
            return TodoMapper.toDomainList(response)
        }
    }
}


