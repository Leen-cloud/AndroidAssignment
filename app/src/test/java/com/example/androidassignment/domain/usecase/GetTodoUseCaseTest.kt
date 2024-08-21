package com.example.androidassignment.domain.usecase

import com.example.androidassignment.domain.model.Resource
import com.example.androidassignment.domain.model.Todo
import com.example.androidassignment.domain.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`


@ExperimentalCoroutinesApi
class GetTodoUseCaseTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var getTodoUseCase: GetTodoUseCase
    private val todoRepository: TodoRepository = Mockito.mock()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getTodoUseCase = GetTodoUseCase(todoRepository)
    }

    @Test
    fun `getTodos should return successfully`() = runTest {
        val todos = listOf( Todo(id = 4, userId = 4, title = "Test 1", completed = false),
            Todo(id = 4, userId = 4, title = "Test 2", completed = false)   )
        `when`(todoRepository.getTodos()).thenReturn(flow { emit(todos) })
        val result = getTodoUseCase().toList()

        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Success)
        assertEquals(todos, (result[1] as Resource.Success).data)
    }

    @Test
    fun `getTodos should return error when an exception occurs`() = runTest {
        val exception = Exception("Test Exception")
        `when`(todoRepository.getTodos()).thenReturn(flow { throw exception })
        val result = getTodoUseCase().toList()

        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Error)
        assertEquals("Test Exception", (result[1] as Resource.Error).message)
    }
}
