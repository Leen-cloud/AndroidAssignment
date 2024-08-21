package com.example.androidassignment.domain.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.androidassignment.domain.model.Resource
import com.example.androidassignment.domain.model.Todo
import com.example.androidassignment.domain.usecase.GetTodoUseCase
import com.example.androidassignment.presentation.TodoIntent
import com.example.androidassignment.presentation.todolist.TodoViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.assertEquals
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class TodoViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Mock
    private lateinit var getTodoUseCase: GetTodoUseCase

    private lateinit var viewModel: TodoViewModel

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
    fun `processIntent LoadTodos should set loading state then success`() = testScope.runTest {
        val todos = listOf(
            Todo(1, 1, "Test Todo", false)
        )
        val resourceFlow: Flow<Resource<List<Todo>>> = flow {
            emit(Resource.Success(todos))
        }

        `when`(getTodoUseCase()).thenReturn(resourceFlow)
        viewModel.processIntent(TodoIntent.LoadTodos)
        val states = mutableListOf<Resource<List<Todo>>>()
        val job = launch {
            viewModel.viewState.collect { state ->
                states.add(state)
            }
        }

        advanceUntilIdle()
        assertEquals(1, states.size)
        assert(states[0] is Resource.Success)
        assertEquals(todos, (states[0] as Resource.Success).data)
        job.cancel()
    }

    @Test
    fun `processIntent LoadTodos should set loading state then error`() = testScope.runTest {
        val errorMessage = "Error fetching todos"
        val resourceFlow: Flow<Resource<List<Todo>>> = flow {
            emit(Resource.Error(errorMessage))
        }

        `when`(getTodoUseCase()).thenReturn(resourceFlow)
        viewModel.processIntent(TodoIntent.LoadTodos)

        val states = mutableListOf<Resource<List<Todo>>>()
        val job = launch {
            viewModel.viewState.collect { state ->
                states.add(state)
            }
        }
        advanceUntilIdle()
        assertEquals(1, states.size)
        assert(states[0] is Resource.Error)
        assertEquals(errorMessage, (states[0] as Resource.Error).message)
        job.cancel()
    }
}
