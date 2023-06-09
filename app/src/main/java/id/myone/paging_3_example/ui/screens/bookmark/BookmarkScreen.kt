/**
 * Created by Mahmud on 05/06/23.
 * mahmud120398@gmail.com
 */

package id.myone.paging_3_example.ui.screens.bookmark

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.myone.paging_3_example.R
import id.myone.paging_3_example.data.local.table.ArticleTable
import id.myone.paging_3_example.ui.componens.InformationComponent
import id.myone.paging_3_example.ui.componens.NewsItemComponent
import id.myone.paging_3_example.ui.utils.SnackBarDelegate
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
                    Text("Penanda",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onBackground)
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                ),
            )
        }
    ) {
        Box(modifier = modifier
            .fillMaxSize()
            .padding(16.dp)) {

            if (favoriteArticleList.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center) {
                    InformationComponent(title = stringResource(id = R.string.empty_bookmark_title),
                        description = stringResource(id = R.string.empty_bookmark_desc))
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(items = favoriteArticleList, itemContent = {
                        NewsItemComponent(article = it, modifier = Modifier.combinedClickable(
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