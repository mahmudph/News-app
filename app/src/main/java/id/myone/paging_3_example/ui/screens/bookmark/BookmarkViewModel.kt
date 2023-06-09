/**
 * Created by Mahmud on 05/06/23.
 * mahmud120398@gmail.com
 */

package id.myone.paging_3_example.ui.screens.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.myone.paging_3_example.data.AppRepositoryContract
import id.myone.paging_3_example.data.ResultData
import id.myone.paging_3_example.data.local.table.ArticleTable
import id.myone.paging_3_example.ui.utils.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class BookmarkViewModel(
    private val repositoryContract: AppRepositoryContract,
) : ViewModel() {

    val favoriteArticle: StateFlow<List<ArticleTable>> = repositoryContract.getFavoriteArticles()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val message = MutableStateFlow<Event<String>?>(null)
    val selectedArticle = MutableStateFlow<ArticleTable?>(null)
    val isShowBottomSheet = selectedArticle.map { it != null }

    fun deleteBookmark() {
        viewModelScope.launch(Dispatchers.Default) {
            selectedArticle.value?.let { article ->
                when (repositoryContract.deleteFavoriteArticle(article.id)) {
                    is ResultData.Success -> {
                        message.value = Event("Berhasil menghapus penanda ${article.title}")
                    }
                    is ResultData.Error -> {
                        message.value = Event("Gagal menghapus penanda ${article.title}")
                    }
                }
                selectedArticle.value = null
            }
        }
    }
}