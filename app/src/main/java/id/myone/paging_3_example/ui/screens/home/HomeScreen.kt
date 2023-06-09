/**
 * Created by Mahmud on 05/06/23.
 * mahmud120398@gmail.com
 */

package id.myone.paging_3_example.ui.screens.home

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import id.myone.paging_3_example.R
import id.myone.paging_3_example.ui.componens.HomeBottomAppBar
import id.myone.paging_3_example.ui.route.RouteName
import id.myone.paging_3_example.ui.route.graph.DashboardNavGraph
import id.myone.paging_3_example.ui.utils.SnackBarDelegate
import org.koin.androidx.compose.get

@Composable
fun HomeScreen(
    rootNavigation: NavHostController,
    statusBar: Color = MaterialTheme.colorScheme.background,
    navigation: NavHostController = rememberNavController(),
) {
    val isDarkMode = isSystemInDarkTheme()
    val activity = LocalContext.current as Activity
    val snackBarDelegate: SnackBarDelegate = get()

    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val systemUiController = rememberSystemUiController()
    var shouldShowBottomMenu by remember { mutableStateOf(false) }


    navigation.addOnDestinationChangedListener { _, destination, _ ->
        shouldShowBottomMenu = when (destination.route) {
            RouteName.home, RouteName.bookmark, RouteName.profile -> {
                true
            }
            else -> {
                false
            }
        }
    }

    SideEffect {
        systemUiController.setStatusBarColor(
            color = statusBar,
            darkIcons = !isDarkMode
        )
    }

    snackBarDelegate.apply {
        coroutineScope = scope
        snackbarHostState = snackBarHostState
    }

    BackHandler { activity.finish() }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) { data ->
                Snackbar(
                    modifier = Modifier.padding(12.dp),
                    action = {
                        TextButton(
                            onClick = { data.dismiss() },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = MaterialTheme.colorScheme.inversePrimary
                            )
                        ) {
                            Text(data.visuals.actionLabel ?: stringResource(id = R.string.dismiss))
                        }
                    }
                ) {
                    Text(data.visuals.message, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }
        },
        bottomBar = {
            if (shouldShowBottomMenu) HomeBottomAppBar(navController = navigation)
        }
    ) {
        Box {
            DashboardNavGraph(navController = navigation, rootNavController = rootNavigation)
        }
    }
}