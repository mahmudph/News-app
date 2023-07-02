/**
 * Created by Mahmud on 05/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.ui.screens.search


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import id.myone.my_news.R
import id.myone.my_news.data.local.table.ArticleTable
import id.myone.my_news.ui.componens.InformationComponent
import id.myone.my_news.ui.componens.NewsItemComponent
import id.myone.my_news.ui.componens.SearchNewsComponent
import org.koin.androidx.compose.getViewModel

typealias VoidCallback = () -> Unit

@OptIn(ExperimentalFoundationApi::class)
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
                    placeholder = R.string.search_placheolder,
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

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(count = articles.itemCount,
                    key = { index -> index },
                    itemContent = { index ->
                        articles[index]?.let { article ->
                            NewsItemComponent(
                                article = article,
                                modifier = Modifier.combinedClickable(
                                    onClick = {
                                        onNavigateToDetailPage(article)
                                    },
                                    onLongClick = {}
                                )
                            )
                        }
                    }
                )
                articles.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item {
                                if (itemCount == 0) {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        InformationComponent(
                                            modifier = Modifier.padding(top = 200.dp),
                                            title = R.string.search,
                                            description = stringResource(id = R.string.search_description),
                                            image = R.drawable.search
                                        )
                                    }
                                } else {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                }
                            }
                        }

                        loadState.append is LoadState.Loading -> {
                            item {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }

                        loadState.refresh is LoadState.Error -> {
                            item {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    InformationComponent(
                                        title = R.string.error_title,
                                        description = stringResource(id = R.string.error_description),
                                    )
                                }
                            }
                        }

                        loadState.prepend is LoadState.Error -> {
                            item {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    InformationComponent(
                                        title = R.string.error_title,
                                        description = stringResource(id = R.string.error_description),
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


