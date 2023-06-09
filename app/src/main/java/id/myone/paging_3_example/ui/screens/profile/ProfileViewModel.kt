/**
 * Created by Mahmud on 07/06/23.
 * mahmud120398@gmail.com
 */

package id.myone.paging_3_example.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.myone.paging_3_example.urils.AppDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val store: AppDataStore
): ViewModel() {

    var isShowDialogLogout = MutableStateFlow(false)
    fun toggleDialog (value: Boolean) { isShowDialogLogout.value = value }

    fun logout()  {
        viewModelScope.launch {
            store.setUserToken("")
        }
    }
}