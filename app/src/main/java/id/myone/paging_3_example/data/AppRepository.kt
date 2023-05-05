/**
 * Created by Mahmud on 04/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.paging_3_example.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import id.myone.paging_3_example.common.Constraint
import id.myone.paging_3_example.data.local.PagingExampleDatabase
import id.myone.paging_3_example.data.local.paging.ArticlesDataSources
import id.myone.paging_3_example.data.local.paging.ArticlesRemoteMediator
import id.myone.paging_3_example.data.local.table.ArticleTable
import id.myone.paging_3_example.data.remote.PagingServiceApi
import id.myone.paging_3_example.data.remote.model.Article
import kotlinx.coroutines.flow.Flow

interface AppRepositoryContract {
    fun getArticles(): Flow<PagingData<ArticleTable>>
    fun searchArticles(searchQuery: String): Flow<PagingData<Article>>
}


@OptIn(ExperimentalPagingApi::class)
class AppRepository(
    private val remoteService: PagingServiceApi,
    private val localDatabase: PagingExampleDatabase,
) : AppRepositoryContract {


    override fun getArticles(): Flow<PagingData<ArticleTable>> {
        val pagingSourceFactory = { localDatabase.articleDao().getAllArticles() }
        return Pager(
            config = PagingConfig(pageSize = Constraint.PAGER_SIZE),
            remoteMediator = ArticlesRemoteMediator(
                remoteService = remoteService,
                localDatabase = localDatabase,
            ),
            pagingSourceFactory = pagingSourceFactory,
        ).flow
    }

    override fun searchArticles(searchQuery: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                ArticlesDataSources(remoteService, searchQuery)
            }
        ).flow
    }
}