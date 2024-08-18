package com.example.androidassignment.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.androidassignment.domain.model.Todo
import com.example.androidassignment.domain.model.TodoState
import com.example.androidassignment.domain.usecase.GetTodoUseCase
import com.example.androidassignment.presentation.todolist.TodoViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class TodoViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var getTodoUseCase: GetTodoUseCase

    private lateinit var viewModel: TodoViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = TodoViewModel(getTodoUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchTodos should return correct todo response`() = runTest {
        val todos = listOf(Todo(1, 4, "test", false))
        val response = TodoState(todos)
        Mockito.`when`(getTodoUseCase.invoke()).thenReturn(flowOf(todos))

        viewModel.fetchTodos()

        val job = launch {
            viewModel.state.collect { value ->
                Assert.assertEquals(response, value)
                cancel()
            }
        }
        job.join()
    }
}

