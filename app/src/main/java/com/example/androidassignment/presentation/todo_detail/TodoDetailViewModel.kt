package com.example.androidassignment.presentation.todo_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidassignment.domain.model.Todo
import com.example.androidassignment.domain.usecase.GetTodoDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TodoDetailViewModel @Inject constructor(
    private val getTodoDetailUseCase: GetTodoDetailUseCase
) : ViewModel() {

    private val _todoDetail = MutableStateFlow<Todo?>(null)
    val todoDetail: StateFlow<Todo?> = _todoDetail.asStateFlow()

    fun fetchTodoDetail(id: Int) {
        viewModelScope.launch {
            getTodoDetailUseCase(id).collect { todo ->
                _todoDetail.value = todo
            }
        }
    }
}
