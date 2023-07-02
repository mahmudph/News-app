/**
 * Created by Mahmud on 06/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.ui.screens.register

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.myone.my_news.common.VoidCallback
import id.myone.my_news.utils.UIState
import org.koin.androidx.compose.getViewModel

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    onNavigateToLogin: VoidCallback,
    snackBarHostState: SnackbarHostState = SnackbarHostState(),
    viewModel: RegisterViewModel = getViewModel(),
) {

    val name by viewModel.name.collectAsStateWithLifecycle()
    val email by viewModel.email.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()

    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val isValidForm by viewModel.isValidFormRegister.collectAsStateWithLifecycle(false)

    val registerResult by viewModel.registerResult.collectAsStateWithLifecycle()

    LaunchedEffect(registerResult) {
        registerResult?.getContentIfNotHandled()?.let { uiState ->
            when (uiState) {
                is UIState.Success -> {
                    snackBarHostState.showSnackbar(uiState.data)
                    onNavigateToLogin()
                }
                is UIState.Error<*> -> {
                    snackBarHostState.showSnackbar(uiState.message)
                }
                else -> {}
            }
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(hostState = snackBarHostState) }) {
        Box(modifier = modifier.padding(16.dp), contentAlignment = Alignment.Center) {
            RegisterContent(
                name = name,
                email = email,
                password = password,
                isEnabledButton = isValidForm,
                onChangeName = { viewModel.name.value = it },
                onChangeEmail = { viewModel.email.value = it },
                onChangePassword = { viewModel.password.value = it },
                onRegister = viewModel::doRegisterProcess,
                onNavigateToLogin = onNavigateToLogin
            )

            if (isLoading) Card(modifier = Modifier.align(Alignment.Center)) {
                CircularProgressIndicator(modifier = Modifier.padding(12.dp))
            }
        }
    }
}