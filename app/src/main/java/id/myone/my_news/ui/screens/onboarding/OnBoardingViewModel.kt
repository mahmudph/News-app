/**
 * Created by Mahmud on 25/06/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.ui.screens.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.myone.my_news.data.AppRepositoryContract
import kotlinx.coroutines.launch

class OnBoardingViewModel(
    private val appRepositoryContract: AppRepositoryContract
): ViewModel() {

    fun setIsPassedSplashscreen() {
        viewModelScope.launch {
            appRepositoryContract.setIsPassedOnBoarding(true)
        }
    }
}