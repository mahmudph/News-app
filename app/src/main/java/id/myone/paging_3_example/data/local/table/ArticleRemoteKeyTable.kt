package id.myone.paging_3_example.data.local.table

import androidx.room.Entity
import androidx.room.PrimaryKey
import id.myone.paging_3_example.common.Constraint


@Entity(tableName = Constraint.ARTICLE_REMOTE_KEY_TABLE)
data class ArticleRemoteKeyTable(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val prePage: Int?,
    val nexPage: Int?,
)
