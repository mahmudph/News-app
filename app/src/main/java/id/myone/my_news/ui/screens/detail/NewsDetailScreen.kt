/**
 * Created by Mahmud on 05/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.ui.screens.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.myone.my_news.R
import id.myone.my_news.ui.componens.InformationComponent
import id.myone.my_news.ui.componens.WebViewComponent
import id.myone.my_news.utils.UIState
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailScreen(
    modifier: Modifier = Modifier,
    newsId: Int,
    viewModel: NewsDetailViewModel = getViewModel(),
    onNavigatePop: () -> Unit,
) {
    var newsTitle by remember { mutableStateOf("") }
    val news by viewModel.result.collectAsStateWithLifecycle()

    LaunchedEffect(newsId) { viewModel.loadNewsById(newsId) }

    LaunchedEffect(key1 = news) {
        news.getContentIfNotHandled()?.let { state ->
            when (state) {
                is UIState.Success -> {
                    newsTitle = state.data.title ?: "Detail Article"
                }
                else -> {}
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = newsTitle,
                        maxLines = 1,
                        fontSize = 18.sp,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }, navigationIcon = {
                    IconButton(onClick = onNavigatePop) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }) { paddingValues ->

        Box(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {

            news.getContent().let { state ->
                when (state) {
                    is UIState.Success -> {
                        state.data.url?.let { WebViewComponent(url = it) }
                    }
                    is UIState.Loading -> {
                        Surface {
                            CircularProgressIndicator(modifier = Modifier.padding(12.dp))
                        }
                    }
                    is UIState.Error<*> -> {
                        InformationComponent(
                            title = R.string.error_title,
                            description = state.message,
                        )
                    }
                    else -> {
                        Box{}
                    }
                }
            }
        }
    }

}

