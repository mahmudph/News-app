/**
 * Created by Mahmud on 06/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.utils.auth

import com.google.firebase.auth.FirebaseAuth

abstract class OAuthContract(
    private val auth: FirebaseAuth,
) {

    open fun initialize() {}

}