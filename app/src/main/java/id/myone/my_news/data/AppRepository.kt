/**
 * Created by Mahmud on 04/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import id.myone.my_news.common.Constraint
import id.myone.my_news.common.mapToArticleTable
import id.myone.my_news.data.local.PagingExampleDatabase
import id.myone.my_news.data.local.paging.ArticlesDataSources
import id.myone.my_news.data.local.paging.ArticlesRemoteMediator
import id.myone.my_news.data.local.table.ArticleTable
import id.myone.my_news.data.remote.PagingServiceApi
import id.myone.my_news.data.remote.model.Article
import id.myone.my_news.utils.AppDataStore
import id.myone.my_news.utils.auth.commons.mapToArticleTable
import id.myone.my_news.utils.auth.commons.mapToFavoriteArticleTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

interface AppRepositoryContract {
    suspend fun getArticleList(currentPage: Int): ResultData<List<ArticleTable>>
    suspend fun getArticleById(id: Int): ResultData<ArticleTable>
    fun getArticles(): Flow<PagingData<ArticleTable>>
    fun getTopRateArticles(num: Int = 10): Flow<List<ArticleTable>>
    fun searchArticles(searchQuery: String): Flow<PagingData<Article>>
    fun getFavoriteArticles(): Flow<List<ArticleTable>>
    fun addFavoriteArticle(articleTable: ArticleTable): ResultData<Boolean>
    fun deleteFavoriteArticle(id: Int): ResultData<Boolean>

    fun login(payload: Map<String,Any>): ResultData<String>
    fun register(payload: Map<String,Any>): ResultData<String>

    fun isLoginUser(): Flow<Boolean>
    fun isPassedOnBoarding(): Flow<Boolean>
    suspend fun setIsLoginUser(token: String): Boolean
    suspend fun setIsPassedOnBoarding(value: Boolean): Boolean
}

@OptIn(ExperimentalPagingApi::class)
class AppRepository(
    private val storage: AppDataStore,
    private val remoteService: PagingServiceApi,
    private val localDatabase: PagingExampleDatabase,
) : AppRepositoryContract {


    override suspend fun getArticleList(currentPage: Int): ResultData<List<ArticleTable>> {
        return try {

            val lang = storage.getLanguage.first()
            val response = remoteService.getNews(page = currentPage, pageSize = 50, language = lang)
            ResultData.Success(response.articles.map { it.mapToArticleTable() })

        } catch(e: Exception) {
            e.printStackTrace()
            ResultData.Error(errorMessage = e.localizedMessage)
        }
    }

    override suspend fun getArticleById(id: Int): ResultData<ArticleTable> {
        return try {
            val data = localDatabase.articleDao().getArticlesById(id)

            if (data != null) ResultData.Success(data)
            else ResultData.Error(errorMessage = "Data Article Is Not Found")

        } catch(e: Exception) {
            e.printStackTrace()
            ResultData.Error(errorMessage = e.localizedMessage)
        }
    }


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

    override fun login(payload: Map<String, Any>): ResultData<String> {
        return try {
            ResultData.Success("true")
        } catch (e: Exception) {
            ResultData.Error(errorMessage = e.localizedMessage)
        }
    }

    override fun register(payload: Map<String, Any>): ResultData<String> {
        return try {
            ResultData.Success("true")
        } catch (e: Exception) {
            ResultData.Error(errorMessage = e.localizedMessage)
        }
    }

    override fun isLoginUser(): Flow<Boolean> {
        return storage.getUserToken.map { it != "" }
    }

    override fun isPassedOnBoarding(): Flow<Boolean> {
        return storage.getIsPassedOnBoarding.map { it != "" }
    }

    override suspend fun setIsLoginUser(token: String): Boolean {
        storage.setUserToken(token)
        return true
    }

    override suspend fun setIsPassedOnBoarding(value: Boolean): Boolean {
        storage.setIsPassedOnBoarding(value.toString())
        return true
    }
}