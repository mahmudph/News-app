/**
 * Created by Mahmud on 04/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.ui.screens.news

import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import id.myone.my_news.common.Constraint
import id.myone.my_news.data.AppRepositoryContract
import id.myone.my_news.data.ResultData
import id.myone.my_news.data.local.table.ArticleTable
import id.myone.my_news.ui.utils.Event
import id.myone.my_news.utils.analytic.Analytic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NewsViewModel(
    private val analytic: Analytic,
    private val repositoryContract: AppRepositoryContract,
) : ViewModel() {


    val articleList = repositoryContract.getArticles().cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PagingData.empty())

    val topArticleList = repositoryContract.getTopRateArticles(10)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    var message = MutableStateFlow<Event<String>?>(null)
    var selectedArticle = MutableStateFlow<ArticleTable?>(null)
    var isShowDialog = selectedArticle.map { it != null }

    fun addToFavorite() {
        viewModelScope.launch(Dispatchers.Default) {
            selectedArticle.value?.let { article ->
                when (repositoryContract.addFavoriteArticle(article)) {
                    is ResultData.Success -> {

                        /**
                         * log to analytic
                         */
                        analytic.log(
                            Constraint.Analytic.addToFavorite,
                            bundleOf(
                                "id" to article.id,
                                "title" to article.title,
                            )
                        )

                        message.value = Event("Success to bookmarks ${article.title}")
                    }

                    is ResultData.Error -> {
                        message.value = Event("failure to bookmarks ${article.title}")
                    }
                }
                selectedArticle.value = null
            }
        }
    }
}