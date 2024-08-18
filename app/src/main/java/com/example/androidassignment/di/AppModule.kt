package com.example.androidassignment.di

import android.content.Context
import com.example.androidassignment.data.repository.TodoDetailRepositoryImpl
import com.example.androidassignment.data.repository.TodoRepositoryImpl
import com.example.androidassignment.data.source.local.AppDatabase
import com.example.androidassignment.data.source.local.dao.TodoDao
import com.example.androidassignment.data.source.remote.api.ApiService
import com.example.androidassignment.domain.repository.TodoDetailRepository
import com.example.androidassignment.domain.repository.TodoRepository
import com.example.androidassignment.domain.usecase.GetTodoDetailUseCase
import com.example.androidassignment.domain.usecase.GetTodoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideTodoDao(database: AppDatabase):
            TodoDao {
        return database.todoDao()
    }

    @Provides
    @Singleton
    fun provideTodoRepository(apiService: ApiService, todoDao: TodoDao): TodoRepository {
        return TodoRepositoryImpl(apiService, todoDao)
    }

    @Provides
    @Singleton
    fun provideTodoDetailRepository(
        apiService: ApiService
    ): TodoDetailRepository {
        return TodoDetailRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideGetTodoUseCase(todoRepository: TodoRepository): GetTodoUseCase {
        return GetTodoUseCase(todoRepository)
    }

    @Provides
    @Singleton
    fun provideGetTodoDetailUseCase(todoDetailRepository: TodoDetailRepository): GetTodoDetailUseCase {
        return GetTodoDetailUseCase(todoDetailRepository)
    }
}