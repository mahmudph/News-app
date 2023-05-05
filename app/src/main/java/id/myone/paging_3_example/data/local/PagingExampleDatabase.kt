/**
 * Created by Mahmud on 04/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.paging_3_example.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import id.myone.paging_3_example.data.local.dao.ArticleDao
import id.myone.paging_3_example.data.local.dao.ArticleRemoteKeyDao
import id.myone.paging_3_example.data.local.table.ArticleRemoteKeyTable
import id.myone.paging_3_example.data.local.table.ArticleTable


@Database(entities = [ArticleTable::class, ArticleRemoteKeyTable::class], version = 1)
abstract class PagingExampleDatabase: RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun articleRemoteKeyDao(): ArticleRemoteKeyDao
}