package com.nyt.nytnews.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.nyt.nytnews.R
import com.nyt.nytnews.ui.composables.ProgressButton
import com.nyt.nytnews.ui.navigation.NytNavigationAction
import com.nyt.nytnews.ui.viewmodel.LoginViewModel
import com.nyt.nytnews.utils.ResponseIo

@Composable
fun LoginScreen(navigationAction: NytNavigationAction, viewModel: LoginViewModel) {

    val loginResponse by viewModel.loginResponse.collectAsState()
    val focusManager = LocalFocusManager.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BrandImage()
        Spacer(modifier = Modifier.size(10.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Email") },
            value = email,
            onValueChange = { email = it },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email, imeAction = ImeAction.Next,
            ),
            singleLine = true
        )
        Spacer(modifier = Modifier.size(10.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Password") },
            value = password,
            onValueChange = { password = it },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password, imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus(true)
                viewModel.login()
            }),
            singleLine = true
        )
        Spacer(modifier = Modifier.size(20.dp))
        ProgressButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Login",
            onClick = { isLoading ->
                if (!isLoading) viewModel.login()
            },
            loading = loginResponse is ResponseIo.Loading
        )
    }
}

@Composable
fun BrandImage() {
    Image(
        painter = rememberImagePainter(
            data = R.drawable.ic_launcher_foreground,
            builder = {
                placeholder(R.drawable.ic_launcher_foreground)
            }
        ),
        contentDescription = "Brand Image",
        alignment = Alignment.Center,
        modifier = Modifier
            .height(250.dp)
            .width(250.dp)
    )
}