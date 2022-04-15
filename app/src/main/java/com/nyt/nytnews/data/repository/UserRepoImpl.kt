package com.nyt.nytnews.data.repository

import com.nyt.nytnews.data.db.dao.UserDao
import com.nyt.nytnews.data.db.entities.UserEntity
import com.nyt.nytnews.data.db.models.User
import javax.inject.Inject

class UserRepoImpl @Inject constructor(private val userDao: UserDao) : UserRepo {

    override suspend fun createUser(name: String, email: String, password: String): User? {
        userDao.insert(
            user = UserEntity(
                name = name,
                email = email,
                password = password,
                dob = ""
            )
        )
        return userDao.loginUser(email, password)
    }

    override suspend fun loginUser(email: String, password: String): User? {
        return userDao.loginUser(email, password)
    }

}