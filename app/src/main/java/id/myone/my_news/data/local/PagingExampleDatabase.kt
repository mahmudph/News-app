/**
 * Created by Mahmud on 04/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import id.myone.my_news.data.local.dao.ArticleDao
import id.myone.my_news.data.local.dao.ArticleRemoteKeyDao
import id.myone.my_news.data.local.dao.FavoriteArticlesDao
import id.myone.my_news.data.local.table.ArticleRemoteKeyTable
import id.myone.my_news.data.local.table.ArticleTable
import id.myone.my_news.data.local.table.FavoriteArticleTable


@Database(entities = [
    ArticleTable::class,
    ArticleRemoteKeyTable::class,
    FavoriteArticleTable::class
],
    version = 2
)
abstract class PagingExampleDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun articleRemoteKeyDao(): ArticleRemoteKeyDao
    abstract fun favoriteArticleDao(): FavoriteArticlesDao
}