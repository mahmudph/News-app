/**
 * Created by Mahmud on 07/06/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.ui.screens.information

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import id.myone.my_news.R
import id.myone.my_news.ui.theme.Paging3exampleTheme
import id.myone.my_news.ui.theme.plusJakartaSans

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformationScreen(
    onNavigateBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.about), color = MaterialTheme.colorScheme.onPrimary, fontFamily = plusJakartaSans,)
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) {
        Box(modifier = Modifier
            .padding(it)
            .fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    fontWeight = FontWeight.W600,
                    fontSize = 20.sp,
                    fontFamily = plusJakartaSans,
                )

                Text(
                    text = stringResource(id = R.string.app_version),
                    fontSize = 18.sp,
                    fontFamily = plusJakartaSans,
                )
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
private fun InformationScreenPreview() {
    Paging3exampleTheme {
        InformationScreen(
            onNavigateBack = {}
        )
    }
}