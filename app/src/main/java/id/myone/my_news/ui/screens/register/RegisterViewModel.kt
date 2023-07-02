/**
 * Created by Mahmud on 06/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.ui.screens.register

import androidx.compose.ui.unit.Constraints
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.myone.my_news.common.Constraint
import id.myone.my_news.ui.utils.Event
import id.myone.my_news.utils.UIState
import id.myone.my_news.utils.auth.FirebaseEmailPasswordOAuthContract
import id.myone.my_news.utils.config.RemoteConfigApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val config: RemoteConfigApp,
    private val firebaseEmailPasswordOAuthContract: FirebaseEmailPasswordOAuthContract
) : ViewModel() {

    var name = MutableStateFlow("")
    var email = MutableStateFlow("")
    var password = MutableStateFlow("")
    var isLoading = MutableStateFlow(false)

    val isValidFormRegister = combine(name,email, password) {name, email, password ->
        name != "" && email != "" && password.length > 6
    }

    private val _registerResult = MutableStateFlow<Event<UIState<String>>?>(null)
    val registerResult: StateFlow<Event<UIState<String>>?> = _registerResult.asStateFlow()

    fun doRegisterProcess() {
        viewModelScope.launch {

            isLoading.value = true
            val shouldSendVerifyEmail = config.getValue(Constraint.Config.sendEmailVerification).asBoolean()

            val response = firebaseEmailPasswordOAuthContract.createNewAccount(
                email = email.value,
                password = password.value,
                sendEmailVerify = shouldSendVerifyEmail,
            )

            if (!response.isSuccess) {
                isLoading.value = false
                _registerResult.value = Event(UIState.Error<String>(response.message ?: "Register failure"))
                return@launch
            }
            _registerResult.value = Event(UIState.Success("Success to register user"))
            isLoading.value = false
        }
    }
}