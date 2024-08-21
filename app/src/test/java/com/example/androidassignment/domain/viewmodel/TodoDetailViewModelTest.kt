package com.example.androidassignment.domain.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.androidassignment.domain.model.Resource
import com.example.androidassignment.domain.model.Todo
import com.example.androidassignment.domain.usecase.GetTodoDetailUseCase
import com.example.androidassignment.presentation.todo_detail.TodoDetailIntent
import com.example.androidassignment.presentation.todo_detail.TodoDetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.assertEquals
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class TodoDetailViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Mock
    private lateinit var getTodoDetailUseCase: GetTodoDetailUseCase

    private lateinit var viewModel: TodoDetailViewModel

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
    fun `processIntent should return success`() = testScope.runTest {
        val todo = Todo(
            userId = 1,
            id = 1,
            title = "Test Todo",
            completed = false
        )
        val resourceFlow: Flow<Resource<Todo>> = flow {
            emit(Resource.Success(todo))
        }
        `when`(getTodoDetailUseCase(1)).thenReturn(resourceFlow)
        viewModel.processIntent(TodoDetailIntent.LoadTodoDetail(1))

        val states = mutableListOf<Resource<Todo>>()
        val job = launch {
            viewModel.viewState.collect { state ->
                states.add(state)
            }
        }

        advanceUntilIdle()
        assertEquals(1, states.size)
        assert(states[0] is Resource.Success)
        assertEquals(todo, (states[0] as Resource.Success).data)
        job.cancel()
    }

    @Test
    fun `processIntent LoadTodoDetail should return error response`() = testScope.runTest {
        val errorMessage = "Error while fetching todo details"
        val resourceFlow: Flow<Resource<Todo>> = flow {
            emit(Resource.Error(errorMessage))
        }

        `when`(getTodoDetailUseCase(1)).thenReturn(resourceFlow)
        viewModel.processIntent(TodoDetailIntent.LoadTodoDetail(1))
        val states = mutableListOf<Resource<Todo>>()
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
