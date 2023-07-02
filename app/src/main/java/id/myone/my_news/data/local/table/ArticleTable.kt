package id.myone.my_news.data.local.table

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import id.myone.my_news.data.remote.model.Source
import kotlinx.parcelize.Parcelize
import id.myone.my_news.common.Constraint

@Parcelize
@Entity(tableName = Constraint.ARTICLE_TABLE)
data class ArticleTable(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val author: String?,
    val content: String?,
    val description: String?,

    @ColumnInfo(name = "publish_at")
    val publishedAt: String?,

    @Embedded(prefix = "arc_")
    val source: Source?,
    val title: String?,
    val url: String?,

    @ColumnInfo(name="url_to_image")
    val urlToImage: String?,
): Parcelable
