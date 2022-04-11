package com.nyt.nytnews.db.repository

import com.nyt.nytnews.db.models.User

interface UserRepo {
    suspend fun createUser(name: String, email: String, password: String): User?
    suspend fun loginUser(email: String, password: String): User?
}