package com.example.androidassignment.data.repository

import com.example.androidassignment.data.mapper.TodoMapper.toDomain
import com.example.androidassignment.data.mapper.TodoMapper.toEntity
import com.example.androidassignment.data.source.local.dao.TodoDao
import com.example.androidassignment.data.source.remote.api.ApiService
import com.example.androidassignment.domain.model.Todo
import com.example.androidassignment.data.source.local.entity.TodoEntity
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.logging.Logger


class TodoRepositoryImplTest {

    private lateinit var todoRepository: TodoRepositoryImpl
    private lateinit var apiService: ApiService
    private lateinit var todoDao: TodoDao
    private lateinit var logger: Logger

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        apiService = mock(ApiService::class.java)
        todoDao = mock(TodoDao::class.java)
        logger = mock(Logger::class.java)
        todoRepository = TodoRepositoryImpl(apiService, todoDao)

    }

    @Test
    fun `getTodos should return correct todos response`(): Unit = runBlocking {
        val cachedTodos = listOf(
            TodoEntity(id = 1, userId = 4, title = "Test Todo 1", completed = false),
            TodoEntity(id = 2, userId = 7, title = "Test Todo 2", completed = true),
            TodoEntity(id = 3, userId = 6, title = "Test Todo 3", completed = false)
        )
        val domainTodos = cachedTodos.map { it.toDomain() }

        `when`(todoDao.getTodos()).thenReturn(cachedTodos)

        val result = todoRepository.getTodos()

        assertEquals(domainTodos, result)
        verify(apiService, never()).getTodos()
    }

    @Test
    fun `getTodos should return correct todos from API and caches when no cached todos`() = runBlocking {
        val apiTodos = listOf(
            Todo(id = 1, userId = 4, title = "Test Todo 1", completed = false),
            Todo(id = 2, userId = 7, title = "Test Todo 2", completed = true),
            Todo(id = 3, userId = 6, title = "Test Todo 3", completed = false)
        )

        val apiTodosEntities = apiTodos.map { it.toEntity() }

        `when`(todoDao.getTodos()).thenReturn(emptyList())
        `when`(apiService.getTodos()).thenReturn(apiTodos)

        val result = todoRepository.getTodos()

        assertEquals(apiTodos, result)
        verify(todoDao).insertTodos(apiTodosEntities)

    }


    @Test
    fun `saveTodos fetches from Api and saves to Dao`() = runBlocking {
        val apiResponse = listOf(
            Todo(id = 1, userId = 4, title = "Test Todo 1", completed = false),
            Todo(id = 2, userId = 7, title = "Test Todo 2", completed = true),
            Todo(id = 3, userId = 6, title = "Test Todo 3", completed = false)
        )

        `when`(apiService.getTodos()).thenReturn(apiResponse)

        todoRepository.saveTodos()

        verify(apiService).getTodos()
        verify(todoDao).insertTodos(apiResponse.map { it.toEntity() })
    }


}
