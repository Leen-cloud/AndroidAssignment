package com.example.androidassignment.domain.usecase

import com.example.androidassignment.domain.model.Resource
import com.example.androidassignment.domain.model.Todo
import com.example.androidassignment.domain.repository.TodoDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTodoDetailUseCase @Inject constructor(
    private val todoRepository: TodoDetailRepository
) {

    operator fun invoke(id: Int): Flow<Resource<Todo>> = flow {
        emit(Resource.Loading())
        try {
            todoRepository.getTodoDetail(id).collect { todo ->
                emit(Resource.Success(todo))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }
}
