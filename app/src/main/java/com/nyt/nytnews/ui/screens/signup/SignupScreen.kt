package com.nyt.nytnews.ui.screens.signup

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.nyt.nytnews.ui.composables.ProgressButton
import com.nyt.nytnews.ui.navigation.NytNavigationAction
import com.nyt.nytnews.ui.screens.login.BrandImage
import com.nyt.nytnews.utils.ResponseIo
import kotlinx.coroutines.launch

@Composable
fun SignupScreen(navigationAction: NytNavigationAction, viewModel: SignupViewModel) {
    val signupResponse by viewModel.signupResponse.collectAsState()
    val focusManager = LocalFocusManager.current

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Scaffold(
        scaffoldState = scaffoldState
    ) { padding ->
        when (signupResponse) {
            is ResponseIo.Data -> {
                scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                navigationAction.navigateToHome()
            }
            is ResponseIo.Error, is ResponseIo.Loading, ResponseIo.Empty -> {
                SignupView(
                    name = name,
                    email = email,
                    password = password,
                    focusManager = focusManager,
                    isLoading = signupResponse is ResponseIo.Loading,
                    onNameChanged = { name = it },
                    onEmailChanged = { email = it },
                    onPasswordChanged = { password = it },
                    isValidName = viewModel.isValidName(name),
                    isValidEmail = viewModel.isValidEmail(email),
                    isValidPassword = viewModel.isValidPassword(password),
                    onSignup = { viewModel.signup(name, email, password) }
                )
                if (signupResponse is ResponseIo.Error) {
                    (signupResponse as? ResponseIo.Error)?.message?.let { error ->
                        LaunchedEffect(key1 = scaffoldState.snackbarHostState, block = {
                            //Dismissing old snackbar instance
                            scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                            coroutineScope.launch {
                                scaffoldState.snackbarHostState
                                    .showSnackbar(
                                        message = error,
                                        actionLabel = "Dismiss",
                                        duration = SnackbarDuration.Long
                                    )
                            }
                        })
                    }
                }
            }
        }
    }
}

@Composable
private fun SignupView(
    name: String,
    email: String,
    password: String,
    focusManager: FocusManager,
    isLoading: Boolean,
    onNameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    isValidName: Boolean,
    isValidEmail: Boolean,
    isValidPassword: Boolean,
    onSignup: () -> Unit
) {
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
            label = { Text(text = "Name") },
            value = name,
            onValueChange = onNameChanged,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email, imeAction = ImeAction.Next,
            ),
            singleLine = true,
        )
        Spacer(modifier = Modifier.size(10.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Email") },
            value = email,
            onValueChange = onEmailChanged,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email, imeAction = ImeAction.Next,
            ),
            singleLine = true,
        )
        Spacer(modifier = Modifier.size(10.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Password") },
            value = password,
            onValueChange = onPasswordChanged,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password, imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus(true)
                if (!isLoading && isValidName && isValidEmail && isValidPassword) onSignup()
            }),
            singleLine = true
        )
        Spacer(modifier = Modifier.size(20.dp))
        ProgressButton(
            modifier = Modifier.fillMaxWidth(),
            enabled = isValidEmail && isValidPassword,
            text = "Signup",
            onClick = { isLoading ->
                focusManager.clearFocus(true)
                if (!isLoading && isValidName && isValidEmail && isValidPassword) onSignup()
            },
            loading = isLoading
        )

    }
}