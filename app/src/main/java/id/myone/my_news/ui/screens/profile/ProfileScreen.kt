/**
 * Created by Mahmud on 06/06/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.ui.screens.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.myone.my_news.R
import id.myone.my_news.ui.componens.InformationComponent
import id.myone.my_news.utils.UIState
import org.koin.androidx.compose.getViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    onNavigateToLogin: () -> Unit,
    onNavigateToSetting: () -> Unit,
    onNavigateToInfo: () -> Unit,
    onNavigateToUpdateProfile: () -> Unit,
    viewModel: ProfileViewModel = getViewModel(),
) {

    val user by viewModel.profileData.collectAsStateWithLifecycle()
    val isShowDialogLogout by viewModel.isShowDialogLogout.collectAsStateWithLifecycle(false)

    DisposableEffect(viewModel) {
        viewModel.addListener()
        onDispose {
            viewModel.removeListener()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.profile),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Share,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
        }
    ) {_ ->
        Box(
            modifier = modifier
                .statusBarsPadding()
                .padding(top = 56.dp)
                .fillMaxWidth()
        ) {

            user.getContent().let { data ->
                when (data) {
                    is UIState.Success -> {
                        ProfileContent(
                            user = data.data,
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
                            onNavigateToUpdateProfile = onNavigateToUpdateProfile,
                        )
                    }
                    else -> {
                        InformationComponent(
                            title = R.string.error_title,
                            description = stringResource(id = R.string.error_description),
                        )
                    }
                }
            }
        }
    }
}