/**
 * Created by Mahmud on 06/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.utils.auth

import com.google.firebase.auth.*
import id.myone.my_news.utils.auth.commons.mapToUserAuthResult
import id.myone.my_news.utils.auth.model.FirebaseAuthState
import kotlinx.coroutines.tasks.await

interface FirebaseEmailPasswordOAuthContract {
    suspend fun sendEmailVerification(): Boolean
    suspend fun createNewAccount(email: String, password: String, sendEmailVerify: Boolean = true): FirebaseAuthState
    suspend fun loginAuth(email: String, password: String, shouldVerifiedAccount: Boolean = true,): FirebaseAuthState
    suspend fun verifyResetPasswordCode(password: String): Boolean
    suspend fun resetPassword(email: String): String
}

class FirebaseEmailPasswordOAuthImpl(
    private val auth: FirebaseAuth,
) : OAuthContract(auth), FirebaseEmailPasswordOAuthContract {

    override suspend fun createNewAccount(
        email: String,
        password: String,
        sendEmailVerify: Boolean,
    ): FirebaseAuthState {

        return try {

            val result = auth.createUserWithEmailAndPassword(email, password).await()

            if (sendEmailVerify) {

                val isSendEmailVerification = this.sendEmailVerification()
                if (!isSendEmailVerification) {
                    throw Exception("failed to send email verification to the email")
                }

            }

            FirebaseAuthState(isSuccess = true, data = result.user?.mapToUserAuthResult())

        } catch (e: FirebaseAuthWeakPasswordException) {
            FirebaseAuthState(isSuccess = false, message = "Your password looks to weeks")
        } catch(e: FirebaseAuthUserCollisionException) {
            FirebaseAuthState(isSuccess = false, message = "Email is already registered, please use another email")
        } catch (e: Exception) {
            e.printStackTrace()
            FirebaseAuthState(isSuccess = false, message = e.localizedMessage)
        }
    }

    override suspend fun sendEmailVerification(): Boolean {
        return try {
            auth.currentUser?.sendEmailVerification()?.await()
            true
        } catch(e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun loginAuth(
        email: String,
        password: String,
        shouldVerifiedAccount: Boolean,
    ): FirebaseAuthState {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val isVerified = result.user?.isEmailVerified ?: false

            if (shouldVerifiedAccount) {
                if (!isVerified) throw Exception("Please verify an account before login to the app")
            }

            FirebaseAuthState(isSuccess = true, data = result.user?.mapToUserAuthResult())

        } catch(e: FirebaseAuthInvalidCredentialsException) {
            FirebaseAuthState(isSuccess = false, message = "invalid password, please try again")

        } catch (e: Exception) {
            e.printStackTrace()
            FirebaseAuthState(isSuccess = false, message = e.localizedMessage)
        }
    }

    override suspend fun resetPassword(email: String): String {
        return try {
            auth.sendPasswordResetEmail(email).await()
            "Reset Token has been sent to the email"
        } catch (e: FirebaseAuthInvalidUserException) {
            when(e.errorCode) {
                "ERROR_USER_DISABLED" -> "Account has been banned"
                "ERROR_USER_NOT_FOUND" -> "Account not found"
                else -> e.localizedMessage?: "reset password failure please try again"
            }

        } catch(e: FirebaseAuthEmailException) {
            "failed to process request try again"
        } catch(e:Exception) {
            e.printStackTrace()
            "something went wrong, please try again a moment"
        }
    }

    override suspend fun verifyResetPasswordCode(password: String): Boolean {
        return try {
            auth.verifyPasswordResetCode(password).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}