/**
 * Created by Mahmud on 25/06/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import id.myone.my_news.data.AppRepositoryContract
import id.myone.my_news.ui.route.RouteGraph
import id.myone.my_news.ui.route.RouteName
import kotlinx.coroutines.launch

class SplashViewModel(
    private val firebaseAuth: FirebaseAuth,
    private val appRepositoryContract: AppRepositoryContract,
) : ViewModel() {

    private val _isLoading = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val _startGraphDestination: MutableState<String?> = mutableStateOf(null)
    val startGraphDestination: State<String?> = _startGraphDestination

    private val _startAuthDestination: MutableState<String?> = mutableStateOf(RouteName.login)
    val startAuthDestination = _startAuthDestination

    init {
        viewModelScope.launch {
            appRepositoryContract.isPassedOnBoarding().collect { isPassed ->
                if (isPassed) {
                    if (firebaseAuth.currentUser != null) {
                        _startGraphDestination.value = RouteName.home
                    } else {
                        _startGraphDestination.value = RouteGraph.auth
                    }
                } else {
                    _startGraphDestination.value = RouteGraph.auth
                    _startAuthDestination.value = RouteName.onBoarding
                }
            }
            _isLoading.value = false
        }
    }
}