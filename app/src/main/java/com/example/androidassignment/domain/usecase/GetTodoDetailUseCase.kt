package com.example.androidassignment.domain.usecase

import com.example.androidassignment.domain.model.Todo
import com.example.androidassignment.domain.repository.TodoDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTodoDetailUseCase @Inject constructor(
    private val todoRepository: TodoDetailRepository
) {

    operator fun invoke(id: Int): Flow<Todo> = flow {
        emit(todoRepository.getTodoDetail(id))
    }
}
