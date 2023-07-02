/**
 * Created by Mahmud on 06/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.ui.screens.login


import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.myone.my_news.common.Constraint
import id.myone.my_news.data.AppRepositoryContract
import id.myone.my_news.ui.utils.Event
import id.myone.my_news.utils.UIState
import id.myone.my_news.utils.analytic.Analytic
import id.myone.my_news.utils.auth.FirebaseEmailPasswordOAuthContract
import id.myone.my_news.utils.auth.model.UserAuthResult
import id.myone.my_news.utils.config.RemoteConfigApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class LoginViewModel(
    private val analytic: Analytic,
    private val config: RemoteConfigApp,
    private val appRepositoryContract: AppRepositoryContract,
    private val firebaseEmailPasswordOAuthContract: FirebaseEmailPasswordOAuthContract,
) : ViewModel() {

    val email = MutableStateFlow("")
    val password = MutableStateFlow("")
    val isLoading = MutableStateFlow(false)

    val loginResult = MutableStateFlow<Event<UIState<String>>>(Event(UIState.Initial))

    val isEnabledLoginButton = email.combine(password) { email, password ->
        email.trim().isNotEmpty() && password.trim().length >= 8
    }

    fun setLoginUser(user: UserAuthResult) {
        viewModelScope.launch {

            loginResult.value = Event(UIState.Success("Login is success"))
            appRepositoryContract.setIsLoginUser(user.id)


            analytic.setUserId(user.id)
            analytic.log(Constraint.Analytic.login,
                bundleOf("type" to Constraint.Analytic.googleSignIn)
            )
        }
    }

    fun loginWithCredential() {
        viewModelScope.launch {

            isLoading.value = true
            val shouldVerifiedAccount = config.getValue(Constraint.Config.shouldVerifiedAccount)

            val response = firebaseEmailPasswordOAuthContract.loginAuth(
                email = email.value,
                password = password.value,
                shouldVerifiedAccount = shouldVerifiedAccount.asBoolean()
            )

            if (!response.isSuccess) {
                isLoading.value = false
                loginResult.value = Event(UIState.Error<String>(
                    response.message ?: "Failed to login")
                )
                return@launch
            }

            response.data?.let {
                analytic.setUserId(it.id)

                analytic.log(
                    Constraint.Analytic.login,
                    bundleOf("type" to Constraint.Analytic.emailPasswordCredential)
                )

                appRepositoryContract.setIsLoginUser(it.id)
            }

            loginResult.value = Event(UIState.Success("Login successfully"))
            isLoading.value = false
        }
    }
}
