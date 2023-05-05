/**
 * Created by Mahmud on 04/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.paging_3_example.ui.route

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import id.myone.paging_3_example.data.local.table.ArticleTable
import id.myone.paging_3_example.ui.screens.news_screen.detail.NewsDetailScreen
import id.myone.paging_3_example.ui.screens.news_screen.news.NewsScreen


@Composable
fun GraphNavigation() {
    val navigationNav = rememberNavController()

    NavHost(navController = navigationNav, startDestination = RouteName.home) {
        composable(route = RouteName.home) {
            NewsScreen(onNavigateToDetailNews = {
                navigationNav.currentBackStackEntry?.savedStateHandle?.set(
                    RouteName.articleNavArgumentKey, it
                )

                navigationNav.navigate(RouteName.detail)
            })
        }

        composable(route = RouteName.detail) {
            val article = navigationNav.previousBackStackEntry?.savedStateHandle?.get<ArticleTable>(RouteName.articleNavArgumentKey)
            Log.d("data", article.toString())
            NewsDetailScreen(
                navigationNav = navigationNav,
                onNavigatePop = {
                    navigationNav.popBackStack()
                }
            )
        }
    }
}