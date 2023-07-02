/**
 * Created by Mahmud on 30/06/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.ui.utils

import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext


@Composable
fun RequestPermission(
    permission: String,
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit,
) {
    val context = LocalContext.current
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val permissionStatus = context.checkSelfPermission(permission)

        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            return onPermissionGranted()
        }

        val requestPermissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted ->
                if (isGranted) onPermissionGranted()
                else onPermissionDenied()
            }
        )

        SideEffect {
            requestPermissionLauncher.launch(permission)
        }
    }
}