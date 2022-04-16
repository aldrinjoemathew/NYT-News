package com.nyt.nytnews.domain.use_cases

import com.nyt.nytnews.data.session.SessionManager
import com.nyt.nytnews.domain.models.User
import javax.inject.Inject

class UpdateUserSessionUseCase @Inject constructor(private val sessionManager: SessionManager) {
    operator fun invoke(user: User?) {
        sessionManager.user = user
    }
}