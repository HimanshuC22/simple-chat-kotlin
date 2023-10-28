package com.example.simplechat.data.repositories

import android.util.Log
import com.example.simplechat.data.api.AuthApi
import com.example.simplechat.data.models.User
import com.example.simplechat.data.storage.dao.UserDao

class AuthRepository(
    private val userDao: UserDao,
) {

    suspend fun saveUser(user : User) {
        userDao.deleteAll()
        userDao.insert(user)
    }

    suspend fun getCurrentUser() : User? {
        val allUsers = userDao.getAll()
        if(allUsers.isEmpty()) return null
        return allUsers[0]
    }
}