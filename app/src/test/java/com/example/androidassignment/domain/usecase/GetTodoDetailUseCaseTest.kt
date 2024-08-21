package com.example.androidassignment.domain.usecase

import com.example.androidassignment.domain.model.Resource
import com.example.androidassignment.domain.model.Todo
import com.example.androidassignment.domain.repository.TodoDetailRepository
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
class GetTodoDetailUseCaseTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var getTodoDetailUseCase: GetTodoDetailUseCase
    private val todoDetailRepository: TodoDetailRepository = Mockito.mock()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getTodoDetailUseCase = GetTodoDetailUseCase(todoDetailRepository)
    }

    @Test
    fun `getTodoDetail should return success response`() = runTest {
        val todoId = 1
        val todo = Todo(2, 2, "Test", false)
        `when`(todoDetailRepository.getTodoDetail(todoId)).thenReturn(flow { emit(todo) })
        val result = getTodoDetailUseCase(todoId).toList()

        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Success)
        assertEquals(todo, (result[1] as Resource.Success).data)
    }

    @Test
    fun `getTodoDetail should return failure response`() = runTest {
        val todoId = 1
        val exception = Exception("Test Exception")
        `when`(todoDetailRepository.getTodoDetail(todoId)).thenReturn(flow { throw exception })
        val result = getTodoDetailUseCase(todoId).toList()

        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Error)
        assertEquals("Test Exception", (result[1] as Resource.Error).message)
    }
}
