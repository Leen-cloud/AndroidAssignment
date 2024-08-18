package com.example.androidassignment.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.androidassignment.domain.model.Todo
import com.example.androidassignment.domain.usecase.GetTodoDetailUseCase
import com.example.androidassignment.presentation.todo_detail.TodoDetailViewModel
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

class TodoDetailViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var getTodoDetailUseCase: GetTodoDetailUseCase
    private lateinit var viewModel: TodoDetailViewModel
    private val testDispatcher = UnconfinedTestDispatcher()


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = TodoDetailViewModel(getTodoDetailUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchTodoDetail should return correct todo detail response`() = runTest {
        val response = Todo(1, 4, "test", false)
        Mockito.`when`(getTodoDetailUseCase(4)).thenReturn(flowOf(response))

        viewModel.fetchTodoDetail(4)

        val job = launch {
            viewModel.todoDetail.collect { value ->
                Assert.assertEquals(response, value)
                cancel()
            }
        }
        job.join()
    }
}