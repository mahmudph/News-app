/**
 * Created by Mahmud on 04/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.ui.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import id.myone.my_news.ui.route.graph.authGraph
import id.myone.my_news.ui.screens.home.HomeScreen


@Composable
fun GraphNavigation(
    startGraphDestination: String,
    startAuthDestination: String,
    navController: NavHostController = rememberNavController(),
) {

    NavHost(navController = navController, startDestination = startGraphDestination) {
        authGraph(navController, startDestination = startAuthDestination)
        composable(route = RouteName.home) {
            HomeScreen(rootNavigation = navController)
        }
    }
}