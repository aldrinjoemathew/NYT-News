package com.nyt.nytnews.domain.use_cases

import com.nyt.nytnews.domain.models.User
import com.nyt.nytnews.domain.repository.UserRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val sessionUseCase: GetUserSessionUseCase,
    private val userRepo: UserRepo
) {
    operator fun invoke(): Flow<User?> {
        val userSession = sessionUseCase.invoke() ?: throw Exception("User session not found")
        return userRepo.getUserInfo(userSession.email)
    }
}