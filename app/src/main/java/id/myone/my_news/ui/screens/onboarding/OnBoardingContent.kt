/**
 * Created by Mahmud on 25/06/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.ui.screens.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.myone.my_news.R
import id.myone.my_news.ui.componens.PagerView
import id.myone.my_news.ui.componens.PagerViewIndicator
import id.myone.my_news.ui.screens.onboarding.data.PagerViewData
import id.myone.my_news.ui.screens.onboarding.data.pagerViewListData
import id.myone.my_news.ui.theme.Paging3exampleTheme
import id.myone.my_news.ui.utils.NoRippleEffect

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingContent(
    modifier: Modifier = Modifier,
    data: List<PagerViewData>,
    onDone: () -> Unit,
) {

    val pageState = rememberPagerState(initialPage = 0, initialPageOffsetFraction = 0f) { data.size }

    Column(modifier = modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
            CompositionLocalProvider(
                LocalRippleTheme provides NoRippleEffect
            ) {
                TextButton(onClick = onDone) {
                    Text(text = stringResource(R.string.skip), textAlign = TextAlign.Right)
                }
            }
        }
        HorizontalPager(state = pageState, modifier = Modifier.padding(horizontal = 16.dp)) { currentPage ->
            PagerView(pager = data[currentPage])
        }

        PagerViewIndicator(totalPager = data.size, currentPage = pageState.currentPage)

        AnimatedVisibility(
            visible = pageState.currentPage == data.size -1,
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            enter = slideInVertically(),
            exit = fadeOut()
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 35.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Button(onClick = onDone, modifier = modifier.fillMaxWidth()) {
                    Text(stringResource(R.string.finish), modifier = Modifier.padding(10.dp))
                }
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
private fun OnBoardingContentPreview() {
    Paging3exampleTheme {
        OnBoardingContent(onDone = { /*TODO*/ },data = pagerViewListData)
    }
}