/**
 * Created by Mahmud on 07/06/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import id.myone.my_news.common.Constraint
import id.myone.my_news.utils.config.RemoteConfigApp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppDataStore(private val context: Context, private val config: RemoteConfigApp) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("app-store")
        private val ARTICLE_LANG_TYPE = stringPreferencesKey("article_lang_type")
        private val USER_APP_TOKEN = stringPreferencesKey("user_app_token")
        private val IS_PASSED_ONBOARD = stringPreferencesKey("is_passed_on_boarding")
    }

    val getLanguage: Flow<String> = context.dataStore.data.map { preferences ->
        val newsDefault = config.getValue(Constraint.Config.newsLanguage).asString()
        preferences[ARTICLE_LANG_TYPE] ?: newsDefault
    }

    val getUserToken: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[USER_APP_TOKEN] ?: ""
    }

    val getIsPassedOnBoarding: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[IS_PASSED_ONBOARD] ?: ""
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

    suspend fun setIsPassedOnBoarding(value: String) {
        context.dataStore.edit { preferences ->
            preferences[IS_PASSED_ONBOARD] =  value
        }
    }
}