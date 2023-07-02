/**
 * Created by Mahmud on 21/06/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.ui.screens.profile.update

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import id.myone.my_news.common.Constraint
import id.myone.my_news.ui.utils.Event
import id.myone.my_news.utils.UIState
import id.myone.my_news.utils.auth.commons.mapToUserAuthResult
import id.myone.my_news.utils.auth.commons.updateCurrentUserData
import id.myone.my_news.utils.auth.model.UserAuthResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UpdateProfileViewModel(
    private val firebaseAuth: FirebaseAuth,
) : ViewModel() {

    var userName = MutableStateFlow("")
    var userEmail = MutableStateFlow("")
    var userAvatar = MutableStateFlow<Uri?>(null)

    private var _updateProfileRes = MutableStateFlow<Event<UIState<String>>>(Event(UIState.Initial))
    val updateProfileResult = _updateProfileRes.asStateFlow()

    init {
        viewModelScope.launch {
            val currentUser = firebaseAuth.currentUser?.mapToUserAuthResult()
            currentUser?.let { user ->
               userName.value = user.name ?: "user guest"
                userEmail.value = user.email ?: "guest@gmail.com"
                userAvatar.value = Uri.parse(user.avatar ?: Constraint.defaultAvatarUri)
            }
        }
    }

    fun handleOnUpdateProfile() {
        viewModelScope.launch {

            userAvatar.value?.let { avatar ->
                val result = firebaseAuth.updateCurrentUserData(userName.value, userEmail.value, avatar)
                if (result != null) {
                    _updateProfileRes.value = Event(UIState.Success("Success to update profile data"))
                    return@launch
                }

                _updateProfileRes.value = Event(UIState.Error<String>("failed to update profile data"))
            }
        }
    }
}