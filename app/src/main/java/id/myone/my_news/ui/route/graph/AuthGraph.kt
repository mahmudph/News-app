/**
 * Created by Mahmud on 06/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.ui.route.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import id.myone.my_news.ui.route.RouteGraph
import id.myone.my_news.ui.route.RouteName
import id.myone.my_news.ui.screens.forgot_password.ForgotPasswordScreen
import id.myone.my_news.ui.screens.forgot_password.SendForgotPasswordInfoScreen
import id.myone.my_news.ui.screens.login.LoginScreen
import id.myone.my_news.ui.screens.onboarding.OnBoardingScreen
import id.myone.my_news.ui.screens.register.RegisterScreen


fun NavGraphBuilder.authGraph(
    navController: NavController,
    startDestination: String,
) {
    navigation(
        startDestination = startDestination,
        route = RouteGraph.auth
    ) {
        composable(route = RouteName.login) {
            LoginScreen(
                onNavigateToRegisterScreen = {
                    navController.navigate(RouteName.register)
                },
                onNavigateToResetPasswordScreen = {
                    navController.navigate(RouteName.forgotPassword)
                },
                navigateToHome = {
                    navController.popBackStack()
                    navController.navigate(RouteName.home)
                }
            )
        }

        composable(route = RouteName.register) {
            RegisterScreen(
                onNavigateToLogin = {
                    navController.popBackStack()
                    navController.navigate(RouteName.login)
                }
            )
        }

        composable(route = RouteName.forgotPassword) {
            ForgotPasswordScreen(
                onNavigatePop = {
                    navController.popBackStack()
                },
                onNavigateToResetSuccess = {
                    navController.navigate(RouteName.forgotPasswordSuccess)
                }
            )
        }

        composable(route = RouteName.onBoarding) {
            OnBoardingScreen(
                navigateToLogin = {
                    navController.popBackStack()
                    navController.navigate(RouteName.login)
                }
            )
        }
        composable(route  = RouteName.forgotPasswordSuccess) {
            SendForgotPasswordInfoScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                    navController.navigate(RouteName.login)
                }
            )
        }
    }
}