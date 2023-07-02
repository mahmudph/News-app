/**
 * Created by Mahmud on 07/06/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.ui.screens.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import id.myone.my_news.ui.utils.Event
import id.myone.my_news.utils.AppDataStore
import id.myone.my_news.utils.UIState
import id.myone.my_news.utils.auth.commons.mapToUserAuthResult
import id.myone.my_news.utils.auth.model.UserAuthResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val firebaseAuth: FirebaseAuth,
    private val store: AppDataStore,
) : ViewModel() {

    var isShowDialogLogout = MutableStateFlow(false)
    private var _profileData = MutableStateFlow<Event<UIState<UserAuthResult>>>(Event(UIState.Initial))
    val profileData = _profileData.asStateFlow()

    fun toggleDialog(value: Boolean) {
        isShowDialogLogout.value = value
    }

    fun addListener() = firebaseAuth.addAuthStateListener {
        val currentUser = firebaseAuth.currentUser?.mapToUserAuthResult()
        currentUser?.let { user ->
            _profileData.value = Event(UIState.Success(user))
        }
    }

    fun removeListener() = firebaseAuth.removeAuthStateListener {
        Log.i("ProfileViewModel", "remove listener")
    }

    fun logout() {
        viewModelScope.launch {
            firebaseAuth.signOut()
            store.setUserToken("")
        }
    }
}