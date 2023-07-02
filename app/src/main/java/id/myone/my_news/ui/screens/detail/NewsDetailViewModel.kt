/**
 * Created by Mahmud on 30/06/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.ui.screens.detail


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.myone.my_news.data.AppRepositoryContract
import id.myone.my_news.data.ResultData
import id.myone.my_news.data.local.table.ArticleTable
import id.myone.my_news.ui.utils.Event
import id.myone.my_news.utils.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewsDetailViewModel(
    private val repositoryContract: AppRepositoryContract
): ViewModel() {

    private var _result: MutableStateFlow<Event<UIState<ArticleTable>>> = MutableStateFlow((Event(UIState.Initial)))
    val result = _result.asStateFlow()

    fun loadNewsById(news: Int) {
        viewModelScope.launch {
            _result.value = Event(UIState.Loading)
            when(val result = repositoryContract.getArticleById(news)) {
                is ResultData.Success -> {
                    _result.value = Event(UIState.Success(result.data!!))
                }
                is ResultData.Error<*> -> {
                    _result.value = Event(UIState.Error<String>(result.message ?: "failed to load data news"))
                }
            }
        }
    }
}