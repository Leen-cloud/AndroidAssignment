package com.example.androidassignment.domain.usecase

import com.example.androidassignment.domain.model.Resource
import com.example.androidassignment.domain.model.Todo
import com.example.androidassignment.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTodoUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {

    operator fun invoke(): Flow<Resource<List<Todo>>> = flow {
        try {
            emit(Resource.Loading())
            val todos = todoRepository.getTodos()
            emit(Resource.Success(todos))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }
}
