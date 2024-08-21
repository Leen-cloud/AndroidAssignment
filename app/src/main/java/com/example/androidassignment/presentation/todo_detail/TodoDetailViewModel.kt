package com.example.androidassignment.presentation.todo_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidassignment.domain.model.Resource
import com.example.androidassignment.domain.model.Todo
import com.example.androidassignment.domain.usecase.GetTodoDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class TodoDetailIntent {
    data class LoadTodoDetail(val id: Int) : TodoDetailIntent()
}

@HiltViewModel
class TodoDetailViewModel @Inject constructor(
    private val getTodoDetailUseCase: GetTodoDetailUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow<Resource<Todo>>(Resource.Loading())
    val viewState: StateFlow<Resource<Todo>> = _viewState.asStateFlow()

    fun processIntent(intent: TodoDetailIntent) {
        when (intent) {
            is TodoDetailIntent.LoadTodoDetail -> fetchTodoDetail(intent.id)
        }
    }

    fun fetchTodoDetail(id: Int) {
        viewModelScope.launch {
            getTodoDetailUseCase(id).collect { state ->
                when (state) {
                    is Resource.Loading -> {

                    }

                    is Resource.Success -> {
                        _viewState.value = state
                    }

                    is Resource.Error -> {
                        _viewState.value = state
                    }
                }
            }
        }
    }
}
