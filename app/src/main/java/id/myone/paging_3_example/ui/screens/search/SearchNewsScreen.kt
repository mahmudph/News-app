/**
 * Created by Mahmud on 05/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.paging_3_example.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import id.myone.paging_3_example.R
import id.myone.paging_3_example.data.local.table.ArticleTable
import id.myone.paging_3_example.ui.componens.InformationComponent
import id.myone.paging_3_example.ui.componens.NewsItemComponent
import id.myone.paging_3_example.ui.componens.SearchNewsComponent
import org.koin.androidx.compose.getViewModel

typealias VoidCallback = () -> Unit

@Composable
fun SearchNewsScreen(
    modifier: Modifier = Modifier,
    onNavigateToDetailPage: (ArticleTable) -> Unit,
    onNavigatePop: VoidCallback,
    viewModel: SearchNewsViewModel = getViewModel(),
) {

    val articles = viewModel.searchResult.collectAsLazyPagingItems()
    val query by viewModel.queryTemp.collectAsState()

    Scaffold(
        topBar = {
            Box(modifier = Modifier.background(MaterialTheme.colorScheme.primary)) {
                SearchNewsComponent(
                    onPressBack = onNavigatePop,
                    onChangeText = { viewModel.searchQuery(it) },
                    placeholder = stringResource(id = R.string.search_placheolder),
                    value = query
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            when (articles.loadState.refresh) {
                is LoadState.Error -> {
                    InformationComponent(
                        title = stringResource(id = R.string.error_title),
                        description = stringResource(id = R.string.error_description),
                    )
                }
                else -> {
                    LazyColumn(modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)) {

                        items(count = articles.itemCount,
                            key = { index -> index },
                            itemContent = { index ->
                                articles[index]?.let { article ->
                                    NewsItemComponent(
                                        article = article,
                                        onPress = {
                                            onNavigateToDetailPage(article)
                                        },
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}


