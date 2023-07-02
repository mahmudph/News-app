package id.myone.my_news

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import id.myone.my_news.ui.route.GraphNavigation
import id.myone.my_news.ui.theme.Paging3exampleTheme
import id.myone.my_news.viewmodels.SplashViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashViewModel: SplashViewModel = getViewModel()
        installSplashScreen().setKeepOnScreenCondition { !splashViewModel.isLoading.value }

        setContent {
            Paging3exampleTheme {
                val startGraphDestination by splashViewModel.startGraphDestination
                val startAuthDestination by splashViewModel.startAuthDestination

                startAuthDestination?.let { authRoute ->
                    startGraphDestination?.let { graphDestination ->
                        GraphNavigation(
                            startGraphDestination = graphDestination,
                            startAuthDestination = authRoute,
                        )
                    }
                }
            }
        }
    }
}