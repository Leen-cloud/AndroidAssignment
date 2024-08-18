package com.example.androidassignment.domain.usecase

import com.example.androidassignment.domain.model.Todo
import com.example.androidassignment.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTodoUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {

    operator fun invoke(): Flow<List<Todo>> = flow {
        emit(todoRepository.getTodos())
    }
}
