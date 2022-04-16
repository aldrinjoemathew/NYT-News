package com.nyt.nytnews.domain.use_cases

import com.nyt.nytnews.data.session.SessionManager
import com.nyt.nytnews.domain.models.User
import javax.inject.Inject

class GetUserSessionUseCase @Inject constructor(private val sessionManager: SessionManager) {
    operator fun invoke(): User? {
        return sessionManager.user
    }
}