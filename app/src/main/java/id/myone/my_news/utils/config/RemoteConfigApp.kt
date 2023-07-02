/**
 * Created by Mahmud on 29/06/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.utils.config

import android.util.Log
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.google.firebase.remoteconfig.FirebaseRemoteConfigValue
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import id.myone.my_news.R
import kotlinx.coroutines.tasks.await

class RemoteConfigApp(
    private val firebaseRemoteConfig: FirebaseRemoteConfig,
) {

    init {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = MINIMUM_FETCH_CONFIG
        }

        firebaseRemoteConfig.setConfigSettingsAsync(configSettings)
        firebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
    }


    fun getValue(key: String): FirebaseRemoteConfigValue {
        return firebaseRemoteConfig.getValue(key)
    }

    suspend fun reloadConfig(): Boolean {
        return try {
            firebaseRemoteConfig.fetchAndActivate().await()
        } catch(e: Exception) {
            e.printStackTrace()
            false
        }
    }


    fun listenForConfigChanges() {
        firebaseRemoteConfig.addOnConfigUpdateListener(object : ConfigUpdateListener {
            override fun onUpdate(configUpdate: ConfigUpdate) {
                Log.d(TAG, "Updated keys: " + configUpdate.updatedKeys);
                firebaseRemoteConfig.activate().addOnCompleteListener {
                    Log.i(TAG, "config already updated with the latest version")
                }
            }

            override fun onError(error: FirebaseRemoteConfigException) {
                Log.w(TAG, "Config update error with code: " + error.code, error)
            }
        })
    }

    companion object {
        private const val MINIMUM_FETCH_CONFIG = 7200L
        private const val TAG = "RemoteConfigApp"
    }

}