/**
 * Created by Mahmud on 08/06/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.myone.my_news.R
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    viewModel: SettingsViewModel = getViewModel(),
) {

    val isShowDialogSettings by viewModel.showDialogLanguage.collectAsStateWithLifecycle()
    val currentLanguage by viewModel.currentLanguage.collectAsStateWithLifecycle("")

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                title = {
                    Text(text = stringResource(R.string.setting),
                        color = MaterialTheme.colorScheme.onPrimary)
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack)
                    {
                        Icon(Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
            )
        }
    ) { _ ->
        SettingsContent(
            modifier = Modifier.padding(top = 77.dp, start = 16.dp, end = 16.dp),
            idShowDialogChangeLang = isShowDialogSettings,
            listAvailableLang = viewModel.languagePreferences,
            changeShowDialog = {
                viewModel.showDialogLanguage.value = it
            },
            setPreferenceLanguage = viewModel::setLanguage,
            currentLanguage = currentLanguage,
        )
    }
}