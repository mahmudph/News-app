/**
 * Created by Mahmud on 04/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.paging_3_example.ui.componens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
fun NewsItemComponent(
    modifier: Modifier = Modifier,
    article: ArticleTable,
    onPress: () -> Unit,
) {

    val customIndication = rememberRipple(
        color = Color.Green,
        bounded = false,
        radius = 50.dp
    )


    Surface(modifier = modifier
        .fillMaxWidth()
        .clickable(onClick = onPress),
        shape = RoundedCornerShape(8.dp),
        shadowElevation = 1.dp,
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = (article.author ?: "-").replaceFirstChar { it.titlecase() },
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        overflow = TextOverflow.Clip,
                        maxLines = 2,
                    )
                }

                Box(modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()) {
                    Text(text = article.publishedAt?.formatToLocalDate() ?: "-",
                        fontSize = 12.sp,
                        textAlign = TextAlign.Right, modifier = Modifier
                            .align(Alignment.CenterEnd)
                    )
                }

            }
            AsyncImage(model = article.urlToImage,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.erorr_load_image),
                error = painterResource(id = R.drawable.erorr_load_image)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(article.title.toString(),
                textAlign = TextAlign.Left,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold)
            Text(
                article.description.toString(),
                textAlign = TextAlign.Left,
                fontSize = 12.sp,
                letterSpacing = 1.sp,
                modifier = Modifier.padding(top = 8.dp),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFFF, widthDp = 460)
@Composable
private fun NewsItemComponentPreview() {
    Paging3exampleTheme {
        NewsItemComponent(
            article = ArticleTable(
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
            onPress = {}
        )
    }
}