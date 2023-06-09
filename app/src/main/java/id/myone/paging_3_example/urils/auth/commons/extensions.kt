/**
 * Created by Mahmud on 06/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.paging_3_example.urils.auth.commons

import com.google.firebase.auth.FirebaseUser
import id.myone.paging_3_example.data.local.table.ArticleTable
import id.myone.paging_3_example.data.local.table.FavoriteArticleTable
import id.myone.paging_3_example.urils.auth.model.UserAuthResult


fun FirebaseUser.mapToUserAuthResult(): UserAuthResult {
    return UserAuthResult(
        id = uid,
        name = displayName,
        email = email
    )
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