/**
 * Created by Mahmud on 04/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.paging_3_example.ui.route

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import id.myone.paging_3_example.ui.route.graph.authGraph
import id.myone.paging_3_example.ui.screens.home.HomeScreen


@Composable
fun GraphNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = RouteGraph.home) {
        authGraph(navController)
        composable(route = RouteGraph.home) {
            HomeScreen(rootNavigation = navController)
        }
    }
}