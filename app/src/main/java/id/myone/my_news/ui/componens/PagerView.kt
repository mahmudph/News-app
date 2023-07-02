/**
 * Created by Mahmud on 25/06/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.ui.componens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.myone.my_news.ui.screens.onboarding.data.PagerViewData
import id.myone.my_news.ui.screens.onboarding.data.pagerViewListData
import id.myone.my_news.ui.theme.Paging3exampleTheme
import id.myone.my_news.ui.theme.plusJakartaSans


@Composable
fun PagerView(
    modifier: Modifier = Modifier,
    pager: PagerViewData,
) {

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Image(
            modifier = Modifier.fillMaxWidth(0.7f).fillMaxHeight(0.7f),
            painter = painterResource(id = pager.image),
            contentDescription = null,
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = pager.title),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            fontFamily = plusJakartaSans
        )

        Text(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            text = stringResource(id = pager.description),
            style = MaterialTheme.typography.bodySmall,
            fontFamily = plusJakartaSans,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xfff, widthDp = 480, heightDp = 680)
private fun PagerViewPreview() {
    Paging3exampleTheme {
        PagerView(
            pager = pagerViewListData.first(),
        )
    }
}