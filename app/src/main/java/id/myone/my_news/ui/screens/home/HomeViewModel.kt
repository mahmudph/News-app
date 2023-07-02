/**
 * Created by Mahmud on 24/06/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.myone.my_news.common.Constraint
import id.myone.my_news.utils.config.RemoteConfigApp
import id.myone.my_news.utils.works.WorkManagerHandler
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class HomeViewModel(
    private val workManagerHandler: WorkManagerHandler,
    private val configApp: RemoteConfigApp,
) : ViewModel() {

    fun startSyncNewsWork() {
        viewModelScope.launch {
            workManagerHandler.startPeriodicSyncManagerOnNotRunning(
                WorkManagerHandler.workName,
                workManagerHandler.createConstraintDeviceConnected(),
                configApp.getValue(Constraint.Config.workManagerIntervalInHour).asLong(),
                TimeUnit.HOURS,
            )
        }
    }
}