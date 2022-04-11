package com.nyt.nytnews.db.repository

import com.nyt.nytnews.db.dao.UserDao
import com.nyt.nytnews.db.entities.UserEntity
import com.nyt.nytnews.db.models.User
import javax.inject.Inject

class UserRepoImpl @Inject constructor(private val userDao: UserDao) : UserRepo {

    override suspend fun createUser(user: UserEntity): Long {
        return userDao.insert(user = user)
    }

    override suspend fun loginUser(email: String, password: String): User? {
        return userDao.loginUser(email, password)
    }

}