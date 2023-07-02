/**
 * Created by Mahmud on 06/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
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
import id.myone.my_news.ui.componens.OAuthButtonLoginComponent
import id.myone.my_news.ui.theme.Paging3exampleTheme
import id.myone.my_news.ui.theme.plusJakartaSans
import id.myone.my_news.ui.utils.NoRippleEffect

@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    email: String,
    password: String,
    isEnabledButton: Boolean = false,
    onChangeEmail: (String) -> Unit,
    onChangePassword: (String) -> Unit,
    onPressLoginWithCredential: VoidCallback,
    onPressLoginWithGoogleAccount: VoidCallback,
    onNavigateToRegister: VoidCallback,
    onNavigateToForgotPassword: VoidCallback,
) {
    val scrollState = rememberScrollState()
    var isShowPassword by remember { mutableStateOf(false) }
    val localFocus = LocalFocusManager.current


    val register = buildAnnotatedString {
        append("Don't Have an Account? ")
        withStyle(
            style = SpanStyle(color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline)
        ) {
            pushStringAnnotation("Register", "Register")
            append("Register")
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.login_image),
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
            text = stringResource(id = R.string.login),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = plusJakartaSans,
            textAlign = TextAlign.Left,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = email,
            onValueChange = onChangeEmail,
            modifier = Modifier.padding(vertical = 4.dp).fillMaxWidth(),
            singleLine = true,
            maxLines = 1,
            placeholder = {
                Text("Your email address",
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
                Text("Your password",
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

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
            CompositionLocalProvider(
                LocalRippleTheme provides  NoRippleEffect
            ) {
                TextButton(
                    onClick = onNavigateToForgotPassword,
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp,
                        disabledElevation = 0.dp
                    )
                ) {
                    Text(
                        "Forgot Password?",
                        textAlign = TextAlign.Right,
                        fontFamily = plusJakartaSans,
                        fontWeight = FontWeight.Medium,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        ButtonComponent(
            name = stringResource(id = R.string.login),
            isEnabled = isEnabledButton,
            onClick = onPressLoginWithCredential
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Divider(modifier = Modifier.weight(3f))
            Text(
                "OR",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            Divider(modifier = Modifier.weight(3f))
        }
        Spacer(modifier = Modifier.height(24.dp))
        OAuthButtonLoginComponent(
            icon = R.drawable.google_icon,
            name = "Login With Google",
            backgroundColor = Color(0xffF1F6F7),
            textColor = Color.Black,
            onClick = onPressLoginWithGoogleAccount,
        )

        Spacer(modifier = Modifier.weight(1f))

        ClickableText(
            text = register,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.bodySmall.copy(
                textAlign = TextAlign.Center,
                fontFamily = plusJakartaSans,
                color = MaterialTheme.colorScheme.onBackground
            ),
            onClick = { offset ->
                register.getStringAnnotations("Register", offset, offset).firstOrNull()?.let {
                    onNavigateToRegister()
                }
            }
        )
    }
}


@Preview(backgroundColor = 0xffff, showBackground = true, widthDp = 460, heightDp = 960)
@Composable
fun LoginContentPreview() {
    Paging3exampleTheme {
        LoginContent(email = "example@gmail.com",
            password = "12345678",
            onChangeEmail = {},
            onChangePassword = {},
            onPressLoginWithCredential = {},
            onPressLoginWithGoogleAccount = {},
            onNavigateToRegister = {},
            onNavigateToForgotPassword = {}
        )
    }
}