/**
 * Created by Mahmud on 04/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import id.myone.my_news.common.Constraint
import id.myone.my_news.data.local.table.ArticleTable
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Query("select * from ${Constraint.ARTICLE_TABLE}")
    fun getAllArticles(): PagingSource<Int,ArticleTable>

    @Query("select * from ${Constraint.ARTICLE_TABLE} where id=:newsId")
    suspend fun getArticlesById(newsId: Int): ArticleTable?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(news: List<ArticleTable>)

    @Query("delete from ${Constraint.ARTICLE_TABLE}")
    suspend fun deleteAllArticles()

    @Query("select * from ${Constraint.ARTICLE_TABLE}")
    fun getTopRatedArticles(): Flow<List<ArticleTable>>
}