/**
 * Created by Mahmud on 04/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.paging_3_example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.myone.paging_3_example.common.Constraint
import id.myone.paging_3_example.data.local.table.ArticleRemoteKeyTable

@Dao
interface ArticleRemoteKeyDao {

    @Query("select * from ${Constraint.ARTICLE_REMOTE_KEY_TABLE} where id=:id")
    suspend fun getRemoteKeyById(id: Int): ArticleRemoteKeyTable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRemoteKeys(data: List<ArticleRemoteKeyTable>)

    @Query("delete from ${Constraint.ARTICLE_REMOTE_KEY_TABLE}")
    suspend fun deleteRemoteKeys()

}