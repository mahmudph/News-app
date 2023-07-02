/**
 * Created by Mahmud on 06/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.ui.screens.login

import android.app.Activity
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.myone.my_news.common.VoidCallback
import id.myone.my_news.utils.UIState
import id.myone.my_news.utils.auth.FirebaseGoogleAuthContract
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getViewModel


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit,
    onNavigateToRegisterScreen: VoidCallback,
    onNavigateToResetPasswordScreen: VoidCallback,
    snackBarHostState: SnackbarHostState = SnackbarHostState(),
    viewModel: LoginViewModel = getViewModel(),
) {
    /**
     * handle for login with google
     */

    val firebaseGoogleSign: FirebaseGoogleAuthContract = get()

    val activity = LocalContext.current as Activity
    val scope = rememberCoroutineScope()

    val email by viewModel.email.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()


    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val loginResult by viewModel.loginResult.collectAsStateWithLifecycle()

    val isEnabledButton by viewModel.isEnabledLoginButton.collectAsStateWithLifecycle(false)

    BackHandler { activity.finish() }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->

            if (result.resultCode == Activity.RESULT_OK) {
                scope.launch {
                    val response = firebaseGoogleSign.getSignedInAccountFromIntent(
                        result.data ?: return@launch
                    )
                    val userData = firebaseGoogleSign.launchForAuthResult(response ?: return@launch)
                    userData.data?.let { viewModel.setLoginUser(it) }
                    Log.d("RESULT", response.toString())
                }
            }
        }
    )


    LaunchedEffect(loginResult) {
        loginResult.getContentIfNotHandled()?.let { uiState ->
            when (uiState) {
                is UIState.Success -> {
                    navigateToHome()
                    snackBarHostState.showSnackbar(uiState.data)
                }
                is UIState.Error<*> -> {
                    snackBarHostState.showSnackbar(uiState.message)
                }
                else -> {}
            }
        }
    }


    Scaffold(snackbarHost = { SnackbarHost(hostState = snackBarHostState) }) { paddingValues ->
        Box(
            modifier = modifier.padding(paddingValues).fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {

            LoginContent(
                email = email,
                password = password,
                isEnabledButton = isEnabledButton,
                onChangeEmail = { viewModel.email.value = it },
                onChangePassword = { viewModel.password.value = it },
                onPressLoginWithCredential = {
                    scope.launch { viewModel.loginWithCredential() }
                },
                onPressLoginWithGoogleAccount = {
                    scope.launch {
                        val googleIntent = firebaseGoogleSign.getIntentRequest()
                        googleIntent?.let { intentRequest ->
                            launcher.launch(intentRequest)
                        }
                    }

                },
                onNavigateToRegister = onNavigateToRegisterScreen,
                onNavigateToForgotPassword = onNavigateToResetPasswordScreen
            )

            if (isLoading) Card(modifier = Modifier.align(Alignment.Center)) {
                CircularProgressIndicator(modifier = Modifier.padding(12.dp))
            }
        }
    }
}