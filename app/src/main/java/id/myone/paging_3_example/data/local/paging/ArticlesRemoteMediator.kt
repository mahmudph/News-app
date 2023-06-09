/**
 * Created by Mahmud on 04/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.paging_3_example.data.local.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import id.myone.paging_3_example.data.local.PagingExampleDatabase
import id.myone.paging_3_example.data.local.table.ArticleRemoteKeyTable
import id.myone.paging_3_example.data.local.table.ArticleTable
import id.myone.paging_3_example.data.remote.PagingServiceApi
import id.myone.paging_3_example.urils.AppDataStore
import kotlinx.coroutines.flow.first

@OptIn(ExperimentalPagingApi::class)
class ArticlesRemoteMediator(
    private val storage: AppDataStore,
    private val remoteService: PagingServiceApi,
    private val localDatabase: PagingExampleDatabase,
) : RemoteMediator<Int, ArticleTable>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleTable>,
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nexPage?.minus(1) ?: 1
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val prePage = remoteKeys?.nexPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    prePage
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val nextPage = remoteKeys?.prePage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val lang = storage.getLanguage.first()
            val response = remoteService.getNews(currentPage, state.config.pageSize, lang)
            val isEndOfPaginationReached = response.articles.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (isEndOfPaginationReached) null else currentPage + 1

            localDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    localDatabase.articleDao().deleteAllArticles()
                    localDatabase.articleRemoteKeyDao().deleteRemoteKeys()
                }

                val keys = response.articles.map {
                    ArticleRemoteKeyTable(
                        prePage = prevPage,
                        nexPage = nextPage
                    )
                }

                val articles = response.articles.map {
                    ArticleTable(
                        author = it.author,
                        content = it.content,
                        description = it.description,
                        publishedAt = it.publishedAt,
                        source = it.source,
                        title = it.title,
                        url = it.url,
                        urlToImage = it.urlToImage,
                    )
                }

                localDatabase.articleDao().insertArticles(articles)
                localDatabase.articleRemoteKeyDao().addRemoteKeys(keys)

            }

            MediatorResult.Success(endOfPaginationReached = isEndOfPaginationReached)

        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }


    override suspend fun initialize(): InitializeAction {
        return InitializeAction.SKIP_INITIAL_REFRESH
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, ArticleTable>,
    ): ArticleRemoteKeyTable? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                localDatabase.articleRemoteKeyDao().getRemoteKeyById(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, ArticleTable>,
    ): ArticleRemoteKeyTable? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { article ->
                localDatabase.articleRemoteKeyDao().getRemoteKeyById(article.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, ArticleTable>,
    ): ArticleRemoteKeyTable? {

        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { article ->
                localDatabase.articleRemoteKeyDao().getRemoteKeyById(article.id)
            }
    }
}