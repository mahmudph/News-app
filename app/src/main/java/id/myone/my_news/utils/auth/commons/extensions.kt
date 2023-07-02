/**
 * Created by Mahmud on 06/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.utils.auth.commons

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import id.myone.my_news.data.local.table.ArticleTable
import id.myone.my_news.data.local.table.FavoriteArticleTable
import id.myone.my_news.utils.auth.model.UserAuthResult


fun FirebaseUser.mapToUserAuthResult(): UserAuthResult {
    return UserAuthResult(
        id = uid,
        name = displayName,
        email = email,
        avatar = photoUrl?.toString(),
    )
}

fun FirebaseAuth.updateCurrentUserData(name: String, email:String, avatar: Uri): UserAuthResult? {
    return try {
        val profile = UserProfileChangeRequest.Builder().apply {
            if (name.isNotEmpty()) displayName = name
            photoUri = avatar
        }

        currentUser?.also {
            it.updateEmail(email)
            it.updateProfile(profile.build())
        }

        currentUser?.mapToUserAuthResult()

    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}



fun FavoriteArticleTable.mapToArticleTable(): ArticleTable {
    return ArticleTable(
        id = id,
        author =  author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = source,
        title = title,
        url = url,
        urlToImage = urlToImage,
    )
}



fun ArticleTable.mapToFavoriteArticleTable(): FavoriteArticleTable {
    return FavoriteArticleTable(
        id = id,
        author =  author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = source,
        title = title,
        url = url,
        urlToImage = urlToImage,
    )
}