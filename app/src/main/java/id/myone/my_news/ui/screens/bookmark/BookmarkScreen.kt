/**
 * Created by Mahmud on 05/06/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.ui.screens.bookmark

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.myone.my_news.R
import id.myone.my_news.data.local.table.ArticleTable
import id.myone.my_news.ui.componens.InformationComponent
import id.myone.my_news.ui.componens.NewsItemComponent
import id.myone.my_news.ui.utils.SnackBarDelegate
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun BookmarkScreen(
    modifier: Modifier = Modifier,
    onNavigateToDetail: (ArticleTable) -> Unit,
    viewModel: BookmarkViewModel = getViewModel(),
) {

    val snackBarDelegate: SnackBarDelegate = get()
    val bottomSheetState = rememberModalBottomSheetState()

    val message by viewModel.message.collectAsStateWithLifecycle()
    val favoriteArticleList by viewModel.favoriteArticle.collectAsStateWithLifecycle()
    val isShowBottomSheet by viewModel.isShowBottomSheet.collectAsStateWithLifecycle(initialValue = false)


    message?.getContentIfNotHandled()?.let { msg ->
        snackBarDelegate.showSnackBar(msg)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.bookmarks),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onPrimary)
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
            )
        }
    ) { padding ->
        Box(modifier = modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp)) {

            if (favoriteArticleList.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center) {
                    InformationComponent(
                        title = R.string.empty_bookmark_title,
                        description =  stringResource(id = R.string.empty_bookmark_desc),
                        image = R.drawable.not_found,
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(vertical = 56.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(items = favoriteArticleList, itemContent = {
                        NewsItemComponent(
                            article = it,
                            modifier = Modifier.combinedClickable(
                                onClick = {
                                    onNavigateToDetail(it)
                                },
                                onLongClick = {
                                    viewModel.selectedArticle.value = it
                                }
                            )
                        )
                    })
                }
            }

            if (isShowBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        viewModel.selectedArticle.value = null
                    },
                    sheetState = bottomSheetState) {
                    Column {
                        ListItem(
                            modifier = Modifier.clickable {
                                viewModel.deleteBookmark()
                            },
                            headlineContent = {
                                Text(stringResource(id = R.string.delete_bookmark_article))
                            },
                            leadingContent = {
                                Icon(
                                    Icons.Filled.Delete,
                                    contentDescription = null,
                                )
                            },
                        )
                    }
                }
            }
        }
    }
}