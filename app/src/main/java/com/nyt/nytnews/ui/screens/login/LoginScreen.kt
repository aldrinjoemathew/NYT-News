package com.nyt.nytnews.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.nyt.nytnews.R
import com.nyt.nytnews.ui.composables.ProgressButton
import com.nyt.nytnews.ui.navigation.NytNavigationAction
import com.nyt.nytnews.ui.theme.TextSizeNormalPlus
import com.nyt.nytnews.utils.ResponseIo
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navigationAction: NytNavigationAction, viewModel: LoginViewModel) {

    val loginResponse by viewModel.loginResponse.collectAsState()
    val focusManager = LocalFocusManager.current

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Scaffold(
        scaffoldState = scaffoldState
    ) { padding ->
        when (loginResponse) {
            is ResponseIo.Data -> {
                LaunchedEffect(key1 = loginResponse, block = {
                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                    navigationAction.navigateToHome()
                })
            }
            is ResponseIo.Error, is ResponseIo.Loading, ResponseIo.Empty -> {
                LoginView(
                    email = email,
                    password = password,
                    focusManager = focusManager,
                    isLoading = loginResponse is ResponseIo.Loading,
                    onEmailChanged = { email = it },
                    onPasswordChanged = { password = it },
                    isValidEmail = viewModel.isValidEmail(email),
                    isValidPassword = viewModel.isValidPassword(password),
                    onLogin = { viewModel.login(email, password) },
                    onSignup = navigationAction.navigateToSignup
                )
                if (loginResponse is ResponseIo.Error) {
                    (loginResponse as? ResponseIo.Error)?.message?.let { error ->
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
private fun LoginView(
    email: String,
    password: String,
    focusManager: FocusManager,
    isLoading: Boolean,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    isValidEmail: Boolean,
    isValidPassword: Boolean,
    onLogin: () -> Unit,
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
            label = { Text(text = stringResource(R.string.tf_email)) },
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
            label = { Text(text = stringResource(R.string.tf_password)) },
            value = password,
            onValueChange = onPasswordChanged,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password, imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus(true)
                if (!isLoading && isValidEmail && isValidPassword) onLogin()
            }),
            singleLine = true
        )
        Spacer(modifier = Modifier.size(20.dp))
        ProgressButton(
            modifier = Modifier.fillMaxWidth(),
            enabled = isValidEmail && isValidPassword,
            text = stringResource(R.string.btn_action_login),
            onClick = { isLoading ->
                focusManager.clearFocus(true)
                if (!isLoading && isValidEmail && isValidPassword) onLogin()
            },
            loading = isLoading
        )
        Spacer(modifier = Modifier.size(20.dp))
        Text(
            text = stringResource(R.string.action_signup),
            modifier = Modifier.clickable(enabled = !isLoading, onClick = onSignup),
            style = TextStyle(
                textDecoration = TextDecoration.Underline,
                fontSize = TextSizeNormalPlus
            )
        )
    }
}

@Composable
fun BrandImage() {
    Image(
        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current)
                .data(data = R.drawable.ic_launcher_foreground)
                .apply(block = fun ImageRequest.Builder.() {
                    placeholder(R.drawable.ic_launcher_foreground)
                }).build()
        ),
        contentDescription = stringResource(R.string.cd_brand_image),
        alignment = Alignment.Center,
        modifier = Modifier
            .height(250.dp)
            .width(250.dp)
    )
}