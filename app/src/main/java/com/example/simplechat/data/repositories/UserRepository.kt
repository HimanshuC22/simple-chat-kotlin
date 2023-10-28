package com.example.simplechat.data.repositories

import com.example.simplechat.data.api.AuthApi
import com.example.simplechat.data.storage.dao.UserDao

class UserRepository(
    private val authApi: AuthApi,
    private val userDao: UserDao,
) {
}