/**
 * Created by Mahmud on 06/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.ui.route.graph

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import id.myone.my_news.ui.route.RouteName
import id.myone.my_news.ui.screens.bookmark.BookmarkScreen
import id.myone.my_news.ui.screens.detail.NewsDetailScreen
import id.myone.my_news.ui.screens.information.InformationScreen
import id.myone.my_news.ui.screens.news.NewsScreen
import id.myone.my_news.ui.screens.profile.ProfileScreen
import id.myone.my_news.ui.screens.profile.update.UpdateProfileScreen
import id.myone.my_news.ui.screens.search.SearchNewsScreen
import id.myone.my_news.ui.screens.settings.SettingsScreen
import id.myone.my_news.utils.config.RemoteConfigApp
import org.koin.androidx.compose.get


@Composable
fun DashboardNavGraph(
    navController: NavHostController,
    rootNavController: NavHostController,
    configApp: RemoteConfigApp = get(),
) {
    NavHost(
        navController = navController,
        startDestination = RouteName.dashboard,
    ) {
        composable(route = RouteName.dashboard) {
            NewsScreen(onNavigateToDetailNews = {
                navController.navigate("${RouteName.detail}/${it.id}")
            },
                onNavigateToSearchNews = {
                    navController.navigate(RouteName.search)
                }
            )
        }

        composable(
            route = "${RouteName.detail}/{news_id}",
            arguments = listOf(navArgument("news_id") { type = NavType.IntType }),
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "${configApp.getValue("DEEP_LINK_HOST").asString()}/news?id={news_id}"
                    action = Intent.ACTION_VIEW
                }
            )
        ) {
            NewsDetailScreen(
                newsId = it.arguments?.getInt("news_id") ?: 0,
                onNavigatePop = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = RouteName.search) {
            SearchNewsScreen(
                onNavigateToDetailPage = {
//                navController.currentBackStackEntry?.savedStateHandle?.set(
//                    RouteName.articleNavArgumentKey, it
//                )
                    navController.navigate("${RouteName.detail}/${it.id}")
                },
                onNavigatePop = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = RouteName.bookmark) {
            BookmarkScreen(
                onNavigateToDetail = {
//                    navController.currentBackStackEntry?.savedStateHandle?.set(
//                        RouteName.articleNavArgumentKey, it
//                    )
                    navController.navigate("${RouteName.detail}/${it.id}") {
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
                },
                onNavigateToUpdateProfile = {
                    navController.navigate(RouteName.updateProfile)
                }
            )
        }

        composable(route = RouteName.information) {
            InformationScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = RouteName.settings) {
            SettingsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = RouteName.updateProfile) {
            UpdateProfileScreen(onNavigateBack = { navController.popBackStack() })
        }
    }
}