/**
 * Created by Mahmud on 07/06/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.ui.screens.settings

import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.myone.my_news.common.Constraint
import id.myone.my_news.utils.AppDataStore
import id.myone.my_news.utils.analytic.Analytic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class SettingsViewModel(
    private val analytic: Analytic,
    private val store: AppDataStore,
) : ViewModel() {

    var showDialogLanguage = MutableStateFlow(false)
    val currentLanguage = store.getLanguage

    val languagePreferences = listOf(
        mapOf("France" to "ar"),
        mapOf("English" to "en"),
        mapOf("Indonesia" to "id"),
        mapOf("Malaysia" to "ms"),
        mapOf("Spains" to "es"),
        mapOf("Rusia" to "ru"),
    )

    fun setLanguage(lang: Map<String, String>) {
        viewModelScope.launch(Dispatchers.Default) {
            store.setLanguage(lang.values.first())

            analytic.log(Constraint.Analytic.changeNewsLanguage, bundleOf("language" to lang.values))
            showDialogLanguage.value = false
        }
    }

}