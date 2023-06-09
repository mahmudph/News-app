/**
 * Created by Mahmud on 07/06/23.
 * mahmud120398@gmail.com
 */

package id.myone.paging_3_example.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.myone.paging_3_example.urils.AppDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class SettingsViewModel(
    private val store: AppDataStore,
) : ViewModel() {

    var showDialogLanguage = MutableStateFlow(false)
    val currentLanguage = store.getLanguage

    val languagePreferences = listOf(
        mapOf("France" to "ar"),
        mapOf("English" to "de"),
        mapOf("Spain's" to "en"),
        mapOf("English" to "es"),
        mapOf("English" to "fr"),
        mapOf("English" to "he"),
        mapOf("English" to "it"),
        mapOf("English" to "nl"),
        mapOf("English" to "no"),
    )

    fun setLanguage(lang: Map<String, String>) {
        viewModelScope.launch(Dispatchers.Default) {
            store.setLanguage(lang.values.first())
            showDialogLanguage.value = false
        }
    }

}