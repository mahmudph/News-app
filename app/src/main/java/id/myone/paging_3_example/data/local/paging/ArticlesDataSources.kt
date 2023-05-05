/**
 * Created by Mahmud on 05/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.paging_3_example.data.local.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.myone.paging_3_example.data.remote.PagingServiceApi
import id.myone.paging_3_example.data.remote.model.Article

class ArticlesDataSources(
    private val apiSource: PagingServiceApi,
    private val querySearch: String,
) : PagingSource<Int, Article>() {

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {

            val position = params.key ?: INITIAL_PAGE
            val response = apiSource.searchNews(position, params.loadSize, querySearch)

            LoadResult.Page(
                data = response.articles,
                prevKey = if (position == INITIAL_PAGE) null else position - 1,
                nextKey = if (response.articles.isEmpty()) null else position + 1,
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val INITIAL_PAGE = 1
    }
}