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
import id.myone.paging_3_example.urils.AppDataStore
import id.myone.paging_3_example.urils.auth.commons.mapToArticleTable
import id.myone.paging_3_example.urils.auth.commons.mapToFavoriteArticleTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface AppRepositoryContract {
    fun getArticles(): Flow<PagingData<ArticleTable>>
    fun getTopRateArticles(num: Int = 10): Flow<List<ArticleTable>>
    fun searchArticles(searchQuery: String): Flow<PagingData<Article>>
    fun getFavoriteArticles(): Flow<List<ArticleTable>>
    fun addFavoriteArticle(articleTable: ArticleTable): ResultData<Boolean>
    fun deleteFavoriteArticle(id: Int): ResultData<Boolean>
}


@OptIn(ExperimentalPagingApi::class)
class AppRepository(
    private val storage: AppDataStore,
    private val remoteService: PagingServiceApi,
    private val localDatabase: PagingExampleDatabase,
) : AppRepositoryContract {


    override fun getArticles(): Flow<PagingData<ArticleTable>> {
        val pagingSourceFactory = { localDatabase.articleDao().getAllArticles() }
        return Pager(
            config = PagingConfig(pageSize = Constraint.PAGER_SIZE),
            remoteMediator = ArticlesRemoteMediator(
                storage = storage,
                remoteService = remoteService,
                localDatabase = localDatabase,
            ),
            pagingSourceFactory = pagingSourceFactory,
        ).flow
    }

    override fun getTopRateArticles(num: Int): Flow<List<ArticleTable>> {
        return localDatabase.articleDao().getTopRatedArticles().map { articleFlow ->
            articleFlow.filter { article ->
                article.title?.isNotEmpty() == true
            }.take(10)
        }
    }

    override fun searchArticles(searchQuery: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                ArticlesDataSources(storage, remoteService, searchQuery)
            }
        ).flow
    }

    override fun getFavoriteArticles(): Flow<List<ArticleTable>> {
        return localDatabase.favoriteArticleDao().getFavoriteData().map {
            it.map { article -> article.mapToArticleTable() }
        }
    }

    override fun addFavoriteArticle(articleTable: ArticleTable): ResultData<Boolean> {
        return try {

            val favoriteArticleTable = articleTable.mapToFavoriteArticleTable()

            localDatabase.favoriteArticleDao().insertFavorite(favoriteArticleTable)
            ResultData.Success(true)

        } catch (e: Exception) {
            ResultData.Error(errorMessage = e.localizedMessage)
        }
    }

    override fun deleteFavoriteArticle(id: Int): ResultData<Boolean> {
        return try {

            localDatabase.favoriteArticleDao().deleteFavorite(id)
            ResultData.Success(true)

        } catch (e: Exception) {
            ResultData.Error(errorMessage = e.localizedMessage)
        }
    }
}