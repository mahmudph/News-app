/**
 * Created by Mahmud on 06/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.ui.screens.forgot_password

import androidx.compose.runtime.mutableStateOf
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.myone.my_news.common.Constraint
import id.myone.my_news.ui.utils.Event
import id.myone.my_news.utils.UIState
import id.myone.my_news.utils.analytic.Analytic
import id.myone.my_news.utils.auth.FirebaseEmailPasswordOAuthContract
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(
    private val analytic: Analytic,
    private val firebaseEmailPasswordOAuthContract: FirebaseEmailPasswordOAuthContract,
) : ViewModel() {

    private val response = MutableStateFlow<Event<UIState<String>>?>(null)
    val getResponse = response.asStateFlow()

    val email = MutableStateFlow("")
    val isValidEmail = email.map { it != "" }

    val isLoading = mutableStateOf(false)

    fun changePassword() {
        viewModelScope.launch {
            isLoading.value = true
            val result = firebaseEmailPasswordOAuthContract.resetPassword(email.value)

            analytic.log(
                Constraint.Analytic.forgotPassword, bundleOf("email" to email.value)
            )

            isLoading.value = false
            response.value = Event(UIState.Success(result))
        }
    }
}