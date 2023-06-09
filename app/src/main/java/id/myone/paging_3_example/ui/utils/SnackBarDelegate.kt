/**
 * Created by Mahmud on 06/06/23.
 * mahmud120398@gmail.com
 */

package id.myone.paging_3_example.ui.utils

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal class SnackBarDelegate(
    var snackbarHostState: SnackbarHostState? = null,
    var coroutineScope: CoroutineScope? = null,
) {

    fun showSnackBar(
        message: String,
        actionLabel: String? = null,
        duration: SnackbarDuration = SnackbarDuration.Short,
    ) {
        coroutineScope?.launch {
            snackbarHostState?.showSnackbar(message, actionLabel, duration = duration)
        }
    }
}