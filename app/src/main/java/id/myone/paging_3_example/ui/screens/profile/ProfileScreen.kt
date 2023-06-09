/**
 * Created by Mahmud on 06/06/23.
 * mahmud120398@gmail.com
 */

package id.myone.paging_3_example.ui.screens.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.myone.paging_3_example.R
import org.koin.androidx.compose.getViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    onNavigateToLogin: () -> Unit,
    onNavigateToSetting: () -> Unit,
    onNavigateToInfo: () -> Unit,
    viewModel: ProfileViewModel = getViewModel(),
) {
    val isShowDialogLogout by viewModel.isShowDialogLogout.collectAsStateWithLifecycle(false)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.profile))
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Share, contentDescription = null)
                    }
                }
            )
        }
    ) {
        Box(
            modifier = modifier
                .statusBarsPadding()
                .padding(top = 56.dp)
        ) {
            ProfileContent(
                isShowDialogLogout = isShowDialogLogout,
                onToggleDialog = {
                    viewModel.toggleDialog(it)
                },
                onNavigateToLogin = {
                    viewModel.logout()
                    onNavigateToLogin()
                },
                onNavigateToInfo = onNavigateToInfo,
                onNavigateToSetting = onNavigateToSetting,
            )
        }
    }
}