/**
 * Created by Mahmud on 06/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.paging_3_example.ui.screens.login

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.myone.paging_3_example.urils.UIState
import id.myone.paging_3_example.urils.auth.FirebaseEmailPasswordOAuthContract
import id.myone.paging_3_example.urils.auth.FirebaseGoogleAuthContract
import id.myone.paging_3_example.urils.auth.model.UserAuthResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class LoginViewModel(
//    private val firebaseGoogleAuthContract: FirebaseGoogleAuthContract,
//    private val firebaseEmailPasswordOAuthContract: FirebaseEmailPasswordOAuthContract,
) : ViewModel() {

    val email = MutableStateFlow("")
    val password = MutableStateFlow("")

    private val loginResult = MutableStateFlow<UIState<UserAuthResult>>(UIState.Initial)

    val isEnabledLoginButton = email.combine(password) { email, password ->
        email.trim().isNotEmpty() && password.trim().length >= 8
    }
//
//    fun doAuthLoginUserWithEmailPassword() {
//        viewModelScope.launch {
//            firebaseEmailPasswordOAuthContract.loginAuth(email.value, password.value,
//                onSuccess = { user ->
//                    loginResult.value = UIState.Success(user)
//                },
//                onFailure = {
//                    loginResult.value = UIState.Error<String>(
//                        it.message ?: "Failed to login, please try again"
//                    )
//                }
//            )
//        }
//    }
//
//    fun doLaunchForSelectGooogleAccount(launcher: ManagedActivityResultLauncher<Intent, ActivityResult>) {
//        firebaseGoogleAuthContract.launchSignProcessForSelectAccount(launcher)
//    }
//
//    fun doAuthLoginWithGoogleAccount(intent: Intent) {
//        viewModelScope.launch {
//            val idToken = firebaseGoogleAuthContract.getSignedInAccountFromIntent(intent)
//
//            if (idToken != null) {
//                firebaseGoogleAuthContract.launchForAuthResult(idToken,
//                    onSuccess = { user ->
//                        loginResult.value = UIState.Success(user)
//                    },
//                    onFailure = {
//                        loginResult.value = UIState.Error<String>(
//                            it.message ?: "Failed to login, please try again"
//                        )
//                    }
//                )
//            } else {
//                loginResult.value = UIState.Error<String>("Failed to login, please try again")
//            }
//        }
//    }

}