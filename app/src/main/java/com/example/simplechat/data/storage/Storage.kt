package com.example.simplechat.data.storage

import kotlinx.coroutines.flow.Flow

interface Storage<T> {
    fun insert(data: T): Flow<Int>

    fun insert(data: List<T>): Flow<Int>

    fun get(where: (T) -> Boolean): Flow<T>

    fun getAll(): Flow<List<T>>

    fun clearAll(): Flow<Int>
}