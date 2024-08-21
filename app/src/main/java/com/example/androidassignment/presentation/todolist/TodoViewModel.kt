package com.example.androidassignment.presentation.todolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidassignment.domain.model.Resource
import com.example.androidassignment.domain.model.Todo
import com.example.androidassignment.domain.usecase.GetTodoUseCase
import com.example.androidassignment.presentation.TodoIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val getTodoUseCase: GetTodoUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow<Resource<List<Todo>>>(Resource.Loading())
    val viewState: StateFlow<Resource<List<Todo>>> = _viewState

    fun processIntent(intent: TodoIntent) {
        when (intent) {
            is TodoIntent.LoadTodos -> fetchTodos()
        }
    }

    fun fetchTodos() {
        viewModelScope.launch {
            getTodoUseCase().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                    }

                    is Resource.Success -> {
                        _viewState.value = result
                    }

                    is Resource.Error -> {
                        _viewState.value = result
                    }
                }
            }
        }
    }
}
