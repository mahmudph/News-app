/**
 * Created by Mahmud on 05/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.paging_3_example.ui.screens.news_screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import id.myone.paging_3_example.R
import id.myone.paging_3_example.common.formatToLocalDate
import id.myone.paging_3_example.data.local.table.ArticleTable
import id.myone.paging_3_example.data.remote.model.Source
import id.myone.paging_3_example.ui.theme.Paging3exampleTheme

@Composable
fun NewsDetailContent(
    modifier: Modifier = Modifier,
    articleTable: ArticleTable,
) {

    val scrollState = rememberScrollState()

    Column(modifier = modifier
        .verticalScroll(scrollState)
        .fillMaxSize()
    ) {
        AsyncImage(model = articleTable.urlToImage,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.erorr_load_image),
            error = painterResource(id = R.drawable.erorr_load_image)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = (articleTable.title ?: "").replaceFirstChar { it.titlecase() },
            fontSize = 14.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Medium,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = (articleTable.author ?: "Guest").replaceFirstChar { it.titlecase() },
                fontSize = 12.sp,
                modifier = Modifier.weight(2f)
            )
            Box(
                contentAlignment = Alignment.CenterEnd,
                modifier = Modifier.weight(1f).fillMaxWidth()
            ){
                Text(
                    text = (articleTable.publishedAt ?: "Guest").formatToLocalDate(),
                    fontSize = 12.sp,

                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = (articleTable.content ?: "").replaceFirstChar { it.titlecase() },
            fontSize = 12.sp,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xfff)
@Composable
private fun NewsDetailContentPreview() {
    Paging3exampleTheme {
        NewsDetailContent(
            articleTable = ArticleTable(
                id = 1,
                title = "What is Lorem Ipsum?",
                description = "is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
                content = "lorem ipsum",
                publishedAt = "2023-05-03T01:00:00Z",
                urlToImage = "",
                url = "",
                source = Source(
                    id = "1",
                    name = "name",
                ),
                author = "mahmud"
            ),
        )
    }
}