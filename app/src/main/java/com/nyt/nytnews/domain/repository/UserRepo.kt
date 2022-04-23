package com.nyt.nytnews.domain.repository

import com.nyt.nytnews.domain.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepo {
    suspend fun createUser(name: String, email: String, password: String): User?
    suspend fun loginUser(email: String, password: String): User?
    fun getUserInfo(email: String): Flow<User?>
}