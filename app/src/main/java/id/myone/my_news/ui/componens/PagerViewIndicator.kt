/**
 * Created by Mahmud on 25/06/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.ui.componens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.myone.my_news.ui.theme.Paging3exampleTheme

@Composable
fun PagerViewIndicator(
    modifier: Modifier = Modifier,
    totalPager: Int,
    currentPage: Int,
) {
    Row(
        modifier = modifier.height(50.dp).fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(totalPager) { iteration ->
            val color = if (currentPage == iteration) Color.DarkGray else Color.LightGray
            Box(
                modifier = Modifier.padding(2.dp).clip(CircleShape).background(color).size(15.dp)
            )
        }
    }
}


@Composable
@Preview(showBackground = true, backgroundColor = 0xfff)
private fun PagerViewIndicatorPreview() {
    Paging3exampleTheme {
        PagerViewIndicator(
            totalPager = 5,
            currentPage = 2,
        )
    }
}