/**
 * Created by Mahmud on 06/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.paging_3_example.ui.route.graph

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import id.myone.paging_3_example.ui.route.RouteGraph
import id.myone.paging_3_example.ui.route.RouteName
import id.myone.paging_3_example.ui.screens.bookmark.BookmarkScreen
import id.myone.paging_3_example.ui.screens.detail.NewsDetailScreen
import id.myone.paging_3_example.ui.screens.news.NewsScreen
import id.myone.paging_3_example.ui.screens.profile.ProfileScreen
import id.myone.paging_3_example.ui.screens.search.SearchNewsScreen
import id.myone.paging_3_example.ui.screens.settings.SettingsScreen


@Composable
fun DashboardNavGraph(navController: NavHostController, rootNavController: NavHostController) {
    NavHost(navController = navController,
        startDestination = RouteName.home,
        route = RouteGraph.home) {

        composable(route = RouteName.home) {
            NewsScreen(onNavigateToDetailNews = {
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    RouteName.articleNavArgumentKey, it
                )
                navController.navigate(RouteName.detail)
            },
                onNavigateToSearchNews = {
                    navController.navigate(RouteName.search)
                }
            )
        }

        composable(route = RouteName.detail) {
            NewsDetailScreen(
                savedStateHandle = navController.previousBackStackEntry?.savedStateHandle,
                onNavigatePop = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = RouteName.search) {
            SearchNewsScreen(onNavigateToDetailPage = {
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    RouteName.articleNavArgumentKey, it
                )

                navController.navigate(RouteName.detail)
            }, onNavigatePop = {
                navController.popBackStack()
            }
            )
        }

        composable(route = RouteName.bookmark) {
            BookmarkScreen(
                onNavigateToDetail = {
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        RouteName.articleNavArgumentKey, it
                    )
                    navController.navigate(RouteName.detail) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(route = RouteName.profile) {
            ProfileScreen(
                onNavigateToLogin = {
                    rootNavController.navigate(RouteName.login)
                },
                onNavigateToInfo = {
                    navController.navigate(RouteName.information)
                },
                onNavigateToSetting = {
                    navController.navigate(RouteName.settings)
                }
            )
        }
        
        composable(route = RouteName.information) {
            Box {
                
            }
        }

        composable(route = RouteName.settings) {
            SettingsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}