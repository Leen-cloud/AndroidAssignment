package com.example.androidassignment.presentation.todolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidassignment.domain.model.Resource
import com.example.androidassignment.domain.model.Todo
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

    private val _state = MutableStateFlow<Resource<List<Todo>>>(Resource.Loading())
    val state: StateFlow<Resource<List<Todo>>> get() = _state

    init {
        fetchTodos()
    }

    fun fetchTodos() {
        viewModelScope.launch {
            getTodoUseCase().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                    }

                    is Resource.Success -> {
                        _state.value = result
                    }

                    is Resource.Error -> {
                        _state.value = result
                    }
                }
            }
        }
    }
}
