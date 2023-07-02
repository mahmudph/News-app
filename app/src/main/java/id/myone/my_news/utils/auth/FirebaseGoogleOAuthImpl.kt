/**
 * Created by Mahmud on 06/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.utils.auth

import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.GoogleAuthProvider
import id.myone.my_news.R
import id.myone.my_news.utils.auth.commons.mapToUserAuthResult
import id.myone.my_news.utils.auth.model.FirebaseAuthState
import kotlinx.coroutines.tasks.await


interface FirebaseGoogleAuthContract {
    suspend fun launchForAuthResult(idToken: String): FirebaseAuthState
    suspend fun getSignedInAccountFromIntent(intent: Intent): String?
    suspend fun getIntentRequest(): Intent?

}

class FirebaseGoogleOAuthImpl(
    private val context: Context,
    private val auth: FirebaseAuth,
) : OAuthContract(auth), FirebaseGoogleAuthContract {

    private var googleSignInClient: GoogleSignInClient? = null

    init { initialize() }

    override fun initialize() {
        val googleSignBuilder = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.web_client_id))
            .requestProfile()
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(context, googleSignBuilder)
    }

    override suspend fun launchForAuthResult(idToken: String): FirebaseAuthState {
        return try {

            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val result = auth.signInWithCredential(credential).await()

            val userToken = getUserToken()
            FirebaseAuthState(isSuccess = true, data = result.user?.mapToUserAuthResult(), token = userToken)

        } catch(e: FirebaseAuthInvalidUserException) {
            val message = when(e.errorCode) {
                "ERROR_USER_DISABLED" -> "Account has been banned"
                "ERROR_USER_NOT_FOUND" -> "Account not found"
                else -> "Login Failure please try again"
            }
            FirebaseAuthState(isSuccess = false, message = message)
        } catch(e: Exception) {
            e.printStackTrace()
            FirebaseAuthState(isSuccess = false, message = e.localizedMessage)
        }
    }

    override suspend fun getSignedInAccountFromIntent(intent: Intent): String? {
        return try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(intent).await()
            task.idToken
        } catch (e: Exception) {
            Log.w(javaClass.name, "Google sign in failed", e)
            null
        }
    }

    override suspend fun getIntentRequest(): Intent? {
        return googleSignInClient?.signInIntent
    }
}