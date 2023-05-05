/**
 * Created by Mahmud on 05/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.paging_3_example.ui.screens.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.SavedStateHandle
import id.myone.paging_3_example.R
import id.myone.paging_3_example.data.local.table.ArticleTable
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
        SmallTopAppBar(title = {
            Text(text = article?.title ?: "Detail Article",
                maxLines = 1,
                fontSize = 18.sp,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }, navigationIcon = {
            IconButton(onClick = onNavigatePop) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = Color.White,
                )
            }
        },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        )
    }) { paddingValues ->
        Box(modifier = modifier
            .padding(paddingValues)
            .fillMaxSize()
        ) {

            if (article != null) {
                NewsDetailContent(articleTable = article!!, modifier = Modifier.padding(16.dp))
            } else {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {

                    Text(text = stringResource(id = R.string.error_title),
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium)

                    Text(text = stringResource(id = R.string.error_description),
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium)
                }
            }
        }
    }

}

