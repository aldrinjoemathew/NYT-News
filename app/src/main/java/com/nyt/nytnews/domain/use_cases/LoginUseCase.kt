package com.nyt.nytnews.domain.use_cases

import com.nyt.nytnews.domain.models.User
import com.nyt.nytnews.domain.repository.UserRepo
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val userRepo: UserRepo) {

    suspend operator fun invoke(email: String, password: String): User? {
        return userRepo.loginUser(email, password)
    }
}