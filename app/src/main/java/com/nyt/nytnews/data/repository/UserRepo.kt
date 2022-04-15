package com.nyt.nytnews.data.repository

import com.nyt.nytnews.data.db.models.User

interface UserRepo {
    suspend fun createUser(name: String, email: String, password: String): User?
    suspend fun loginUser(email: String, password: String): User?
}