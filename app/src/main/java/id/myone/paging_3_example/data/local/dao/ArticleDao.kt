/**
 * Created by Mahmud on 04/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.paging_3_example.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import id.myone.paging_3_example.common.Constraint
import id.myone.paging_3_example.data.local.table.ArticleTable

@Dao
interface ArticleDao {

    @Query("select * from ${Constraint.ARTICLE_TABLE}")
    fun getAllArticles(): PagingSource<Int,ArticleTable>

    @Query("select * from ${Constraint.ARTICLE_TABLE} where id=:newsId")
    suspend fun getArticlesById(newsId: Int): ArticleTable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(news: List<ArticleTable>)

    @Query("delete from ${Constraint.ARTICLE_TABLE}")
    suspend fun deleteAllArticles()
}