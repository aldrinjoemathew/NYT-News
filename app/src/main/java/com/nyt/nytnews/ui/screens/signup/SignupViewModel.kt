package com.nyt.nytnews.ui.screens.signup

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyt.nytnews.data.db.models.User
import com.nyt.nytnews.data.repository.UserRepo
import com.nyt.nytnews.session.SessionManager
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
    private val userRepo: UserRepo,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _signupResponse = MutableStateFlow<ResponseIo<User>>(ResponseIo.Empty)
    val signupResponse = _signupResponse.asStateFlow()

    fun signup(name: String, email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _signupResponse.update { ResponseIo.Loading }
                delay(2000)
                val user = userRepo.createUser(
                    name = name,
                    email = email,
                    password = password,
                )!!
                sessionManager.user = user
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