/**
 * Created by Mahmud on 04/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.paging_3_example.ui.route

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import id.myone.paging_3_example.ui.screens.detail.NewsDetailScreen
import id.myone.paging_3_example.ui.screens.news.NewsScreen


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
            NewsDetailScreen(
                savedStateHandle = navigationNav.previousBackStackEntry?.savedStateHandle,
                onNavigatePop = {
                    navigationNav.popBackStack()
                }
            )
        }
    }
}