package com.nyt.nytnews.db.repository

import com.nyt.nytnews.db.entities.UserEntity
import com.nyt.nytnews.db.models.User

interface UserRepo {
    suspend fun createUser(user: UserEntity): Long
    suspend fun loginUser(email: String, password: String): User?
}