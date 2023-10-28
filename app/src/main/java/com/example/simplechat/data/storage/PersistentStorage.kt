package com.example.simplechat.data.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.lang.reflect.Type

class PersistentStorage<T> constructor(
    private val gson: Gson,
    private val type: Type,
    private val dataStore: DataStore<Preferences>,
    private val preferenceKey: Preferences.Key<String>,
) : Storage<T> {

    override fun insert(data: T): Flow<Int> {
        TODO("Not yet implemented")
    }

    override fun insert(data: List<T>): Flow<Int> {
        TODO("Not yet implemented")
    }

    override fun get(where: (T) -> Boolean): Flow<T> {
        TODO("Not yet implemented")
    }

    override fun getAll(): Flow<List<T>> {
        return dataStore.data.map {
            val jsonString = it[preferenceKey] ?: Companion.EMPTY_JSON_STRING;
            val elements = gson.fromJson<List<T>>(jsonString, type)
            elements
        }
    }

    override fun clearAll(): Flow<Int> {
        TODO("Not yet implemented")
    }

    companion object {
        private const val EMPTY_JSON_STRING = "[]";
    }
}