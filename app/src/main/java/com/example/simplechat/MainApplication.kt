package com.example.simplechat

import android.app.Application
import com.example.simplechat.data.repositories.AuthRepository
import com.example.simplechat.data.storage.AppDatabase
import com.example.simplechat.services.AuthService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MainApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())
    private val database by lazy { AppDatabase.getDatabase(this, applicationScope) }
    private val authRepository by lazy { AuthRepository(database.userDao()) }
    val authService by lazy {
        AuthService(authRepository)
    }
}