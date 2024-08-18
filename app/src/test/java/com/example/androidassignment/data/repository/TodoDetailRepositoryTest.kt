package com.example.androidassignment.data.repository

import com.example.androidassignment.data.source.remote.api.ApiService
import com.example.androidassignment.domain.model.Todo
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`


class TodoDetailRepositoryImplTest {

    private lateinit var apiService: ApiService
    private lateinit var repository: TodoDetailRepositoryImpl

    @Before
    fun setUp() {
        apiService = mock(ApiService::class.java)
        repository = TodoDetailRepositoryImpl(apiService)
    }

    @Test
    fun `getTodoDetail should returns correct Todo response`() = runBlocking {
        val id = 1
        val apiTodo =Todo(2, 3, "description",false)
        val domainTodo =Todo(2, 3, "description",false)

        `when`(apiService.getTodoDetail(id)).thenReturn(apiTodo)

        val result = repository.getTodoDetail(id)

        assertEquals(domainTodo, result)
    }
}
