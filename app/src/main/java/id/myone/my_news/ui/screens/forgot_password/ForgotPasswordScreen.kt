/**
 * Created by Mahmud on 06/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.ui.screens.forgot_password

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
fun ForgotPasswordScreen(
    modifier: Modifier = Modifier,
    onNavigatePop: VoidCallback,
    onNavigateToResetSuccess: VoidCallback,
    viewModel: ForgotPasswordViewModel = getViewModel(),
) {

    val snackBarHostState = SnackbarHostState()

    val email by viewModel.email.collectAsStateWithLifecycle()

    val isValidEmail by viewModel.isValidEmail.collectAsStateWithLifecycle(initialValue = false)

    val state by viewModel.getResponse.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading

    LaunchedEffect(state) {
        state?.getContentIfNotHandled()?.let {
            when(it) {
                is UIState.Success -> {
                    viewModel.email.value = ""
                    snackBarHostState.showSnackbar(it.data)
                    onNavigateToResetSuccess()
                }
                is UIState.Error<*> -> {
                    snackBarHostState.showSnackbar(it.message)
                }
                else ->{
                    snackBarHostState.showSnackbar("failed to send token reset password")
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            ForgotPasswordContent(
                email = email,
                isValidEmail = isValidEmail,
                modifier = modifier.padding(16.dp),
                onSubmit = viewModel::changePassword,
                onChangeEmail = { viewModel.email.value = it },
                onNavigateBack = onNavigatePop
            )

            if (isLoading) Card(modifier = Modifier.align(Alignment.Center)) {
                CircularProgressIndicator(modifier = Modifier.padding(12.dp))
            }
        }
    }
}