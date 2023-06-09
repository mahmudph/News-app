/**
 * Created by Mahmud on 05/06/23.
 * mahmud120398@gmail.com
 */

package id.myone.paging_3_example.data.local.dao

import androidx.room.*
import id.myone.paging_3_example.common.Constraint
import id.myone.paging_3_example.data.local.table.FavoriteArticleTable
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