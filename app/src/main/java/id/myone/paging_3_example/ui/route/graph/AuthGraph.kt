/**
 * Created by Mahmud on 06/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.paging_3_example.ui.route.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import id.myone.paging_3_example.ui.route.RouteGraph
import id.myone.paging_3_example.ui.route.RouteName
import id.myone.paging_3_example.ui.screens.forgot_password.ForgotPasswordScreen
import id.myone.paging_3_example.ui.screens.login.LoginScreen
import id.myone.paging_3_example.ui.screens.register.RegisterScreen
import id.myone.paging_3_example.ui.screens.splashscreen.SplashScreen


fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation(
        startDestination = RouteName.splashScreen,
        route = RouteGraph.auth
    ) {
        composable(route = RouteName.login) {
            LoginScreen(
                onNavigateToRegisterScreen = {
                    navController.navigate(RouteName.login)
                },
                onNavigateToResetPasswordScreen = {
                    navController.navigate(RouteName.forgotPassword)
                },
                onNavigateToHomeScreen = {
                    navController.popBackStack()
                    navController.navigate(RouteGraph.home)
                }
            )
        }

        composable(route = RouteName.register) {
            RegisterScreen(
                onNavigatePop = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = RouteName.forgotPassword) {
            ForgotPasswordScreen(
                onNavigatePop = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = RouteName.splashScreen) {
            SplashScreen(
                onNavigateToLogin = {
                    navController.popBackStack()
                    navController.navigate(RouteName.login)
                },
                onNaviagateToHome = {
                    navController.popBackStack()
                    navController.navigate(RouteGraph.home)
                }
            )
        }
    }
}