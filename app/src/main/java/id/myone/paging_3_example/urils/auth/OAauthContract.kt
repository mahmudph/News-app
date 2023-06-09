/**
 * Created by Mahmud on 06/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.paging_3_example.urils.auth

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.internal.IdTokenListener
import id.myone.paging_3_example.urils.auth.commons.mapToUserAuthResult
import id.myone.paging_3_example.urils.auth.model.UserAuthResult

abstract class OAuthContract(
    private val auth: FirebaseAuth,
) {
    open fun initialize() {}

    protected open fun getTaskAuthResult(
        result: Task<AuthResult>, onSuccess: (user: UserAuthResult) -> Unit,
        onFailure: (e: Throwable) -> Unit,
    ) {
        try {
            val user = result.result?.user?.mapToUserAuthResult()

            if (result.isSuccessful && user != null) {
                Log.i(this.javaClass.name, "task is success being executed")
                onSuccess(user)
            } else {
                Log.d(this.javaClass.name, result.exception.toString())
                onFailure(result.exception ?: Throwable("task is failed to be executed"))
            }
        } catch (e: FirebaseAuthUserCollisionException) {
            Log.d(this.javaClass.name, e.toString())
            onFailure(Throwable(handleOnErrorException(e)))

        } catch (e: Exception) {
            Log.d(this.javaClass.name, e.toString())
            onFailure(e)
        }
    }

    private fun handleOnErrorException(e: FirebaseAuthException): String {
        return when (e.errorCode) {
            "ERROR_EMAIL_ALREADY_IN_USE" -> "email is alrady in use"
            "ERROR_CREDENTIAL_ALREADY_IN_USE" -> "this credential already in use"
            "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL" -> "email already registered"
            else -> "process failure please try again"
        }
    }

    fun getCurrentUser(): UserAuthResult? {
        return auth.currentUser?.mapToUserAuthResult()
    }

    fun logOut() {
        if (auth.currentUser != null) auth.signOut()
    }

    fun updateUser(onTokenChangeListener: (token: String) -> Unit) {
        auth.addIdTokenListener(IdTokenListener {
            it.token?.let { token -> onTokenChangeListener(token) }
        })
    }
}