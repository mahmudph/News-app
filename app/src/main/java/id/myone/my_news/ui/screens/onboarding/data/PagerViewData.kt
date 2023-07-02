package id.myone.my_news.ui.screens.onboarding.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import id.myone.my_news.R

data class PagerViewData(
    @DrawableRes val image: Int,
    @StringRes val title: Int,
    @StringRes val description: Int,
)

val pagerViewListData = listOf(
    PagerViewData(
        image = R.drawable.news_collections,
        title = R.string.page_view_title_1,
        description = R.string.page_view_desc_1
    ),
    PagerViewData(
        image = R.drawable.search_news,
        title = R.string.page_view_title_2,
        description = R.string.page_view_desc_2
    ),
    PagerViewData(
        image = R.drawable.news,
        title = R.string.page_view_title_3,
        description = R.string.page_view_desc_3
    ),
)
