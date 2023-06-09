/**
 * Created by Mahmud on 06/06/23.
 * mahmud120398@gmail.com
 */

package id.myone.paging_3_example.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import id.myone.paging_3_example.R
import id.myone.paging_3_example.ui.componens.ListSettingItem
import id.myone.paging_3_example.ui.theme.Paging3exampleTheme
import id.myone.paging_3_example.ui.theme.plusJakartaSans

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    isShowDialogLogout: Boolean,
    onToggleDialog: (isHide: Boolean) -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToSetting: () -> Unit,
    onNavigateToInfo: () -> Unit,

    ) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {

                Box(
                    modifier = Modifier
                        .size(130.dp, 130.dp)
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = "https://e7.pngegg.com/pngimages/799/987/png-clipart-computer-icons-avatar-icon-design-avatar-heroes-computer-wallpaper-thumbnail.png",
                        modifier = Modifier.clip(RoundedCornerShape(100)),
                        contentScale = ContentScale.Fit,
                        alignment = Alignment.Center,
                        contentDescription = null,
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            "Joko Santoso",
                            fontFamily = plusJakartaSans,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text("jokosantoso@gmail.com",
                            fontFamily = plusJakartaSans,
                            fontSize = 12.sp)
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Outlined.Edit, contentDescription = null)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                ListSettingItem(
                    label = stringResource(R.string.settings),
                    leadingDrawableIcon = R.drawable.ic_baseline_settings_applications_24,
                    onClick = onNavigateToSetting
                )
                ListSettingItem(
                    label = stringResource(R.string.information),
                    leadingDrawableIcon = R.drawable.ic_outline_info_24,
                    onClick = {}
                )
                ListSettingItem(
                    label = stringResource(R.string.logout),
                    leadingDrawableIcon = R.drawable.ic_outline_logout_24,
                    onClick = {
                        onToggleDialog(true)
                    }
                )
            }
        }

        if (isShowDialogLogout) {
            AlertDialog(
                onDismissRequest = {
                    onToggleDialog(false)
                },
            ) {
                Surface(
                    modifier = Modifier.wrapContentSize(),
                    shape = MaterialTheme.shapes.large,
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.logout_confirm_info)
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(
                                onClick = { onToggleDialog(false) },
                            ) {
                                Text(stringResource(R.string.cancel))
                            }
                            TextButton(
                                onClick = onNavigateToLogin,
                            ) {
                                Text(stringResource(R.string.oke))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xfff)
@Composable
private fun ProfileContentPreview() {
    Paging3exampleTheme {
        ProfileContent(
            onNavigateToSetting = {},
            onNavigateToInfo = {},
            onNavigateToLogin = {},
            isShowDialogLogout = false,
            onToggleDialog = {},
        )
    }
}