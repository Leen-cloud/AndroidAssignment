package com.example.androidassignment.presentation.todolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidassignment.domain.model.TodoState
import com.example.androidassignment.domain.usecase.GetTodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TodoViewModel @Inject constructor(
    private val getTodoUseCase: GetTodoUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(TodoState())
    val state: StateFlow<TodoState> get() = _state

    init {
        fetchTodos()
    }

    fun fetchTodos() {
        viewModelScope.launch {
            getTodoUseCase().collect { todos ->
                _state.value = _state.value.copy(todos = todos)
            }
        }
    }


}
