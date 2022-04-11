package com.nyt.nytnews.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyt.nytnews.utils.ResponseIo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")

    private val _loginResponse = MutableStateFlow<ResponseIo<String>>(ResponseIo.Empty)
    val loginResponse = _loginResponse.asStateFlow()

    fun login() {
        viewModelScope.launch (Dispatchers.IO) {
            _loginResponse.update { ResponseIo.Loading }
            delay(3000)
            _loginResponse.update { ResponseIo.Empty }
        }
    }
}