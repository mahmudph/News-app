/**
 * Created by Mahmud on 25/06/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.ui.screens.forgot_password


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.myone.my_news.R
import id.myone.my_news.common.VoidCallback
import id.myone.my_news.ui.componens.ButtonComponent
import id.myone.my_news.ui.theme.Paging3exampleTheme
import id.myone.my_news.ui.theme.plusJakartaSans
import id.myone.my_news.ui.utils.NoRippleEffect

@Composable
fun ForgotPasswordContent(
    isValidEmail: Boolean,
    modifier: Modifier = Modifier,
    email: String,
    onChangeEmail: (String) -> Unit,
    onSubmit: VoidCallback,
    onNavigateBack: VoidCallback,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier.fillMaxSize().verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopStart) {
            CompositionLocalProvider(
                LocalRippleTheme provides NoRippleEffect
            ) {
                Row(modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable(onClick = onNavigateBack),
                    verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null)
                    Text(text = stringResource(R.string.back),
                        fontFamily = plusJakartaSans,
                        modifier = Modifier.padding(start = 12.dp))
                }
            }
        }
        Image(
            painter = painterResource(id = R.drawable.forgot_password),
            contentDescription = null,
            contentScale = ContentScale.Inside,
            alignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight(0.8f)
                .background(Color.Transparent)
        )

        Text(
            stringResource(R.string.forgot_password_desc),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            fontFamily = plusJakartaSans,
            style = MaterialTheme.typography.bodySmall,
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = email,
            onValueChange = onChangeEmail,
            modifier = Modifier
                .padding(vertical = 4.dp)
                .fillMaxWidth(),
            singleLine = true,
            maxLines = 1,
            placeholder = {
                Text("Your email address",
                    style = MaterialTheme.typography.bodySmall,
                    fontFamily = plusJakartaSans)
            },
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done,
                autoCorrect = false,
            ),
        )

        Spacer(modifier = Modifier.height(6.dp))

        ButtonComponent(
            name = stringResource(R.string.forgot_password_btn),
            isEnabled = isValidEmail,
            onClick = onSubmit
        )
        Spacer(modifier = Modifier.height(6.dp))
    }
}


@Composable
@Preview(showBackground = true)
fun ForgotPasswordContentPreview() {
    Paging3exampleTheme {
        ForgotPasswordContent(
            email = "example@gmail.com",
            onChangeEmail = {},
            onSubmit = {},
            onNavigateBack = {},
            isValidEmail = false,
        )
    }
}