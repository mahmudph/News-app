/**
 * Created by Mahmud on 05/06/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.data.local.dao

import androidx.room.*
import id.myone.my_news.common.Constraint
import id.myone.my_news.data.local.table.FavoriteArticleTable
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteArticlesDao {

    @Query("SELECT * FROM ${Constraint.FAVORITE_ARTICLE_TABLE}")
    fun getFavoriteData(): Flow<List<FavoriteArticleTable>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(data: FavoriteArticleTable)

    @Query("DELETE FROM ${Constraint.FAVORITE_ARTICLE_TABLE} WHERE id=:id")
    fun deleteFavorite(id: Int)

}