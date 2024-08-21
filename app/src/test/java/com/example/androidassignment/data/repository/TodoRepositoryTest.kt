package com.example.androidassignment.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.androidassignment.data.mapper.TodoMapper.toDomain
import com.example.androidassignment.data.source.local.dao.TodoDao
import com.example.androidassignment.data.source.local.entity.TodoEntity
import com.example.androidassignment.data.source.remote.api.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class TodoRepositoryImplTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Mock
    private lateinit var apiService: ApiService

    @Mock
    private lateinit var todoDao: TodoDao
    private lateinit var todoRepository: TodoRepositoryImpl

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)
        todoRepository = TodoRepositoryImpl(apiService, todoDao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getTodos should return success`() = testScope.runTest {
        val todosLocal = listOf(
            TodoEntity(1, 1, "Test", false)
        )
        val todosDomain = todosLocal.map { it.toDomain() }
        `when`(todoDao.getTodos()).thenReturn(todosLocal)
        val resultFlow = todoRepository.getTodos()
        val result = resultFlow.toList()
        assertEquals(1, result.size)
        assertEquals(todosDomain, result[0])
    }
}
