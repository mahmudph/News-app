/**
 * Created by Mahmud on 05/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import id.myone.my_news.common.mapToArticleTable
import id.myone.my_news.data.AppRepositoryContract
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

@OptIn(FlowPreview::class)
class SearchNewsViewModel(
    private val appRepositoryContract: AppRepositoryContract,
) : ViewModel() {

    val queryTemp = MutableStateFlow("")


    @OptIn(ExperimentalCoroutinesApi::class)
    val searchResult = queryTemp
        .debounce(500)
        .distinctUntilChanged()
        .filter { it.trim().isNotEmpty() }
        .flatMapLatest {
            appRepositoryContract.searchArticles(it)
        }.map { pager -> pager.map {  it.mapToArticleTable() } }.cachedIn(viewModelScope)

    fun searchQuery(query: String) {
        queryTemp.value = query
    }
}