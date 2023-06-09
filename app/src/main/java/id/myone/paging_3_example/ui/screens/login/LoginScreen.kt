/**
 * Created by Mahmud on 06/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.paging_3_example.ui.screens.login

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.myone.paging_3_example.common.VoidCallback
import org.koin.androidx.compose.getViewModel


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onNavigateToRegisterScreen: VoidCallback,
    onNavigateToResetPasswordScreen: VoidCallback,
    onNavigateToHomeScreen: VoidCallback,
    viewModel: LoginViewModel = getViewModel(),
) {
    /**
     * handle for login with google
     */
    val activity = LocalContext.current as Activity
    val resultLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()
        ) { result ->
//            if (result.resultCode == Activity.RESULT_OK) {
//                viewModel.doAuthLoginWithGoogleAccount(result.data!!)
//            }
        }


    val email by viewModel.email.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()
    val isEnabledButton by viewModel.isEnabledLoginButton.collectAsStateWithLifecycle(false)

    BackHandler {
        activity.finish()
    }

    Scaffold{ paddingValues ->
        Box(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

            LoginContent(
                email = email,
                password = password,
                isEnabledButton = isEnabledButton,
                onChangeEmail = {
                    viewModel.email.value = it
                },
                onChangePassword = {
                    viewModel.password.value = it
                },
                onPressLoginWithCredential = {
//                    viewModel.doAuthLoginUserWithEmailPassword()
                },
                onPressLoginWithGoogleAccount = {
//                    viewModel.doLaunchForSelectGooogleAccount(launcher = resultLauncher)
                },
                onNavigateToRegister = onNavigateToRegisterScreen,
                onNavigateToForgotPassword = onNavigateToResetPasswordScreen
            )
        }
    }
}