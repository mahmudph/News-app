/**
 * Created by Mahmud on 21/06/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.ui.screens.profile.update

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.myone.my_news.R
import id.myone.my_news.ui.theme.Paging3exampleTheme
import id.myone.my_news.utils.UIState
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateProfileScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    snackBarHostState: SnackbarHostState = SnackbarHostState(),
    viewModel: UpdateProfileViewModel = getViewModel(),
) {
    val userName by viewModel.userName.collectAsStateWithLifecycle()
    val userEmail by viewModel.userEmail.collectAsStateWithLifecycle()
    val userAvatar by viewModel.userAvatar.collectAsStateWithLifecycle()

    val result by viewModel.updateProfileResult.collectAsStateWithLifecycle()


    LaunchedEffect(result) {
        result.getContentIfNotHandled()?.let { state ->
            when (state) {
                is UIState.Success -> {
                    snackBarHostState.showSnackbar(state.data)
                    onNavigateBack()

                }
                is UIState.Error<*> -> {
                    snackBarHostState.showSnackbar(state.message)
                }
                else -> {}
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        text = stringResource(R.string.update_profile),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
            )
        }
    ) {
        Box(modifier = modifier.padding(top = 56.dp)) {
            UpdateProfileContent(
                currentUserName = userName,
                currentUserEmail = userEmail,
                currentUserAvatar = userAvatar,
                isLoading = result.getContent() is UIState.Loading,
                onChangeUserAvatar = { uri -> viewModel.userAvatar.value = uri },
                onChangeUserEmail = { email -> viewModel.userEmail.value = email },
                onChangeUserName = { name -> viewModel.userName.value = name },
                onSaveUserProfile = { viewModel.handleOnUpdateProfile() }
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
private fun UpdateProfileScreenPreview() {
    Paging3exampleTheme {
        UpdateProfileScreen(
            onNavigateBack = {}
        )
    }
}