/**
 * Created by Mahmud on 04/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.paging_3_example.ui.screens.news

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import id.myone.paging_3_example.data.local.table.ArticleTable
import id.myone.paging_3_example.ui.componens.NewsItemComponent
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
    modifier: Modifier = Modifier,
    newsViewModel: NewsViewModel = getViewModel(),
    onNavigateToDetailNews: (article: ArticleTable) -> Unit,
    onNavigateToSearchNews: () -> Unit,
) {
    val listNews = newsViewModel.newsResult.collectAsLazyPagingItems()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Articles List",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = onNavigateToSearchNews) {
                        Icon(Icons.Default.Search, contentDescription = null, tint = Color.White)
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = modifier
            .padding(paddingValues)
            .fillMaxSize()) {

            if (listNews.loadState.refresh is LoadState.Error) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .align(Alignment.Center)
                ) {
                    Text(text = "Failed to get data from server, please try again",
                        textAlign = TextAlign.Center, modifier = Modifier.align(Alignment.Center))
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)) {


                    items(count = listNews.itemCount,
                        key = listNews.itemKey(key = { news -> news.id }),
                        contentType = listNews.itemContentType()) { index ->

                        listNews[index]?.let { article ->
                            NewsItemComponent(article = article, onPress = {
                                Log.i("a", "article with title ${article.title ?: "-"}")
                                onNavigateToDetailNews(article)
                            })
                        }
                    }
                }
            }
        }
    }
}

