package com.nyt.nytnews.ui.screens.signup

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyt.nytnews.domain.models.User
import com.nyt.nytnews.domain.use_cases.SignupUseCase
import com.nyt.nytnews.domain.use_cases.UpdateUserSessionUseCase
import com.nyt.nytnews.utils.ResponseIo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val signupUseCase: SignupUseCase,
    private val updateUserSessionUseCase: UpdateUserSessionUseCase
) : ViewModel() {

    private val _signupResponse = MutableStateFlow<ResponseIo<User>>(ResponseIo.Empty)
    val signupResponse = _signupResponse.asStateFlow()

    fun signup(name: String, email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _signupResponse.update { ResponseIo.Loading }
                delay(2000)
                val user = signupUseCase(
                    name = name,
                    email = email,
                    password = password,
                )!!
                updateUserSessionUseCase(user)
                _signupResponse.update { ResponseIo.Data(user) }
            } catch (e: Exception) {
                e.printStackTrace()
                _signupResponse.update { ResponseIo.Error("Signup failed") }
            }
        }
    }

    fun isValidName(name: String) = name.trim().isNotEmpty()

    fun isValidEmail(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isValidPassword(password: String) = password.trim().isNotEmpty()
}