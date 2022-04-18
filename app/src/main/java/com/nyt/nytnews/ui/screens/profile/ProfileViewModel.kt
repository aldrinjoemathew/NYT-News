package com.nyt.nytnews.ui.screens.profile

import androidx.lifecycle.ViewModel
import com.nyt.nytnews.domain.models.User
import com.nyt.nytnews.domain.use_cases.GetUserProfileUseCase
import com.nyt.nytnews.domain.use_cases.GetUserSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase
) : ViewModel() {

    private val _user = getUserProfileUseCase()
    val user = _user
}