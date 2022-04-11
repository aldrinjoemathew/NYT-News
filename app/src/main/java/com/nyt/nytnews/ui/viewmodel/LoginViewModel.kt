package com.nyt.nytnews.ui.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyt.nytnews.db.entities.UserEntity
import com.nyt.nytnews.db.models.User
import com.nyt.nytnews.db.repository.UserRepo
import com.nyt.nytnews.utils.ResponseIo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepo: UserRepo) : ViewModel() {

    private val _loginResponse = MutableStateFlow<ResponseIo<User>>(ResponseIo.Empty)
    val loginResponse = _loginResponse.asStateFlow()

    init {
        //TODO added for testing purpose, remove after signup flow completed
        viewModelScope.launch(Dispatchers.IO) {
            userRepo.createUser(
                UserEntity(
                    name = "Aldrin",
                    email = "aldrin@mail.com",
                    password = "password",
                    dob = ""
                )
            )
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loginResponse.update { ResponseIo.Loading }
                delay(2000)
                val user = userRepo.loginUser(email, password)!!
                Timber.d("Login response: $user")
                _loginResponse.update { ResponseIo.Data(user) }
            } catch (e: Exception) {
                e.printStackTrace()
                _loginResponse.update { ResponseIo.Error(message = "Login failed") }
            }
        }
    }

    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String) = password.trim().isNotEmpty()
}