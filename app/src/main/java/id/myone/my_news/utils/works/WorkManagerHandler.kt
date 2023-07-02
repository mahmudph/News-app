/**
 * Created by Mahmud on 30/06/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.utils.works

import android.util.Log
import androidx.work.*
import java.util.concurrent.TimeUnit

class WorkManagerHandler(private val workManager: WorkManager) {
    companion object {
        private const val TAG = "WorkManagerHandler"
        const val singleWorkName = "single_work_name"
        const val workName = "sync_news_worker"
    }

    fun createConstraintDeviceConnected(networkType: NetworkType = NetworkType.CONNECTED): Constraints {
        return Constraints.Builder()
            .setRequiredNetworkType(networkType)
            .build()
    }

    fun startSingleWorkManager(workName: String, constraints: Constraints) {
        val workRequest = OneTimeWorkRequestBuilder<SynchronizeNewsWorker>()
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniqueWork(workName, ExistingWorkPolicy.REPLACE, workRequest)
    }

    fun startPeriodicWorkManager(
        workName: String,
        constraints: Constraints,
        interval: Long, timeUnit: TimeUnit,
    ) {
        val workRequest = PeriodicWorkRequestBuilder<SynchronizeNewsWorker>(
            repeatInterval = interval, timeUnit
        ).setConstraints(constraints).build()

        workManager.enqueueUniquePeriodicWork(
            workName,
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }

    fun cancelAllWorkManager() {
        workManager.cancelAllWork()
    }

    fun cancelUniqWorkManager(workName: String) {
        workManager.cancelUniqueWork(workName)
    }

    fun isWorkScheduled(workName: String): Boolean {
        var isRunning = false
        val data = workManager.getWorkInfosForUniqueWork(workName)
        Log.i(TAG, data.get().toString())

        data.get()?.let { workInfo ->
            for (work in workInfo) {

                Log.i(TAG, work.state.toString())
                if (work.state == WorkInfo.State.RUNNING || work.state == WorkInfo.State.ENQUEUED) {
                    isRunning = true
                    break
                }
            }
        }

        return isRunning
    }

    fun startPeriodicSyncManagerOnNotRunning(
        workName: String,
        constraints: Constraints,
        interval: Long,
        timeUnit: TimeUnit,
    ) {

        val isRunning = isWorkScheduled(workName)
        if (isRunning) return

        startPeriodicWorkManager(workName, constraints, interval, timeUnit)
    }
}