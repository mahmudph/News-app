/**
 * Created by Mahmud on 06/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.paging_3_example.urils.auth

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import id.myone.paging_3_example.urils.auth.model.UserAuthResult


interface FirebaseGoogleAuthContract {
    fun launchSignProcessForSelectAccount(launcher: ManagedActivityResultLauncher<Intent, ActivityResult>)
    fun launchForAuthResult(
        idToken: String,
        onSuccess: (user: UserAuthResult) -> Unit,
        onFailure: (e: Throwable) -> Unit,
    )

    fun getSignedInAccountFromIntent(intent: Intent): String?
}

class FirebaseGoogleOAuthImpl(
    private val context: Context,
    private val auth: FirebaseAuth,
) : OAuthContract(auth), FirebaseGoogleAuthContract {

    private var googleSignInClient: GoogleSignInClient? = null

    override fun initialize() {
        val googleSignBuilder = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("")
            .requestEmail().build()

        googleSignInClient = GoogleSignIn.getClient(context, googleSignBuilder)
    }

    override fun launchSignProcessForSelectAccount(launcher: ManagedActivityResultLauncher<Intent, ActivityResult>) {
        if (googleSignInClient?.signInIntent != null) launcher.launch(googleSignInClient!!.signInIntent)
    }

    override fun launchForAuthResult(
        idToken: String,
        onSuccess: (user: UserAuthResult) -> Unit,
        onFailure: (e: Throwable) -> Unit,
    ) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener { result ->
            getTaskAuthResult(result, onSuccess, onFailure)
        }
    }

    override fun getSignedInAccountFromIntent(intent: Intent): String? {
        return try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
            val account = task.getResult(ApiException::class.java)!!
            account.idToken!!
        } catch (e: Exception) {
            Log.w(javaClass.name, "Google sign in failed", e)
            null
        }
    }
}