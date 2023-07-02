/**
 * Created by Mahmud on 21/06/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.ui.screens.profile.update

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.myone.my_news.R
import id.myone.my_news.ui.componens.TextInputField
import id.myone.my_news.ui.theme.Paging3exampleTheme
import id.myone.my_news.ui.theme.plusJakartaSans

@Composable
fun UpdateProfileContent(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    currentUserName: String,
    currentUserEmail: String,
    currentUserAvatar: Uri?,
    onChangeUserName: (String) -> Unit,
    onChangeUserEmail: (String) -> Unit,
    onChangeUserAvatar: (Uri) -> Unit,
    onSaveUserProfile: () -> Unit,
) {

    val scrollState = rememberScrollState()

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier
                .padding(16.dp)
                .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Top
            ) {
                TextInputField(
                    initialValue = currentUserName,
                    onValueChange = onChangeUserName,
                    label = stringResource(id = R.string.name)
                )
                TextInputField(
                    initialValue = currentUserEmail,
                    onValueChange = onChangeUserEmail,
                    label = stringResource(R.string.email),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done,
                    )
                )
            }
        }
        Surface(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            ElevatedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 12.dp,
                        vertical = 12.dp
                    ),
                shape = MaterialTheme.shapes.medium,
                onClick = onSaveUserProfile,
            ) {
                Text(stringResource(R.string.save), fontWeight = FontWeight.W600, fontSize = 22.sp, fontFamily = plusJakartaSans)
            }
        }

        if (isLoading) {
            Surface {
                CircularProgressIndicator(modifier = Modifier.padding(12.dp))
            }
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xfff, heightDp = 668, widthDp = 468)
private fun UpdateProfileContentPreview() {
    Paging3exampleTheme {
        UpdateProfileContent(
            currentUserAvatar = null,
            currentUserEmail = "joko@gmail.com",
            currentUserName = "joko",
            onSaveUserProfile = {},
            onChangeUserName = {},
            onChangeUserAvatar = {},
            onChangeUserEmail = {},
            isLoading = false
        )
    }
}