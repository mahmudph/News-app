/**
 * Created by Mahmud on 05/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.paging_3_example.ui.screens.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.SavedStateHandle
import id.myone.paging_3_example.R
import id.myone.paging_3_example.data.local.table.ArticleTable
import id.myone.paging_3_example.ui.componens.InformationComponent
import id.myone.paging_3_example.ui.componens.WebViewComponent
import id.myone.paging_3_example.ui.route.RouteName

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailScreen(
    modifier: Modifier = Modifier,
    savedStateHandle: SavedStateHandle?,
    onNavigatePop: () -> Unit,
) {
    var article by remember { mutableStateOf<ArticleTable?>(null) }

    LaunchedEffect(savedStateHandle) {
        article = savedStateHandle?.get<ArticleTable>(RouteName.articleNavArgumentKey)
    }

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = article?.title ?: "Detail Article",
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
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.background
            )
        )
    }) { paddingValues ->

        Box(modifier = modifier.padding(paddingValues).fillMaxSize()) {
            if (article != null) {
//                NewsDetailContent(articleTable = article!!, modifier = Modifier.padding(16.dp))
                article?.url?.let { WebViewComponent(url = it) }
            } else {
                InformationComponent(
                    title = stringResource(id = R.string.error_title),
                    description = stringResource(id = R.string.error_description),
                )
            }
        }
    }

}

