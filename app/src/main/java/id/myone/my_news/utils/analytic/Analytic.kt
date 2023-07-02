/**
 * Created by Mahmud on 29/06/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.utils.analytic

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Analytic(private val firebaseAnalytics: FirebaseAnalytics) {

    fun setUserId(userId: String){
        firebaseAnalytics.setUserId(userId)
    }

    fun log(key: String, bundle: Bundle) {
        bundle.apply { putString("created_at", getLocalDate()) }
        firebaseAnalytics.logEvent(key, bundle)
    }

    private fun getLocalDate(): String {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
        return LocalDateTime.now().format(formatter)
    }
}