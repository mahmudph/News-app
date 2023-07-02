/**
 * Created by Mahmud on 26/06/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.ui.screens.register

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.myone.my_news.R
import id.myone.my_news.common.VoidCallback
import id.myone.my_news.ui.componens.ButtonComponent
import id.myone.my_news.ui.theme.Paging3exampleTheme
import id.myone.my_news.ui.theme.plusJakartaSans
import id.myone.my_news.ui.utils.NoRippleEffect

@Composable
fun RegisterContent(
    modifier: Modifier = Modifier,
    name: String,
    email: String,
    password: String,
    isEnabledButton: Boolean = false,
    onChangeName: (String) -> Unit,
    onChangeEmail: (String) -> Unit,
    onChangePassword: (String) -> Unit,
    onRegister: () -> Unit,
    onNavigateToLogin: VoidCallback,
) {

    val scrollState = rememberScrollState()
    var isShowPassword by remember { mutableStateOf(false) }

    val localFocus = LocalFocusManager.current
    val localUrlHandler = LocalUriHandler.current

    val tnc = "Terms and Condition"
    val privacyPolicy = "Privacy Policy"

    val style = SpanStyle(color = Color.Black, textDecoration = TextDecoration.Underline)
    val annotatedString = buildAnnotatedString {
        append("By click register you totally agree with our ")
        withStyle(style = style) {
            pushStringAnnotation(tnc, "http://103.174.115.8:3000/privacy")
            append(tnc)
        }
        append(" and ")
        withStyle(style = style) {
            pushStringAnnotation(privacyPolicy, "http://103.174.115.8:3000/privacy")
            append(privacyPolicy)
        }
    }

    val loginAnnotatedString = buildAnnotatedString {
        append("Already have an account? ")
        withStyle(
            style = style.copy(color = MaterialTheme.colorScheme.primary)
        ) {
            pushStringAnnotation("login", "login")
            append("Login")
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopStart) {
            CompositionLocalProvider(
                LocalRippleTheme provides NoRippleEffect
            ) {
                Row(modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable(onClick = onNavigateToLogin), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null)
                    Text(text = stringResource(R.string.back), fontFamily = plusJakartaSans, modifier = Modifier.padding(start = 12.dp))
                }
            }
        }
        Image(
            painter = painterResource(id = R.drawable.register),
            contentDescription = null,
            contentScale = ContentScale.Inside,
            alignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight(0.8f)
                .background(Color.Transparent)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            stringResource(id = R.string.register),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = plusJakartaSans,
            textAlign = TextAlign.Left,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = name,
            onValueChange = onChangeName,
            modifier = Modifier
                .padding(vertical = 4.dp)
                .fillMaxWidth(),
            singleLine = true,
            maxLines = 1,
            placeholder = {
                Text("Username",
                    style = MaterialTheme.typography.bodySmall,
                    fontFamily = plusJakartaSans
                )
            },
            shape = RoundedCornerShape(12.dp),
            keyboardActions = KeyboardActions(onNext = {
                localFocus.moveFocus(FocusDirection.Down)
            }),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
                autoCorrect = false,
            ),
        )

        OutlinedTextField(
            value = email,
            onValueChange = onChangeEmail,
            modifier = Modifier
                .padding(vertical = 4.dp)
                .fillMaxWidth(),
            singleLine = true,
            maxLines = 1,
            placeholder = {
                Text("Email address",
                    style = MaterialTheme.typography.bodySmall,
                    fontFamily = plusJakartaSans)
            },
            shape = RoundedCornerShape(12.dp),
            keyboardActions = KeyboardActions(onNext = {
                localFocus.moveFocus(FocusDirection.Down)
            }),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
                autoCorrect = false,
            ),
        )
        OutlinedTextField(
            value = password,
            onValueChange = onChangePassword,
            modifier = Modifier
                .padding(vertical = 4.dp)
                .fillMaxWidth(),
            singleLine = true,
            maxLines = 1,
            placeholder = {
                Text("Password",
                    style = MaterialTheme.typography.bodySmall,
                    fontFamily = plusJakartaSans)
            },
            shape = RoundedCornerShape(12.dp),
            keyboardActions = KeyboardActions(onDone = {
                localFocus.clearFocus()
            }),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
                autoCorrect = false,
            ),
            visualTransformation = if (isShowPassword) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { isShowPassword = !isShowPassword }) {
                    Icon(
                        painter = painterResource(
                            id = if (isShowPassword) R.drawable.ic_baseline_visibility_off_24 else R.drawable.ic_baseline_visibility_24
                        ),
                        contentDescription = null,
                    )
                }
            },
        )
        Spacer(modifier = Modifier.height(6.dp))

        ButtonComponent(
            name = stringResource(R.string.register),
            isEnabled = isEnabledButton,
            onClick = onRegister
        )

        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
            ClickableText(
                text = annotatedString,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                style = MaterialTheme.typography.bodySmall.copy(
                    textAlign = TextAlign.Center,
                    fontFamily = plusJakartaSans
                ),
                onClick = { offset ->

                    annotatedString.getStringAnnotations(tnc, offset, offset).firstOrNull()
                        ?.let { span ->
                            localUrlHandler.openUri(span.item)
                        }

                    annotatedString.getStringAnnotations(privacyPolicy, offset, offset)
                        .firstOrNull()?.let { span ->
                            localUrlHandler.openUri(span.item)
                        }
                }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        ClickableText(
            text = loginAnnotatedString,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.bodySmall.copy(
                textAlign = TextAlign.Center,
                fontFamily = plusJakartaSans,
                color = MaterialTheme.colorScheme.onBackground
            ),
            onClick = { offset ->
                loginAnnotatedString.getStringAnnotations("login", offset, offset).firstOrNull()?.let {
                    onNavigateToLogin()
                }
            }
        )
    }
}


@Preview(backgroundColor = 0xffff, showBackground = true, widthDp = 460, heightDp = 960)
@Composable
fun RegisterContentPreview() {
    Paging3exampleTheme {
        RegisterContent(
            name = "Joko",
            email = "example@gmail.com",
            password = "12345678",
            onChangeName = {},
            onChangeEmail = {},
            onChangePassword = {},
            onRegister = {},
            onNavigateToLogin = {},
        )
    }
}