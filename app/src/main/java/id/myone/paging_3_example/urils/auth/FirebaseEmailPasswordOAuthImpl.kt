/**
 * Created by Mahmud on 06/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.paging_3_example.urils.auth

import com.google.firebase.auth.FirebaseAuth
import id.myone.paging_3_example.urils.auth.model.UserAuthResult


interface FirebaseEmailPasswordOAuthContract {
    fun createNewAccount(
        email: String,
        password: String,
        onSuccess: (user: UserAuthResult) -> Unit,
        onFailure: (e: Throwable) -> Unit,
    )

    fun loginAuth(
        email: String,
        password: String,
        onSuccess: (user: UserAuthResult) -> Unit,
        onFailure: (e: Throwable) -> Unit,
    )
}

class FirebaseEmailPasswordOAuthImpl(
    private val auth: FirebaseAuth,
) : OAuthContract(auth), FirebaseEmailPasswordOAuthContract {

    override fun createNewAccount(
        email: String,
        password: String,
        onSuccess: (user: UserAuthResult) -> Unit,
        onFailure: (e: Throwable) -> Unit,
    ) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { result ->
            getTaskAuthResult(result, onSuccess, onFailure)
        }
    }

    override fun loginAuth(
        email: String,
        password: String,
        onSuccess: (user: UserAuthResult) -> Unit,
        onFailure: (e: Throwable) -> Unit,
    ) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { result ->
            getTaskAuthResult(result, onSuccess, onFailure)
        }
    }
}