package com.nyt.nytnews.domain.use_cases

import com.nyt.nytnews.domain.models.User
import com.nyt.nytnews.domain.repository.UserRepo
import javax.inject.Inject

class SignupUseCase @Inject constructor(private val userRepo: UserRepo) {

    suspend operator fun invoke(name: String, email: String, password: String): User? {
        return userRepo.createUser(name, email, password)
    }
}