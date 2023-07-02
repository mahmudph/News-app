/**
 * Created by Mahmud on 06/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.ui.screens.onboarding

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import id.myone.my_news.ui.screens.onboarding.data.pagerViewListData
import org.koin.androidx.compose.getViewModel

@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    viewModel: OnBoardingViewModel = getViewModel(),
    navigateToLogin: () -> Unit,
) {

    Scaffold {
        OnBoardingContent(
            modifier = modifier,
            data = pagerViewListData,
            onDone = {
                viewModel.setIsPassedSplashscreen()
                navigateToLogin()
            },
        )
    }
}