package id.myone.my_news.utils.auth.model

data class FirebaseAuthState(
    val isSuccess: Boolean = false,
    val message: String? = null,
    val data: UserAuthResult? = null,
    val token: String? = null,
)
