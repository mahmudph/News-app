/**
 * Created by Mahmud on 04/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.ui.screens.news

import android.content.Intent
import android.text.TextUtils
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.material3.pullrefresh.PullRefreshIndicator
import androidx.compose.material3.pullrefresh.pullRefresh
import androidx.compose.material3.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import id.myone.my_news.R
import id.myone.my_news.data.local.table.ArticleTable
import id.myone.my_news.ui.componens.ContentSectionComponent
import id.myone.my_news.ui.componens.NewsItemComponent
import id.myone.my_news.ui.utils.SnackBarDelegate
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getViewModel


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NewsScreen(
    modifier: Modifier = Modifier,
    newsViewModel: NewsViewModel = getViewModel(),
    onNavigateToDetailNews: (article: ArticleTable) -> Unit,
    onNavigateToSearchNews: () -> Unit,
) {
    val context = LocalContext.current
    val snackBarDelegate: SnackBarDelegate = get()

    val message by newsViewModel.message.collectAsStateWithLifecycle()
    val selectedArticle by newsViewModel.selectedArticle.collectAsStateWithLifecycle()
    val showDialog by newsViewModel.isShowDialog.collectAsStateWithLifecycle(false)

    val listTopRatedNews by newsViewModel.topArticleList.collectAsStateWithLifecycle()
    val listNews = newsViewModel.articleList.collectAsLazyPagingItems()

    val bottomSheet = rememberModalBottomSheetState()
    val refresh = rememberPullRefreshState(
        refreshing = listNews.loadState.refresh is LoadState.Loading,
        onRefresh = {
            listNews.refresh()
        }
    )

    message?.getContentIfNotHandled()?.let { msg ->
        snackBarDelegate.showSnackBar(msg)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.artikel),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.W600
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = onNavigateToSearchNews) {
                        Icon(Icons.Default.Search,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
        },
    ) {
        Box(
            modifier = modifier.fillMaxSize().pullRefresh(refresh).padding(top = 56.dp)) {
            LazyColumn(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    ContentSectionComponent(
                        modifier = Modifier.padding(start = 16.dp, end = 4.dp, top = 16.dp),
                        title = stringResource(id = R.string.trending_article),
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(count = listTopRatedNews.size,
                            key = { index -> index },
                            itemContent = { index ->
                                Surface(
                                    modifier = Modifier
                                        .padding(
                                            start = if (index == 0) 16.dp else 0.dp,
                                            end = if (index == (listTopRatedNews.size - 1)) 16.dp else 0.dp
                                        )
                                        .placeholder(
                                            visible = listNews.loadState.refresh is LoadState.Loading,
                                            color = MaterialTheme.colorScheme.surfaceVariant,
                                            shape = RoundedCornerShape(16.dp),
                                            highlight = PlaceholderHighlight.shimmer(
                                                highlightColor = MaterialTheme.colorScheme.surface
                                            ),
                                        )
                                        .width(300.dp)
                                        .clickable {
                                            onNavigateToDetailNews(listTopRatedNews[index])
                                        },
                                    tonalElevation = 1.dp,
                                    shape = RoundedCornerShape(16.dp),
                                ) {
                                    Column(
                                        modifier = Modifier.padding(16.dp),
                                        verticalArrangement = Arrangement.Top,
                                        horizontalAlignment = Alignment.Start
                                    ) {
                                        AsyncImage(
                                            model = listTopRatedNews[index].urlToImage,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(300.dp, 150.dp)
                                                .clip(
                                                    RoundedCornerShape(16.dp)
                                                ),
                                            placeholder = painterResource(id = R.drawable.erorr_load_image),
                                            error = painterResource(id = R.drawable.erorr_load_image),
                                            contentScale = ContentScale.Crop,
                                            alignment = Alignment.Center,
                                        )
                                        Text(listTopRatedNews[index].title ?: "-",
                                            fontSize = 14.sp,
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier.padding(top = 12.dp)
                                        )
                                    }
                                }
                            }
                        )
                    }
                }

                item {
                    ContentSectionComponent(
                        modifier = Modifier.padding(start = 16.dp, end = 4.dp),
                        title = stringResource(id = R.string.latest_article),
                    )
                }

                items(count = listNews.itemCount,
                    key = listNews.itemKey(key = { news -> news.id }),
                    contentType = listNews.itemContentType()) { index ->

                    listNews[index]?.let { article ->
                        NewsItemComponent(
                            article = article,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .combinedClickable(
                                    onClick = {
                                        onNavigateToDetailNews(article)
                                    },
                                    onLongClick = {
                                        newsViewModel.selectedArticle.value = article
                                    }
                                )
                                .placeholder(
                                    visible = listNews.loadState.refresh is LoadState.Loading,
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                    shape = RoundedCornerShape(16.dp),
                                    highlight = PlaceholderHighlight.shimmer(
                                        highlightColor = MaterialTheme.colorScheme.surface
                                    ),
                                )
                        )
                    }
                }
            }

            if (showDialog) {
                ModalBottomSheet(
                    sheetState = bottomSheet,
                    onDismissRequest = {
                        newsViewModel.selectedArticle.value = null
                    }) {
                    Column {
                        ListItem(
                            modifier = Modifier.clickable {
                                selectedArticle?.let { article ->
                                    val intent = Intent(Intent.ACTION_SEND).apply {
                                        type = "text/plain"
                                        putExtra(
                                            Intent.EXTRA_TEXT,
                                            TextUtils.concat(article.url, "\n",
                                                article.title?.replaceFirstChar { it.uppercase() },
                                                "\n",
                                                article.description
                                            )
                                        )
                                        putExtra(Intent.EXTRA_TITLE, article.title ?: "")
                                        putExtra(Intent.EXTRA_SUBJECT, article.description ?: "")
                                    }
                                    context.startActivity(Intent.createChooser(intent, "Send"))
                                }

                                newsViewModel.selectedArticle.value = null
                            },
                            headlineContent = {
                                Text(stringResource(id = R.string.share_article))
                            },
                            leadingContent = {
                                Icon(
                                    Icons.Filled.Share,
                                    contentDescription = null,
                                )
                            },
                        )
                        ListItem(
                            modifier = Modifier.clickable {
                                newsViewModel.addToFavorite()
                            },
                            headlineContent = {
                                Text(stringResource(id = R.string.bookmark_article))
                            },
                            leadingContent = {
                                Icon(
                                    Icons.Filled.Favorite,
                                    contentDescription = null,
                                )
                            },
                        )
                    }
                }
            }

            PullRefreshIndicator(
                listNews.loadState.refresh is LoadState.Loading,
                refresh,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

