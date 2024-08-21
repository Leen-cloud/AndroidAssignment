package com.example.androidassignment.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.androidassignment.data.source.remote.api.ApiService
import com.example.androidassignment.domain.model.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.assertEquals
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class TodoDetailRepositoryImplTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Mock
    private lateinit var apiService: ApiService
    private lateinit var todoDetailRepository: TodoDetailRepositoryImpl

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)
        todoDetailRepository = TodoDetailRepositoryImpl(apiService)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getTodoDetail should return data from apiService`() = testScope.runTest {
        val todoId = 1
        val expectedTodo = Todo(1, 1, "Test", false)
        `when`(apiService.getTodoDetail(todoId)).thenReturn(expectedTodo)
        val resultFlow = todoDetailRepository.getTodoDetail(todoId)
        val result = resultFlow.toList()
        assertEquals(1, result.size)
        assertEquals(expectedTodo, result[0])
    }
}
