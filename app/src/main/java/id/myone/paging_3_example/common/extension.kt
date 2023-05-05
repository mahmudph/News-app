/**
 * Created by Mahmud on 05/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.paging_3_example.common

import id.myone.paging_3_example.data.local.table.ArticleTable
import id.myone.paging_3_example.data.remote.model.Article
import id.myone.paging_3_example.data.remote.model.Source
import java.text.SimpleDateFormat
import java.util.*

fun String.formatToLocalDate(): String {
    val formatDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    formatDate.timeZone = TimeZone.getTimeZone("UTC")

    val inputDate = formatDate.parse(this)!!
    val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return outputFormat.format(inputDate)
}

fun Article.mapToArticleTable(): ArticleTable {
    return ArticleTable(
        id = 0,
        title = title,
        description = description,
        content = content,
        publishedAt = publishedAt,
        urlToImage = urlToImage,
        url = url,
        source = Source(
            id =  source.id,
            name = source.name
        ),
        author = author
    )
}