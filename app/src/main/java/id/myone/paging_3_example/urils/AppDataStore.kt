/**
 * Created by Mahmud on 07/06/23.
 * mahmud120398@gmail.com
 */

package id.myone.paging_3_example.urils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppDataStore(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("app-store")
        private val ARTICLE_LANG_TYPE = stringPreferencesKey("article_lang_type")
        private val USER_APP_TOKEN = stringPreferencesKey("user_app_token")
    }

    val getLanguage: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[ARTICLE_LANG_TYPE] ?: "en"
    }

    val getUserToken: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[USER_APP_TOKEN] ?: ""
    }

    suspend fun setUserToken(token:String) {
        context.dataStore.edit { preferences ->
            preferences[USER_APP_TOKEN] = token
        }
    }

    suspend fun setLanguage(lang: String) {
        context.dataStore.edit { preferences ->
            preferences[ARTICLE_LANG_TYPE] = lang
        }
    }
}