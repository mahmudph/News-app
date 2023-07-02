/**
 * Created by Mahmud on 29/06/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.utils.works

import android.content.Context
import androidx.room.withTransaction
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import id.myone.my_news.data.AppRepositoryContract
import id.myone.my_news.data.ResultData
import id.myone.my_news.data.local.PagingExampleDatabase
import id.myone.my_news.data.local.table.ArticleRemoteKeyTable
import id.myone.my_news.utils.notification.NotificationHandler

class SynchronizeNewsWorker(
    params: WorkerParameters,
    context: Context,
    private val notificationHandler: NotificationHandler,
    private val appRepositoryContract: AppRepositoryContract,
    private val database: PagingExampleDatabase,
): CoroutineWorker(context, params) {

    companion object {
        private const val currentPage = 1
    }

    override suspend fun doWork(): Result {
        return try {

            when(val response = appRepositoryContract.getArticleList(currentPage)) {
                is ResultData.Error -> Result.failure()

                is ResultData.Success -> {

                    response.data?.let { articles ->
                        val isReachedEndPage = response.data.isEmpty()
                        val (prevPage,nextPage) = getPage(currentPage, isReachedEndPage)
                        val keys = articles.map { ArticleRemoteKeyTable(prePage = prevPage,nexPage = nextPage) }

                        database.withTransaction {
                            database.articleDao().deleteAllArticles()
                            database.articleRemoteKeyDao().deleteRemoteKeys()

                            database.articleDao().insertArticles(articles)
                            database.articleRemoteKeyDao().addRemoteKeys(keys)
                        }

                        if (!isReachedEndPage) {
                            articles.random().also { data ->
                                val pendingIntent = notificationHandler.createPendingIntent(data.id)
                                val notification = notificationHandler.createNotificationData(
                                    data.title ?: "-",
                                    data.description ?: "",
                                    pendingIntent
                                )
                                notificationHandler.notify(notification)
                            }
                        }
                    }

                    Result.success()
                }
            }

        } catch(e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }

    private fun getPage(currentPage: Int, isEndOfPaginationReached: Boolean): Pair<Int?, Int?> {
        val prevPage = if (currentPage == 1) null else currentPage - 1
        val nextPage = if (isEndOfPaginationReached) null else currentPage + 1

        return Pair(prevPage, nextPage)
    }
}