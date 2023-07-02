/**
 * Created by Mahmud on 06/06/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import id.myone.my_news.R
import id.myone.my_news.common.Constraint.defaultAvatarUri
import id.myone.my_news.ui.componens.ListSettingItem
import id.myone.my_news.ui.theme.Paging3exampleTheme
import id.myone.my_news.ui.theme.plusJakartaSans
import id.myone.my_news.utils.auth.model.UserAuthResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    user: UserAuthResult,
    isShowDialogLogout: Boolean,
    onToggleDialog: (isHide: Boolean) -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToSetting: () -> Unit,
    onNavigateToInfo: () -> Unit,
    onNavigateToUpdateProfile: () -> Unit,
) {


    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .padding(16.dp),
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .height(100.dp)
                        .padding(start = 16.dp),
                    contentAlignment = Alignment.BottomStart
                ) {
                    AsyncImage(
                        model = user.avatar ?: defaultAvatarUri,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(100.dp),
                        contentScale = ContentScale.FillWidth,
                        alignment = Alignment.BottomStart,
                        contentDescription = null,
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            user.name ?: "user guest",
                            fontFamily = plusJakartaSans,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            user.email ?: "guest@gmail.com",
                            fontFamily = plusJakartaSans,
                            fontSize = 12.sp
                        )
                    }
                    IconButton(onClick = onNavigateToUpdateProfile) {
                        Icon(
                            Icons.Outlined.Edit,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
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
                    onClick = onNavigateToInfo
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
                            text = stringResource(R.string.logout_confirm_info),
                            style = MaterialTheme.typography.bodySmall,
                            fontFamily = plusJakartaSans,
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(
                                onClick = { onToggleDialog(false) },
                            ) {
                                Text(stringResource(R.string.cancel), fontFamily = plusJakartaSans)
                            }
                            TextButton(
                                onClick = onNavigateToLogin,
                            ) {
                                Text(stringResource(R.string.oke), fontFamily = plusJakartaSans)
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
            user = UserAuthResult(
                id = "12",
                name = "joko",
                email = "joko@gmail.com",
                avatar = "https://lh3.googleusercontent.com/a/AAcHTtfMXbYFFag9frnehJdsqrP5l2rYlBorbImezrwVYYG7NrI=s96-c"
            ),
            onNavigateToSetting = {},
            onNavigateToInfo = {},
            onNavigateToLogin = {},
            isShowDialogLogout = false,
            onToggleDialog = {},
            onNavigateToUpdateProfile = {}
        )
    }
}