/**
 * Created by Mahmud on 06/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.utils.auth

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

abstract class OAuthContract(
    private val auth: FirebaseAuth,
) {

    open fun initialize() {}

    open suspend fun getUserToken(forceRefresh: Boolean = true): String {
        val result = auth.currentUser?.getIdToken(forceRefresh)?.await() ?: throw Exception("failed to authenticate ")
        if (result.token == null) throw Exception("failed to authenticate request")
        return result.token!!;
    }

}