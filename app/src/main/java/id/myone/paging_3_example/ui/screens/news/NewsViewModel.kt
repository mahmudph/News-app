/**
 * Created by Mahmud on 04/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.paging_3_example.ui.screens.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import id.myone.paging_3_example.data.AppRepositoryContract
import id.myone.paging_3_example.data.ResultData
import id.myone.paging_3_example.data.local.table.ArticleTable
import id.myone.paging_3_example.ui.utils.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NewsViewModel(
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
                        message.value = Event("Berhasil menandai artikel ${article.title}")
                    }
                    is ResultData.Error -> {
                        message.value = Event("Gagal menandai artikel ${article.title}")
                    }
                }
                selectedArticle.value = null
            }
        }
    }
}